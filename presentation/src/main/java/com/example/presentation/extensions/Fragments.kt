package com.example.presentation.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.alerts.MainAlert
import com.example.presentation.alerts.MainAlert.Companion.ERROR_MESSAGE
import com.example.presentation.ui.MainActivity
import com.example.presentation.utils.getConstraints
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

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

fun Fragment.showDatePicker(
    titleText: String = "Selecciona fecha",
    constraints: CalendarConstraints = getConstraints(),
    currentDate: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
    onOkButtonClickListener: (myCalendar: Calendar) -> Unit,
) {
    val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(titleText)
        .setCalendarConstraints(constraints).setSelection(currentDate.timeInMillis).build()
    datePicker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        onOkButtonClickListener(calendar)
    }
    fragmentManager?.let { datePicker.show(it, "DATE_DIALOG") }
}


fun Fragment.showPickerTime(hourToShowOnTimer: Date, onTimeSet: (Int, Int) -> Unit) {
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(hourToShowOnTimer.hours).setMinute(hourToShowOnTimer.minutes)
        .setTitleText("Selecciona hora").build()
    picker.addOnPositiveButtonClickListener {
        val hour = picker.hour
        val min = picker.minute
        onTimeSet(hour, min)
    }
    activity?.supportFragmentManager?.let { picker.show(it, "time") }
}