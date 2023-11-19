package com.example.presentation.ui.home

import androidx.navigation.fragment.findNavController
import com.example.data.preferences.HomePreferences
import com.example.domain.extensions.toJson
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.extensions.click
import com.example.presentation.extensions.showDateDialog
import com.example.presentation.extensions.showMessage
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

    override fun setUpUi() {
        with(binding) {
            btnSelecionarDate.click {
                showDateDialog {
                    tvTodayDate.text = formatDate.format(it.timeInMillis)
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

    private fun makeQuery() {
        if (getDateSelectByUser().isEmpty()) {
            showMessage(getString(R.string.select_date))
            return
        }
        homePreferences.saveLastQuery(getDateSelectByUser())
        navigateToListView(date = getDateSelectByUser())
    }

    private fun lastQuery() {
        homePreferences.getLastQuery()?.let {
            navigateToListView(it)
            return
        }
        showMessage(getString(R.string.not_imformation))
    }

    private fun navigateToListView(date: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToListFragment(
                ListFragment.ListArgs(date = date).toJson()
            )
        )
    }

    private fun getDateSelectByUser() = binding.tvTodayDate.text.toString()

}