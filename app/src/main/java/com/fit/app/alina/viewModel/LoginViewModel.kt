package com.fit.app.alina.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.app.alina.data.local.DataRepoImpl
import com.fit.app.alina.data.User
import kotlinx.coroutines.launch


class LoginViewModel(val context: Context): ViewModel() {

    val isLoggedIn = MutableLiveData<Boolean>()
    val isGoogleSignIn = MutableLiveData<Boolean>()
    val isDataEntered = MutableLiveData<Boolean>()
    lateinit var currentUser: User

    init {
        viewModelScope.launch {
            DataRepoImpl(context).deleteAll()
        }
    }
    fun onLoginButtonClicked(phone: String) {
        isLoggedIn.postValue(true)
        currentUser = User(key = phone, phone = phone)
    }

    fun onSignInButtonClicked(email: String) {
        isLoggedIn.postValue(true)
        currentUser = User(key = email, email = email)
    }

    fun onNextNameButtonClicked(name: String) {
        currentUser.name = name
    }

    fun onNextGenderButtonClicked(gender: String) {
        currentUser.gender = gender
    }

    fun onNextDataButtonClicked(age: String, height: String, currentWeight: String, desireWeight: String) {
        currentUser.currentWeight = currentWeight
        currentUser.age = age
        currentUser.height = height
        currentUser.desiredWeight = desireWeight
        Log.d("tag", currentUser.toString())
        isDataEntered.postValue(true)
        viewModelScope.launch {
            DataRepoImpl(context).insertUser(currentUser)
        }
    }

    fun onSignInButtonClicked() {
        isGoogleSignIn.postValue(true)
    }
}