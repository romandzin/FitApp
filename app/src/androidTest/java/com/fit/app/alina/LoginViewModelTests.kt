package com.fit.app.alina

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fit.app.alina.viewModel.LoginViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

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
        loginViewModel.onSignInButtonClicked("email")
        assertEquals("email", loginViewModel.currentUser.key)
    }

    @Test
    fun creatingKeyWithPhoneIsCorrect() {
        loginViewModel.onLoginButtonClicked(phone = "phoneNumber")
        assertEquals("phoneNumber", loginViewModel.currentUser.key)
    }
}