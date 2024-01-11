package com.fit.app.alina.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.app.alina.data.local.DataImpl
import com.fit.app.alina.data.User
import kotlinx.coroutines.launch


class LoginViewModel(val context: Context): ViewModel() {

    val isLoggedIn = MutableLiveData<Boolean>()
    val isGoogleSignIn = MutableLiveData<Boolean>()
    val isDataEntered = MutableLiveData<Boolean>()
    lateinit var currentUser: User

    init {
        viewModelScope.launch {
            DataImpl(context).deleteAll()
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
        isDataEntered.postValue(true)
        viewModelScope.launch {
            DataImpl(context).insertUser(currentUser)
        }
    }

    fun onSignInButtonClicked() {
        isGoogleSignIn.postValue(true)
    }
}