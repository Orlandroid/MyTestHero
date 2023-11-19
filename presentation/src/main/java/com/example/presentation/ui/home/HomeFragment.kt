package com.example.presentation.ui.home

import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.extensions.click
import com.example.presentation.extensions.showDatePicker

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    //dd/mm/aaaa.
    override fun setUpUi() {
        with(binding) {
            tvTodayDate.click {

            }
            btnSelecionarDate.click {
                showDatePicker {
                    
                }
            }
        }
    }

}