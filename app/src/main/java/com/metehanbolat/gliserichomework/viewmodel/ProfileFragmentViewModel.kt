package com.metehanbolat.gliserichomework.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.metehanbolat.gliserichomework.model.UserModel
import com.metehanbolat.gliserichomework.view.activity.LoginActivity

class ProfileFragmentViewModel: ViewModel() {

    val userData = MutableLiveData<UserModel>()

    fun exitApp(activity: Activity, auth: FirebaseAuth) {
        auth.signOut()
        Intent(activity.applicationContext, LoginActivity::class.java).apply {
            activity.startActivity(this)
            activity.finish()
        }
    }

    fun getUserDataFromFirebase(firestore: FirebaseFirestore, auth: FirebaseAuth) {
        auth.currentUser?.let {
            it.email?.let { userEmail ->
                firestore.collection(userEmail).document("userData").get().addOnSuccessListener { document ->
                    if (document != null) {
                        val data = document.data
                        data?.let { userFeatures ->
                            val name = userFeatures["name"] as String
                            val surname = userFeatures["surname"] as String
                            val phone = userFeatures["phone"] as String
                            val email = userFeatures["email"] as String
                            val password = userFeatures["password"] as String
                            userData.value = UserModel(name, surname, phone, email, password)
                        }
                    }else{
                        println("No Data")
                    }
                }.addOnFailureListener { e ->
                    println(e.localizedMessage)
                }
            }
        }

    }
}