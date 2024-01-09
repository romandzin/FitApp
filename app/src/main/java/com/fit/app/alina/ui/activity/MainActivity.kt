package com.fit.app.alina.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.fit.app.alina.databinding.ActivityMainBinding
import com.fit.app.alina.ui.fragment.EnterDataFragment
import com.fit.app.alina.ui.fragment.LoginFragment
import com.fit.app.alina.ui.fragment.MainScreenFragment
import com.fit.app.alina.viewModel.LoginViewModel
import com.fit.app.alina.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ru.tinkoff.acquiring.sdk.AcquiringSdk
import ru.tinkoff.acquiring.sdk.TinkoffAcquiring
import ru.tinkoff.acquiring.sdk.models.enums.CheckType
import ru.tinkoff.acquiring.sdk.models.options.screen.PaymentOptions
import ru.tinkoff.acquiring.sdk.redesign.mainform.MainFormLauncher
import ru.tinkoff.acquiring.sdk.utils.Money
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val loginViewModel: LoginViewModel by lazy {
        LoginViewModel(this)
    }
    val mainViewModel: MainViewModel by lazy {
        MainViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, LoginFragment()).commit()
        loginViewModel.isLoggedIn.observe(this) {
            moveToEnterDataFragment()
        }
        loginViewModel.isGoogleSignIn.observe(this) {
            signInGoogle()
        }
        loginViewModel.isDataEntered.observe(this) {
            openMainScreen()
            binding.profileIcon.isVisible = true
        }
        binding.profileIcon.setOnClickListener {
            mainViewModel.profileButtonClicked()
        }
        mainViewModel.profileOpen.observe(this) {
            supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, MainScreenFragment()).commit()
        }
    }

    private fun openMainScreen() {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, MainScreenFragment()).commit()
    }

    private fun moveToEnterDataFragment() {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, EnterDataFragment()).commit()
    }

    override fun onBackPressed() {
        mainViewModel.stage.observe(this) {
            if (it < 1) super.onBackPressed()
        }
        mainViewModel.closeStage()
    }

    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        ActivityCompat.startActivityForResult(this, signInIntent, 500, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 500) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            Log.d("tag", completedTask.exception.toString())
            val account = completedTask.getResult(ApiException::class.java)
            loginViewModel.onSignInButtonClicked(account.email.toString())
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("tag", "signInResult:failed code=" + e.localizedMessage)
        }
    }
}