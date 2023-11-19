package com.example.presentation.extensions

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.alerts.MainAlert
import com.example.presentation.alerts.MainAlert.Companion.ERROR_MESSAGE
import com.example.presentation.alerts.MainAlert.Companion.SUCCESS_MESSAGE
import com.example.presentation.ui.MainActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar

fun Fragment.showLog(message: String, tag: String = javaClass.name) {
    Log.w(tag, message)
}

fun Fragment.showProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.hideProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.shouldShowProgress(isLoading: Boolean) {
    (requireActivity() as MainActivity).shouldShowProgress(isLoading)
}


fun Fragment.changeToolbarTitle(title: String) {
    (requireActivity() as MainActivity).changeTitleToolbar(title)
}


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}


fun Fragment.navigate(accion: NavDirections) {
    findNavController().navigate(accion)
}


fun Fragment.showErrorApi(shouldCloseTheViewOnApiError: Boolean = false) {
    val dialog = MainAlert(kindOfMessage = ERROR_MESSAGE,
        messageBody = getString(R.string.error_service),
        clickOnAccept = {
            if (shouldCloseTheViewOnApiError) {
                findNavController().popBackStack()
            }
        })
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showErrorNetwork(shouldCloseTheViewOnApiError: Boolean = false) {
    val dialog = MainAlert(kindOfMessage = ERROR_MESSAGE,
        messageBody = getString(R.string.verifica_conexion),
        clickOnAccept = {
            if (shouldCloseTheViewOnApiError) {
                findNavController().popBackStack()
            }
        })
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showMessage(message: String) {
    val dialog = MainAlert(
        kindOfMessage = SUCCESS_MESSAGE,
        messageBody = message
    )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}


@SuppressLint("SimpleDateFormat")
fun Fragment.showDateDialog(
    title: String = "Seleccione fecha",
    onPositiveButtonClickListener: (myCalendar: Calendar) -> Unit,
) {
    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    datePicker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        calendar.add(Calendar.DATE, 1)
        onPositiveButtonClickListener(calendar)
    }
    activity?.supportFragmentManager?.let { datePicker.show(it, "dateDialog") }
}
