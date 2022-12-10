package com.cafeapp.data.auth.remote

import android.util.Log
import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSourceImpl(private val firebaseAuth: FirebaseAuth) : AuthRemoteDataSource {
    override val user: RemoteUser?
        get() = firebaseAuth.currentUser?.toRemoteUser()

    override suspend fun signUpUser(email: String, password: String): RemoteSignInResult {
        val createdUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
        return if (createdUser != null) RemoteSignInResult.Success(createdUser.toRemoteUser()) else RemoteSignInResult.WrongCredentialsError
    }

    override suspend fun signInUser(email: String, password: String): RemoteSignInResult {
        return try {
            val currentUser = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            if (currentUser != null) RemoteSignInResult.Success(currentUser.toRemoteUser()) else RemoteSignInResult.WrongCredentialsError
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            RemoteSignInResult.WrongCredentialsError
        } catch (e: Exception) {
            RemoteSignInResult.NetworkUnavailableError
        }
    }

    private fun FirebaseUser.toRemoteUser(): RemoteUser =
        RemoteUser(uid, email!!, photoUrl?.toString(), displayName)
}