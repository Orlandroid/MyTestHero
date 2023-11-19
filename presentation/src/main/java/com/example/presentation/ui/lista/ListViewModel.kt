package com.example.presentation.ui.lista

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
import com.example.domain.entities.remote.EarthquakesResponse
import com.example.domain.state.Result
import com.example.presentation.base.BaseViewModel
import com.example.presentation.helpers.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository,
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper
) : BaseViewModel(coroutineDispatchers, networkHelper) {


    private val _earthquakes = MutableLiveData<Result<EarthquakesResponse>>()
    val earthquakesResponse = _earthquakes


    fun getEarthquakes(startDate: String, endDte: String) {
        viewModelScope.launch {
            safeApiCall(_earthquakes, coroutineDispatchers) {
                val response = repository.getEarthquakes(startDate, endDte)
                withContext(Dispatchers.Main) {
                    _earthquakes.value = Result.Success(response)
                }
            }
        }
    }

}