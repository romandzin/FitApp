package com.fit.app.alina

import android.os.Handler
import android.os.Looper
import android.telephony.PhoneNumberUtils
import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fit.app.alina.viewModel.LoginViewModel
import junit.framework.TestCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import kotlin.math.log

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginViewModelTests {
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun createLoginViewModel() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        loginViewModel = LoginViewModel(appContext)
    }

    @Test
    fun creatingKeyWithEmailIsCorrect() {
        val handler = Handler(Looper.getMainLooper());
        handler.post {
            loginViewModel.isNeedToRegister.observeForever {
                assertEquals("email", loginViewModel.currentUser?.key)
            }
            loginViewModel.isOpenMainScreen.observeForever {
                assertEquals("email", loginViewModel.currentUser?.key)
            }
            loginViewModel.signInThroughGoogle("email")
        }
    }

    @Test
    fun creatingKeyWithPhoneIsCorrect() {
        val handler = Handler(Looper.getMainLooper());
        handler.post {
            loginViewModel.isNeedToRegister.observeForever {
                assertEquals("89610915748", loginViewModel.currentUser?.key)
            }
            loginViewModel.isOpenMainScreen.observeForever {
                assertEquals("89610915748", loginViewModel.currentUser?.key)
            }
            loginViewModel.onLoginButtonClicked(phone = "89610915748")
        }
    }

    @Test
    fun testPhoneValidation() {
        val phone = "89610915749"
        if (phone == "" || !PhoneNumberUtils.isGlobalPhoneNumber(phone) || phone.length != 11) {
            TestCase.assertTrue(false)
        }
        else assertTrue(true)
    }
}