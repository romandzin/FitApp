package com.fit.app.alina.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.app.alina.common.SingleLiveData
import com.fit.app.alina.data.dataClasses.Notification
import com.fit.app.alina.data.local.DataImpl
import com.fit.app.alina.data.dataClasses.User
import com.fit.app.alina.ui.activity.MainActivity
import kotlinx.coroutines.launch

class MainViewModel(val activity: MainActivity) : ViewModel() {

    val userData = MutableLiveData<User?>()
    val profileOpen = MutableLiveData<Boolean>()
    val notificationOpen = SingleLiveData<List<Notification>>()
    private var currentUser: User? = null
    private val dataRepoImpl = DataImpl(activity)
    var stage = MutableLiveData(1)

    init {
        refreshUser()
    }

    private fun refreshUser() {
        viewModelScope.launch {
            currentUser = activity.currentUser  //Todo переписать метод обновления
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
        refreshUser()
        profileOpen.postValue(true)
    }

    fun notificationButtonClicked() {
        viewModelScope.launch {
            val notifications = dataRepoImpl.getListOfNotifications()
            notificationOpen.postValue(notifications)
        }

    }
}