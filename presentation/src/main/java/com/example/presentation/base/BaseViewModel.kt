package com.example.presentation.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.helpers.NetworkHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import com.example.data.di.CoroutineDispatchers
import com.example.domain.state.Result
import com.example.presentation.utils.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel constructor(
    protected val coroutineDispatchers: CoroutineDispatchers,
    val networkHelper: NetworkHelper
) : ViewModel() {

    private var job: Job? = null

    enum class ErrorType {
        NETWORK,
        TIMEOUT,
        UNKNOWN
    }

    suspend inline fun <T> safeApiCall(
        result: MutableLiveData<Result<T>>,
        coroutineDispatchers: CoroutineDispatchers,
        crossinline apiToCall: suspend () -> Unit,
    ) {
        viewModelScope.launch(coroutineDispatchers.io) {
            try {
                withContext(coroutineDispatchers.main) {
                    result.value = Result.Loading
                }
                if (!networkHelper.isNetworkConnected()) {
                    result.value = Result.ErrorNetwork("")
                    return@launch
                }
                apiToCall()
            } catch (e: Exception) {
                withContext(coroutineDispatchers.main) {
                    e.printStackTrace()
                    Log.e("ApiCalls", "Call error: ${e.localizedMessage} code:$e", e.cause)
                    when (e) {
                        is HttpException -> {
                            val errorBody = e.response()?.errorBody()
                            val errorCode = e.response()?.code()
                            result.value = Result.Error(
                                error = e.message(),
                                errorCode = errorCode ?: -1,
                                errorBody = errorBody.toString()
                            )
                            Log.w("Call error :", "code:$errorCode")
                        }

                        is SocketTimeoutException -> result.value =
                            Result.Error(ErrorType.TIMEOUT.name)

                        is IOException -> result.value = Result.Error(ErrorType.NETWORK.name)
                        else -> result.value = Result.Error(ErrorType.UNKNOWN.name)
                    }
                }
            }
        }
    }


    suspend inline fun <T> safeDbOperation(
        result: MutableLiveData<ResultData<T>>,
        crossinline dbOperation: suspend () -> Unit,
    ) {
        try {
            withContext(Dispatchers.Main) {
                result.value = ResultData.Loading()
            }
            withContext(Dispatchers.IO) {
                dbOperation()
            }
            result.value = null
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                result.value = ResultData.Error(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}



