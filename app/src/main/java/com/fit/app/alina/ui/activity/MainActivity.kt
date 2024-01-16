package com.fit.app.alina.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fit.app.alina.R
import com.fit.app.alina.databinding.ActivityMainBinding
import com.fit.app.alina.ui.fragment.MainScreenFragment
import com.fit.app.alina.ui.fragment.ProfileFragment
import com.fit.app.alina.viewModel.LoginViewModel
import com.fit.app.alina.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_container)
    }

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
        initOneSignal()
    }

    private fun initView() {
        loginViewModel.isNeedToRegister.observe(this) {
            moveToEnterDataFragment()
        }
        loginViewModel.isGoogleSignIn.observe(this) {
            signInGoogle()
        }
        loginViewModel.isOpenMainScreen.observe(this) {
            openMainScreen()
            binding.profileIcon.isVisible = true
        }
        binding.bottomLayout.setupWithNavController(navController)
        binding.bottomLayout.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainScreenFragment -> {
                    binding.profileIcon.isVisible = true
                    navController.navigate(it.itemId)
                }
                R.id.trainer -> {
                    val telegramIntent = Intent(Intent.ACTION_VIEW)
                    telegramIntent.setData(Uri.parse("http://telegram.me/petrova_alina_fitness"))
                    startActivity(telegramIntent)
                }
                R.id.profileFragment -> {
                    navController.navigate(it.itemId)
                    binding.profileIcon.isVisible = false
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initOneSignal() {
        OneSignal.Debug.logLevel = LogLevel.VERBOSE
        OneSignal.initWithContext(this, "bd2fd052-d5ff-40d7-bed2-068de8f060fd")
    }

    private fun openMainScreen() {
        binding.bottomLayout.isVisible = true
        navController.navigate(R.id.mainScreenFragment)
        binding.profileIcon.setOnClickListener {
            mainViewModel.profileButtonClicked()
        }
        mainViewModel.profileOpen.observe(this) {
            moveToProfileFragment()
        }
        binding.profileIcon.isVisible = true
    }

    private fun moveToEnterDataFragment() {
        navController.navigate(R.id.action_loginFragment_to_enterDataFragment)
    }

    private fun moveToProfileFragment() {
        navController.navigate(R.id.action_mainScreenFragment_to_profileFragment)
        binding.profileIcon.isVisible = false
    }

    fun subscribeToNotification() {
        mainViewModel.notificationOpen.observe(this) {
            if (it != null)
            {
                navController.navigate(R.id.action_profileFragment_to_notificationsFragment)
            }
            mainViewModel.notificationOpen.removeObservers(this)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)

        if (navHostFragment!!.childFragmentManager.fragments[0]::class == MainScreenFragment::class)
        {
            mainViewModel.closeStage()
        }
        else {
            if (navHostFragment.childFragmentManager.fragments[0]::class == ProfileFragment::class) binding.profileIcon.isVisible = true
            goBackOnGraph()
        }
    }

    private fun goBackOnGraph() {
        navController.popBackStack()
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
            loginViewModel.signInThroughGoogle(account.email.toString())
        } catch (e: ApiException) {
            Log.w("tag", "signInResult:failed code=" + e.localizedMessage)
        }
    }
}