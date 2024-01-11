package com.fit.app.alina.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.app.alina.common.SingleLiveData
import com.fit.app.alina.data.local.DataImpl
import com.fit.app.alina.data.User
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {

    val userData = MutableLiveData<User?>()
    val profileOpen = MutableLiveData<Boolean>()
    val notificationOpen = SingleLiveData<List<String>>()
    private var currentUser: User? = null
    private val dataRepoImpl = DataImpl(context)
    var stage = MutableLiveData(1)

    init {
        refreshUser()
    }

    private fun refreshUser() {
        viewModelScope.launch {
            currentUser = dataRepoImpl.getUser()
            userData.postValue(currentUser)
        }
    }

    fun openNewStage() {
        stage.postValue(stage.value?.plus(1))
    }

    fun closeStage() {
        if (stage.value != 1) stage.postValue(stage.value?.minus(1))
    }

    fun subscribedButtonClicked() {
        if (currentUser!!.isSubscribed) currentUser!!.isSubscribed = false
        else currentUser!!.isSubscribed = true
        userData.postValue(currentUser)
    }

    fun profileButtonClicked() {
        profileOpen.postValue(true)
    }

    fun notificationButtonClicked() {
        viewModelScope.launch {
            val notifications = dataRepoImpl.getListOfNotifications()
            notificationOpen.postValue(notifications)
        }

    }
}