package com.example.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSignUpBinding
import com.example.presentation.extensions.doOnTextChange
import com.example.presentation.extensions.obtainText
import com.example.presentation.extensions.showLog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SignUpFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setOnlyExpanded()
        doOnTextChange()
        //dialog?.setCancelable(false)
        return binding.root
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
        showLog(canEnableButton().toString())
    }

    private fun canEnableButton(): Boolean {
        if (getUser().isEmpty()) return false
        if (getPassword().isEmpty()) return false
        if (getConfirmPassword().isEmpty()) return false
        if (areEqualPasswords().not()) return false
        return true
    }

    private fun areEqualPasswords(): Boolean {
        if (getPassword() == getConfirmPassword()) return true
        return false
    }

    private fun getUser() = binding.inputUser.obtainText()
    private fun getPassword() = binding.inputPassword.obtainText()
    private fun getConfirmPassword() = binding.inputConfirmPassword.obtainText()
}