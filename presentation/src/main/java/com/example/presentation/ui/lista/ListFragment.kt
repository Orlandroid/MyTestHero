package com.example.presentation.ui.lista

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.domain.entities.local.Earthquake
import com.example.domain.extensions.fromJson
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentListBinding
import com.example.presentation.extensions.observeApiResult
import com.example.presentation.extensions.observeResultGenericDb
import com.example.presentation.extensions.showLog
import com.example.presentation.extensions.showMessage
import com.example.presentation.ui.MainActivity
import com.example.presentation.ui.toEarthquakes
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val args: ListFragmentArgs by navArgs()
    private val viewModel: ListViewModel by viewModels()
    private val adapter = ListAdapter { clickOnEarthquake(it) }
    private val formatDateService by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    data class ListArgs(
        val date: String,
        val kindOfQuery: KindOfQuery
    )

    enum class KindOfQuery {
        REMOTE,
        LOCAl
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.sismos)
    )

    override fun setUpUi() {
        binding.recycler.adapter = adapter
        args.listArgs.fromJson<ListArgs>().let {
            when (it.kindOfQuery) {
                KindOfQuery.REMOTE -> {
                    viewModel.getEarthquakes(it.date, it.date)
                }

                KindOfQuery.LOCAl -> {
                    viewModel.getEarthquakesFromDb()
                }
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResult(viewModel.earthquakesResponse) {
            adapter.setData(it.features.toEarthquakes())
        }
        observeResultGenericDb(viewModel.earthquakesDbFromDb) {
            it?.let {
                if (it.isEmpty()) {
                    showMessage(message = getString(R.string.not_imformation))
                } else {
                    adapter.setData(it)
                }
            }
        }
    }

    private fun clickOnEarthquake(earthquake: Earthquake) {

    }

    private fun getStartDate(): String {
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.YEAR, -5)
        val myStartDate = formatDateService.format(startDate.timeInMillis)
        showLog(myStartDate)
        return myStartDate
    }


}