package com.example.presentation.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.presentation.R
import com.example.presentation.utils.ResultData


fun <T> Fragment.observeResultGenericDb(
    liveData: LiveData<ResultData<T>>?,
    onError: (() -> Unit)? = null,
    errorDbMessage: String = getString(R.string.error_db),
    onSuccess: (data: T?) -> Unit,
) {
    if (liveData == null) return
    liveData.observe(viewLifecycleOwner) {
        shouldShowProgress(it is ResultData.Loading)
        when (it) {
            is ResultData.Error -> {
                if (onError == null) {
                    showErrorDb(
                        messageBody = getString(R.string.error_db)
                    )
                } else {
                    showErrorDb(
                        messageBody = errorDbMessage
                    )
                }
            }

            is ResultData.Success -> {
                onSuccess(it.data)
            }

            else -> {}
        }
    }
}