package com.fit.app.alina.viewModel

import android.content.Context
import android.telephony.PhoneNumberUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.app.alina.common.SingleLiveData
import com.fit.app.alina.data.local.DataImpl
import com.fit.app.alina.data.dataClasses.User
import kotlinx.coroutines.launch


class LoginViewModel(val context: Context) : ViewModel() {

    val isNeedToRegister = MutableLiveData<Boolean>()
    val isGoogleSignIn = MutableLiveData<Boolean>()
    val isOpenMainScreen = MutableLiveData<User>()
    val validationPhoneResult = SingleLiveData<String>()
    val validationAgeResult = SingleLiveData<String>()
    val validationHeightResult = SingleLiveData<String>()
    val validationCurrentWeightResult = SingleLiveData<String>()
    val validationDesireWeightResult = SingleLiveData<String>()
    var currentUser: User? = null

    fun onLoginButtonClicked(phone: String) {
        if (validatePhone(phone)) {
            checkCreatedUser(phone, false)
        }
    }

    fun signInThroughGoogle(email: String) {
        checkCreatedUser(email, true)
    }

    private fun checkCreatedUser(mainKey: String, withGoogle: Boolean) {
        viewModelScope.launch {
            val users = DataImpl(context).getAllUsers()
            for (i in users) {
                if (i?.key == mainKey) {
                    currentUser = i
                    isOpenMainScreen.postValue(currentUser)
                }

            }
            if (currentUser == null) {
                if (withGoogle) startNewUserRegistrationWithGoogle(mainKey)
                else startNewUserRegistration(mainKey)
            }
        }
    }

    private fun startNewUserRegistration(mainKey: String) {
        currentUser = User(key = mainKey, phone = mainKey)
        isNeedToRegister.postValue(true)
    }

    private fun startNewUserRegistrationWithGoogle(mainKey: String) {
        currentUser = User(key = mainKey, email = mainKey)
        isNeedToRegister.postValue(true)
    }

    private fun validatePhone(phone: String): Boolean {
        return if (phone == "" || !PhoneNumberUtils.isGlobalPhoneNumber(phone) || phone.length != 11) {
            validationPhoneResult.postValue("Введите валидный номер телефона")
            false
        } else true
    }

    fun onNextNameButtonClicked(name: String) {
        currentUser?.name = name
    }

    fun onNextGenderButtonClicked(gender: String) {
        currentUser?.gender = gender
    }

    fun onNextDataButtonClicked(
        age: String,
        height: String,
        currentWeight: String,
        desireWeight: String
    ) {
        if (validateUserData(age, height, currentWeight, desireWeight)) {
            currentUser?.currentWeight = currentWeight
            currentUser?.age = age
            currentUser?.height = height
            currentUser?.desiredWeight = desireWeight
            isOpenMainScreen.postValue(currentUser)
            viewModelScope.launch {
                DataImpl(context).insertUser(currentUser!!)
            }
        }
    }

    private fun validateUserData(
        age: String,
        height: String,
        currentWeight: String,
        desireWeight: String
    ): Boolean {
        when ("") {
            age -> {
                validationAgeResult.postValue("Введите валидный возраст")
                return false
            }

            height -> {
                validationHeightResult.postValue("Введите валидный рост")
                return false
            }

            currentWeight -> {
                validationCurrentWeightResult.postValue("Введите валидный текущий вес")
                return false
            }

            desireWeight -> {
                validationDesireWeightResult.postValue("Введите валидный желаемый вес")
                return false
            }

            else -> return true
        }
    }

    fun signInGoogleButtonClicked() {
        isGoogleSignIn.postValue(true)
    }
}