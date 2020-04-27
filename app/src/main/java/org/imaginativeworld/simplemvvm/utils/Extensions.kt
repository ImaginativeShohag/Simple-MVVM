package org.imaginativeworld.simplemvvm.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.DialogSingleInputBinding


/**
 * This method converts device specific pixels to density independent pixels.
 */
fun Int.pxToDp(context: Context): Int {
    return (this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}


/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 */
fun Int.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}


/**
 * This method converts sp unit to equivalent pixels, depending on device density.
 */
fun Int.spToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}


/**
 * Get dimension attributes value
 *
 * Example:
 * getAttrDimension(R.attr.actionBarSize)
 */
fun Context.getAttrDimension(@AttrRes attr: Int): Int {
    val dimenAttr = intArrayOf(attr)
    val typedValue = TypedValue()
    val typedArray = this.obtainStyledAttributes(typedValue.data, dimenAttr)
    val dimenInPixel = typedArray.getDimensionPixelSize(0, -1)
    typedArray.recycle()
    return dimenInPixel
}


/**
 * Hide soft keyboard
 */
fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}




/**
 * Show default Alert Dialog.
 */
fun Activity.showAlertDialog(
    title: String,
    message: String,
    positiveBtnText: String,
    positiveListener: DialogInterface.OnClickListener,
    negativeBtnText: String? = null,
    negativeListener: DialogInterface.OnClickListener? = null
) {
    if (this.isFinishing) {
        return
    }

    AlertDialog.Builder(this)
        .apply {
            setTitle(title)
            setMessage(message)

            setPositiveButton(positiveBtnText, positiveListener)

            if (negativeBtnText != null) {
                setNegativeButton(negativeBtnText, negativeListener)
            }

            show()
        }

}


/**
 * Fix the dialog height.
 */
fun Window.setDialogMaxWidthAndMinHeight() {
    setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
}


/**
 * Show a single input dialog.
 */
fun Activity.showDialogSingleInput(
    title: String,
    inputHint: String,
    emptyInputText: String,
    positiveBtnText: String,
    positiveListener: (inputText: String) -> Unit,
    negativeBtnText: String,
    negativeListener: () -> Unit,
    inputFieldCustomization: (editText: TextInputEditText) -> Unit
) {

    if (this.isFinishing) return

    val dialogBinding = DialogSingleInputBinding.inflate(LayoutInflater.from(this))

    val builder = android.app.AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
        .setView(dialogBinding.root)
        // Note: This is an input dialog, so auto cancel should be disabled.
        .setCancelable(false)

    val alertDialog = builder.create()


    // init Views
    inputFieldCustomization(dialogBinding.etInput)

    dialogBinding.tvTitle.text = title
    dialogBinding.tilInput.hint = inputHint
    dialogBinding.btnNegative.text = negativeBtnText
    dialogBinding.btnPositive.text = positiveBtnText


    // Listener
    dialogBinding.btnNegative.setOnClickListener {

        negativeListener()

        alertDialog.dismiss()

    }

    dialogBinding.btnPositive.setOnClickListener {

        dialogBinding.etInput.error = null

        if (!dialogBinding.etInput.text.isNullOrBlank()) {

            positiveListener(dialogBinding.etInput.text.toString())

            alertDialog.dismiss()

        } else {

            dialogBinding.etInput.error = emptyInputText
            dialogBinding.etInput.requestFocus()

        }

    }

    alertDialog.show()

    alertDialog.window?.setDialogMaxWidthAndMinHeight()
}
