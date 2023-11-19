package com.example.presentation.ui.lista

import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentLoginBinding
import com.example.presentation.ui.MainActivity

class ListFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_list) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.sismos)
    )

    override fun setUpUi() {

    }

}