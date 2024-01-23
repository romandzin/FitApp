package com.fit.app.alina.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.app.alina.common.SingleLiveData
import com.fit.app.alina.data.dataClasses.Notification
import com.fit.app.alina.data.local.DataImpl
import com.fit.app.alina.data.dataClasses.User
import com.fit.app.alina.data.dataClasses.Video
import com.fit.app.alina.ui.activity.MainActivity
import kotlinx.coroutines.launch

class MainViewModel(val activity: MainActivity) : ViewModel() {

    val userData = MutableLiveData<User?>()
    val profileOpen = MutableLiveData<Boolean>()
    val notificationOpen = SingleLiveData<List<Notification>>()
    val currentOpenedVideo = MutableLiveData<Video>()

    private var currentUser: User? = null
    private val dataRepoImpl = DataImpl(activity)
    var stage = MutableLiveData(1)

    init {
        refreshUser()
    }

    private fun refreshUser() {
        viewModelScope.launch {
            currentUser = activity.currentUser //TODO сделать сохранение пользователя
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
        currentUser!!.isSubscribed = !currentUser!!.isSubscribed
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

    fun openVideoScreen(video: Video) {
        currentOpenedVideo.postValue(video)
    }

    fun mainScreenOpened() {
        refreshUser()
    }
}