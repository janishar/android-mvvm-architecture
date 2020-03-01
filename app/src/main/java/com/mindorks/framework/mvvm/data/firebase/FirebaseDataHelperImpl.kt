package com.mindorks.framework.mvvm.data.firebase

import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataHelperImpl @Inject constructor(
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : FirebaseDataHelper {

    //authentication

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    override fun getSignInInent() =
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

    override fun getCurrentUser() = firebaseAuth.currentUser

    //firestore
    override fun setUserInFirestore(userMap: Map<String, Any>): Task<DocumentReference> {
        return firebaseFirestore.collection(USER_COLLECTION).add(userMap)
    }

    companion object {
        private const val USER_COLLECTION = "users"

        const val USER_KEY_ACCESS_TOKEN = "access_token"
        const val USER_KEY_USER_ID = "user_id"
        const val USER_KEY_LOGGED_IN_MODE = "logged_in_mode"
        const val USER_KEY_USERNAME = "user_name"
        const val USER_KEY_EMAIL = "email"
        const val USER_KEY_PROFILE_PIC_PATH = "profile_pic_path"
    }
}
