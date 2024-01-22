package com.fit.app.alina

import android.telephony.PhoneNumberUtils
import android.telephony.PhoneNumberUtils.isGlobalPhoneNumber
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.fit.app.alina.common.SingleLiveData
import junit.framework.TestCase.assertFalse
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 **/
class ExampleUnitTest {

    @Test
    fun testGenerateNum() {
        val rnd = Random
        val number: Int = rnd.nextInt(999999)
        val formattedNumber = String.format("%06d", number)
        assertEquals(number.toString(), formattedNumber)
    }

}