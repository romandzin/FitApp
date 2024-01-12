package com.fit.app.alina

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.fit.app.alina.common.SingleLiveData
import org.junit.Assert
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 **/
class ExampleUnitTest {

    @Test
    fun testSingleLiveEventOneValue() {

        val singleLiveData = SingleLiveData<Int>()
        val testArray = arrayListOf<Int>()
        singleLiveData.postValue(1)
        singleLiveData.observeForever {
            if (it != null) {
                testArray.add(it)
            }
        }
        singleLiveData.postValue(2)
        Assert.assertEquals(1, testArray.size)
    }
}