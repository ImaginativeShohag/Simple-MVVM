/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.databinding.DialogMonthPickerBinding
import org.imaginativeworld.simplemvvm.databinding.DialogSingleInputBinding
import java.util.*

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
 * Show default Alert Dialog.
 */
fun Activity.showAlertDialog(
    title: String?,
    message: String?,
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
            if (title != null) {
                setTitle(title)
            }

            if (message != null) {
                setMessage(message)
            }

            setPositiveButton(positiveBtnText, positiveListener)

            if (negativeBtnText != null) {
                setNegativeButton(negativeBtnText, negativeListener)
            }

            show()
        }
}

/**
 * Show a single input dialog.
 *
 * @param title Title text of the input box.
 * @param inputHint Input field hint text.
 * @param emptyInputText Empty input field validation message.
 * @param positiveBtnText Positive button text.
 * @param positiveListener This lambda function will invoke if the user clicked positive button.
 * @param negativeBtnText Negative button text.
 * @param negativeListener This lambda function will invoke if the user clicked negative button.
 * @param inputFieldCustomization Customize the input field using this lambda function.
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
): AlertDialog? {

    if (this.isFinishing) return null

    val dialogBinding = DialogSingleInputBinding.inflate(LayoutInflater.from(this))

    val builder = AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
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

    return alertDialog
}

/**
 * Show a single input dialog.
 */
fun Context.showMonthPickerDialog(
    month: Int = -1,
    year: Int = -1,
    listener: (year: Int, month: Int) -> Unit
) {

    val binding = DialogMonthPickerBinding.inflate(LayoutInflater.from(this))

    val builder = AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
        .setView(binding.root)
        .setPositiveButton("Ok") { _, _ ->
            listener(binding.pickerYear.value, binding.pickerMonth.value)
        }
        .setNegativeButton("Cancel") { _, _ ->
            // ...
        }

    val alertDialog = builder.create()

    val calendar = Calendar.getInstance()

    binding.pickerMonth.minValue = 0
    binding.pickerMonth.maxValue = 11
    if (month == -1) {
        binding.pickerMonth.value = calendar.get(Calendar.MONTH)
    } else {
        binding.pickerMonth.value = month
    }
    binding.pickerMonth.displayedValues = arrayOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "June",
        "July",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec"
    )

    binding.pickerYear.minValue = 1950
    binding.pickerYear.maxValue = 2099
    if (year == -1) {
        binding.pickerYear.value = calendar.get(Calendar.YEAR)
    } else {
        binding.pickerYear.value = year
    }

    alertDialog.show()
}

fun Int.getMonthName(): String {
    try {
        return arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "June",
            "July",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )[this]
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error"
    }
}

/**
 * Ask to open a url.
 *
 * @param url Website URL.
 */
fun Activity.askAndOpenUrl(url: String) {
    showAlertDialog(
        null,
        "Do you want to go to this link― $url ?",
        "Yes",
        { _, _ ->
            openUrl(url)
        },
        "No",
        { _, _ ->
            /* no-op */
        }
    )
}

/**
 * Ask to a phone call.
 */
fun Activity.askAndStartPhoneCall(number: String) {
    showAlertDialog(
        null,
        "Do you want to call this number― $number ?",
        "Yes",
        { _, _ ->
            startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:$number")
                )
            )
        },
        "No",
        { _, _ ->
            /* no-op */
        }
    )
}

/**
 * Ask to a open email client.
 */
fun Activity.askAndOpenEmailClient(
    fromEmail: String,
    subject: String,
    body: String = ""
) {
    showAlertDialog(
        null,
        "Do you want to send email here― $fromEmail ?",
        "Yes",
        { _, _ ->
            sendEmail(
                fromEmail,
                subject,
                body
            )
        },
        "No",
        { _, _ ->
            /* no-op */
        }
    )
}
