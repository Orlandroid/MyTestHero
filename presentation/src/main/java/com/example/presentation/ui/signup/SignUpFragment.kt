package com.example.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.data.preferences.LoginPreferences
import com.example.domain.entities.local.MyUser
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSignUpBinding
import com.example.presentation.extensions.click
import com.example.presentation.extensions.doOnTextChange
import com.example.presentation.extensions.gone
import com.example.presentation.extensions.obtainText
import com.example.presentation.extensions.visible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSignUpBinding

    @Inject
    lateinit var loginPreferences: LoginPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        setOnlyExpanded()
        doOnTextChange()
        binding.buttonRegistarse.click {
            loginPreferences.saveUser(getMyUser())
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
    }

    private fun setOnlyExpanded() {
        dialog?.setOnShowListener {
            val bottomSheet: View? =
                (it as BottomSheetDialog).findViewById(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
        }
    }

    private fun doOnTextChange() = with(binding) {
        inputUser.editText?.doOnTextChange {
            checkEnableButton()
        }
        inputPassword.editText?.doOnTextChange {
            checkEnableButton()
        }
        inputConfirmPassword.editText?.doOnTextChange {
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
        if (getConfirmPassword().isEmpty()) return false
        if (areEqualPasswords().not()) {
            binding.tvError.visible()
            return false
        }
        return true
    }

    private fun areEqualPasswords(): Boolean {
        if (getPassword() == getConfirmPassword()) return true
        return false
    }

    private fun getUser() = binding.inputUser.obtainText()
    private fun getPassword() = binding.inputPassword.obtainText()
    private fun getConfirmPassword() = binding.inputConfirmPassword.obtainText()

    private fun getMyUser() = MyUser(
        user = getUser(), password = getPassword()
    )
}