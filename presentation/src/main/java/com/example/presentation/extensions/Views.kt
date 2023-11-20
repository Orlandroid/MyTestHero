package com.example.presentation.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputLayout


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.changeDrawableColor(color: Int) {
    this.setColorFilter(resources.getColor(color))
}

fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}

fun View.getColor(@ColorRes color: Int): Int {
    return this.context.resources.getColor(color)
}

fun View.navigate(action: NavDirections) {
    findNavController().navigate(action)
}

fun View.takeScreenshot(): Bitmap {
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val bgDrawable = this.background
    if (bgDrawable != null) {
        bgDrawable.draw(canvas)
    } else {
        canvas.drawColor(Color.WHITE)
    }
    this.draw(canvas)
    return bitmap
}


fun EditText.obtainText(): String {
    return text.toString().trim()
}

fun TextInputLayout.obtainText(): String {
    return editText?.text.toString().trim()
}

fun EditText.doOnTextChange(onTextChange: () -> Unit) {
    doOnTextChanged { text, start, before, count ->
        onTextChange()
    }
}

fun TextView.setTextFromHtml(textHtml: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(textHtml, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(textHtml)
    }
}


