package com.mindorks.framework.mvvm.data.firebase

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseDataHelperImpl @Inject constructor(
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : FirebaseDataHelper {

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

}
