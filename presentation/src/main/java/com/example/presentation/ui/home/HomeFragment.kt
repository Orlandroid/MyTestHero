package com.example.presentation.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.os.BuildCompat
import androidx.navigation.fragment.findNavController
import com.example.data.preferences.HomePreferences
import com.example.domain.extensions.toJson
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.extensions.click
import com.example.presentation.extensions.onBackGesture
import com.example.presentation.extensions.showDateDialog
import com.example.presentation.extensions.showMessage
import com.example.presentation.extensions.showMessageAcceptCancel
import com.example.presentation.ui.MainActivity
import com.example.presentation.ui.lista.ListFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {


    @Inject
    lateinit var homePreferences: HomePreferences

    private val formatDate by lazy {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }
    private val formatDateService by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }
    private var dateForService = ""
    private var dateHomeView = ""

    override fun setUpUi() {
        with(binding) {
            if (dateHomeView.isNotEmpty()) {
                tvTodayDate.text = dateHomeView
            }
            btnSelecionarDate.click {
                showDateDialog {
                    tvTodayDate.text = formatDate.format(it.timeInMillis)
                    dateHomeView = formatDate.format(it.timeInMillis)
                    dateForService = formatDateService.format(it.timeInMillis)
                }
            }
            btnMakeQuery.click {
                makeQuery()
            }
            btnLastQuery.click {
                lastQuery()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                showMessageAcceptCancel(message = getString(R.string.close_session)) {
                    resetActivity()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
    }


    private fun resetActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun makeQuery() {
        if (dateForService.isEmpty()) {
            showMessage(getString(R.string.select_date))
            return
        }
        homePreferences.saveLastQuery(dateForService)
        navigateToListView(date = dateForService, ListFragment.KindOfQuery.REMOTE)
    }

    private fun lastQuery() {
        homePreferences.getLastQuery()?.let {
            navigateToListView(it, ListFragment.KindOfQuery.LOCAl)
            return
        }
    }

    private fun navigateToListView(date: String, myKindOfQuery: ListFragment.KindOfQuery) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToListFragment(
                ListFragment.ListArgs(
                    date = date, kindOfQuery = myKindOfQuery
                ).toJson()
            )
        )
    }


}