package com.cafeapp.data.auth.remote

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSourceImpl(private val firebaseAuth: FirebaseAuth) : AuthRemoteDataSource {
    override val user: RemoteUser?
        get() = firebaseAuth.currentUser?.toRemoteUser()

    override suspend fun signUpUser(email: String, password: String): RemoteResult<RemoteUser> {
        val createdUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
        return if (createdUser != null) RemoteResult.Success(createdUser.toRemoteUser()) else RemoteResult.Failed()
    }

    override suspend fun signInUser(email: String, password: String): RemoteResult<RemoteUser> {
        return try {
            val currentUser = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            if (currentUser != null) RemoteResult.Success(currentUser.toRemoteUser()) else RemoteResult.Failed()
        } catch (e: Exception) {
            RemoteResult.Failed()
        }
    }

    private fun FirebaseUser.toRemoteUser(): RemoteUser =
        RemoteUser(uid, email!!, photoUrl?.toString(), displayName)
}