package com.example.presentation.ui.splashscreen

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.data.preferences.LoginPreferences
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen) {

    @Inject
    lateinit var loginPreferences: LoginPreferences
    override fun setUpUi() {
        launchSplashScreen()
    }

    private fun launchSplashScreen() {
        lifecycleScope.launch {
            startAnimation()
            delay(3.seconds)
            navigateToNextView()
        }
    }

    private fun navigateToNextView() {
        loginPreferences.getUser()?.let {
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
            return
        }
        findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToSignUpFragment())
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.imageSplash.startAnimation(animation)
    }

}