package com.fit.app.alina.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T> : MutableLiveData<T?>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        super.observe(owner) { t ->
            if (t != null) {
                observer.onChanged(t)
                this@SingleLiveData.removeObservers(owner)
                this@SingleLiveData.observe(owner, observer)
            }
        }
    }

    override fun observeForever(observer: Observer<in T?>) {
        super.observeForever(observer)
        this@SingleLiveData.removeObserver(observer)
        this@SingleLiveData.observeForever(observer)
    }
}