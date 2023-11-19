package com.example.presentation.ui.login

import androidx.navigation.fragment.findNavController
import com.example.data.preferences.LoginPreferences
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentLoginBinding
import com.example.presentation.extensions.click
import com.example.presentation.extensions.doOnTextChange
import com.example.presentation.extensions.gone
import com.example.presentation.extensions.obtainText
import com.example.presentation.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    @Inject
    lateinit var loginPreferences: LoginPreferences

    override fun setUpUi() {
        doOnTextChange()
        binding.buttonRegistarse.click {
            if (isValidAccount()) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            } else {
                binding.tvError.visible()
            }
        }
    }

    private fun doOnTextChange() = with(binding) {
        inputUser.editText?.doOnTextChange {
            checkEnableButton()
        }
        inputPassword.editText?.doOnTextChange {
            checkEnableButton()
        }
    }

    private fun checkEnableButton() {
        binding.buttonRegistarse.isEnabled = canEnableButton()
    }

    private fun canEnableButton(): Boolean {
        binding.tvError.gone()
        if (getUser().isEmpty()) return false
        if (getPassword().isEmpty()) return false
        return true
    }

    private fun isValidAccount(): Boolean {
        if (areUsersEquals() && arePasswordsEquals()) return true
        return false
    }

    private fun arePasswordsEquals(): Boolean {
        val user = getDbUser() ?: return false
        if (user.password == getPassword()) return true
        return false
    }


    private fun areUsersEquals(): Boolean {
        val user = getDbUser() ?: return false
        if (user.user == getUser()) return true
        return false
    }

    private fun getDbUser() = loginPreferences.getUser()


    private fun getUser() = binding.inputUser.obtainText()
    private fun getPassword() = binding.inputPassword.obtainText()

}