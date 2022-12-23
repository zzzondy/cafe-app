package com.cafeapp.data.auth.remote

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteCheckUserResult
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.cafeapp.data.auth.remote.states.RemoteSignUpResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSourceImpl(private val firebaseAuth: FirebaseAuth) : AuthRemoteDataSource {
    override var user: RemoteUser? = firebaseAuth.currentUser?.toRemoteUser()

    override suspend fun signUpUser(email: String, password: String): RemoteSignUpResult {
        return try {
            val createdUser =
                firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
            if (createdUser != null) RemoteSignUpResult.Success(createdUser.toRemoteUser()) else RemoteSignUpResult.UserAlreadyExistsError
        } catch (e: FirebaseAuthUserCollisionException) {
            RemoteSignUpResult.UserAlreadyExistsError
        } catch (e: FirebaseNetworkException) {
            RemoteSignUpResult.NetworkUnavailableError
        } catch (e: Exception) {
            RemoteSignUpResult.OtherError
        }
    }

    override suspend fun signInUser(email: String, password: String): RemoteSignInResult {
        return try {
            val currentUser = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            if (currentUser != null) RemoteSignInResult.Success(currentUser.toRemoteUser()) else RemoteSignInResult.WrongCredentialsError
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            RemoteSignInResult.WrongCredentialsError
        } catch (e: FirebaseNetworkException) {
            RemoteSignInResult.NetworkUnavailableError
        } catch (e: Exception) {
            RemoteSignInResult.OtherError
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
        user = null
    }

    override suspend fun checkUserAlreadyExists(email: String): RemoteCheckUserResult {
        return try {
            val result = firebaseAuth.fetchSignInMethodsForEmail(email)
                .await()

            if (result.signInMethods == null || result.signInMethods!!.isEmpty()) {
                RemoteCheckUserResult.NotExists
            } else {
                RemoteCheckUserResult.AlreadyExists
            }
        } catch (e: FirebaseNetworkException) {
            RemoteCheckUserResult.NetworkUnavailableError
        } catch (e: Exception) {
            RemoteCheckUserResult.OtherError
        }
    }

    private fun FirebaseUser.toRemoteUser(): RemoteUser =
        RemoteUser(uid, email!!, photoUrl?.toString(), displayName)
}