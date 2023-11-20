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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
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

fun Fragment.showErrorDb(
    shouldCloseTheViewOnApiError: Boolean = false,
    messageBody: String = getString(R.string.error_db)
) {
    val dialog = MainAlert(kindOfMessage = ERROR_MESSAGE,
        messageBody = messageBody,
        clickOnAccept = {
        })
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showMessage(message: String, shouldClose: Boolean = false) {
    val dialog = MainAlert(
        kindOfMessage = SUCCESS_MESSAGE,
        messageBody = message,
        clickOnAccept = {
            if (shouldClose) {
                findNavController().popBackStack()
            }
        }
    )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}


@SuppressLint("SimpleDateFormat")
fun Fragment.showDateDialog(
    title: String = "Seleccione fecha",
    onPositiveButtonClickListener: (myCalendar: Calendar) -> Unit,
) {
    val today = Calendar.getInstance().timeInMillis
    val startDate = Calendar.getInstance()
    startDate.add(Calendar.YEAR, -5)
    val calendarConstraintBuilder = CalendarConstraints.Builder()
    calendarConstraintBuilder.setStart(startDate.timeInMillis)
    calendarConstraintBuilder.setEnd(today)

    //min and end date
    val endDate = Calendar.getInstance()
    endDate.add(Calendar.DATE, -1)
    val dateValidatorMin = DateValidatorPointForward.from(startDate.timeInMillis)
    val dateValidatorMax = DateValidatorPointBackward.before(endDate.timeInMillis)
    val listValidators = ArrayList<DateValidator>()
    listValidators.add(dateValidatorMin)
    listValidators.add(dateValidatorMax)
    val validators = CompositeDateValidator.allOf(listValidators)
    calendarConstraintBuilder.setValidator(validators)

    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setCalendarConstraints(calendarConstraintBuilder.build())
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
