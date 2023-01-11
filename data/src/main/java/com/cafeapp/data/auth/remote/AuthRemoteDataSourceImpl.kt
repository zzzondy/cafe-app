package com.cafeapp.data.auth.remote

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteCheckUserResult
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.cafeapp.data.auth.remote.states.RemoteSignUpResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
) : AuthRemoteDataSource {
    override var user: RemoteUser? = null
        get() = firebaseAuth.currentUser?.toRemoteUser()

    override suspend fun signUpUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photo: ByteArray?
    ): RemoteSignUpResult {
        return try {
            val createdUser =
                firebaseAuth.createUserWithEmailAndPassword(email, password).await().user

            val userProfileUpdate = userProfileChangeRequest {
                displayName = "$firstName $lastName"
            }
            firebaseAuth.currentUser!!.updateProfile(userProfileUpdate).await()
            if (photo != null) {
                changeUserPhoto(photo)
            }
            RemoteSignUpResult.Success(createdUser!!.toRemoteUser())
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

    private suspend fun changeUserPhoto(photo: ByteArray) {
        val userPhotoRef = firebaseStorage.reference.child("${firebaseAuth.currentUser!!.uid}.jpg")
        userPhotoRef.putBytes(photo).await()
        val profileUpdates = userProfileChangeRequest {
            photoUri = userPhotoRef.downloadUrl.await()
        }
        firebaseAuth.currentUser!!.updateProfile(profileUpdates).await()
    }

    private fun FirebaseUser.toRemoteUser(): RemoteUser =
        RemoteUser(uid, email!!, photoUrl?.toString(), displayName)
}