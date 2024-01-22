package com.fit.app.alina.viewModel

import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.app.alina.common.SingleLiveData
import com.fit.app.alina.data.dataClasses.User
import com.fit.app.alina.data.local.DataImpl
import com.fit.app.alina.data.remote.DataRemoteImpl
import com.fit.app.alina.ui.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class LoginViewModel(val activity: MainActivity) : ViewModel() {

    val isNeedToRegister = MutableLiveData<Boolean>()
    val isGoogleSignIn = MutableLiveData<Boolean>()
    val isOpenMainScreen = MutableLiveData<User>()
    val isOpenDialog = MutableLiveData<String>()
    val validationPhoneResult = SingleLiveData<String>()
    val validationAgeResult = SingleLiveData<String>()
    val validationHeightResult = SingleLiveData<String>()
    val validationCurrentWeightResult = SingleLiveData<String>()
    val validationDesireWeightResult = SingleLiveData<String>()
    var currentUser: User? = null

    var phone = ""

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
            val users = DataImpl(activity).getAllUsers()
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
        phone = mainKey
        checkSmsValidation()
    }

    fun smsValidated() {
        currentUser = User(key = phone, phone = phone)
        isNeedToRegister.postValue(true)
    }

    private fun startNewUserRegistrationWithGoogle(mainKey: String) {
        currentUser = User(key = mainKey, email = mainKey)
        isNeedToRegister.postValue(true)

    }

    private fun checkSmsValidation() {
        val randomNum = getRandomNumberString()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DataRemoteImpl.service.postSms("Ваш код подтверждения - $randomNum", phone).execute()
                Log.d("tag", randomNum)
                isOpenDialog.postValue(randomNum)
            }
        }
    }

    private fun getRandomNumberString(): String {
        val rnd = Random
        val number: Int = rnd.nextInt(999999)
        return String.format("%06d", number)
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
                DataImpl(activity).insertUser(currentUser!!)
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