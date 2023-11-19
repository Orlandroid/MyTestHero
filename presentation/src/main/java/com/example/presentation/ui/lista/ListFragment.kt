package com.example.presentation.ui.lista

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.domain.entities.local.Earthquake
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentListBinding
import com.example.presentation.extensions.observeApiResult
import com.example.presentation.ui.MainActivity
import com.example.presentation.ui.toEarthquakes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val args: ListFragmentArgs by navArgs()
    private val viewModel: ListViewModel by viewModels()
    private val adapter = ListAdapter { clickOnEarthquake(it) }

    data class ListArgs(
        val date: String
    )

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.sismos)
    )

    override fun setUpUi() {
        binding.recycler.adapter = adapter
        //viewModel.getEarthquakes(args.listArgs.fromJson<ListArgs>().date)
        viewModel.getEarthquakes("2014-01-01", "2014-01-02")
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResult(viewModel.earthquakesResponse) {
            adapter.setData(it.features.toEarthquakes())
        }
    }

    private fun clickOnEarthquake(earthquake: Earthquake) {

    }

}