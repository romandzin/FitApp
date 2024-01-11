package com.fit.app.alina.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
    }

    private fun openMainScreen() {
        findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_enterDataFragment_to_mainScreenFragment)
        binding.profileIcon.setOnClickListener {
            mainViewModel.profileButtonClicked()
        }
        mainViewModel.profileOpen.observe(this) {
            moveToProfileFragment()
        }
    }

    private fun moveToEnterDataFragment() {
        findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_loginFragment_to_enterDataFragment)
    }

    private fun moveToProfileFragment() {
        findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_mainScreenFragment_to_profileFragment)
        binding.profileIcon.isVisible = false
        mainViewModel.notificationOpen.observe(this) {
            val navHostFragment: Fragment? =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
            Log.d("tag", navHostFragment!!.childFragmentManager.fragments[0]::class.toString())
            if (it != null)
            {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_profileFragment_to_notificationsFragment)
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
        findNavController(R.id.nav_host_fragment_container).popBackStack()
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
            Log.w("tag", "signInResult:failed code=" + e.localizedMessage)
        }
    }
}