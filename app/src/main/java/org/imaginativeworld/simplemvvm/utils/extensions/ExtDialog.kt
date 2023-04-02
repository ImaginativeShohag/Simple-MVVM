/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
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
import org.imaginativeworld.simplemvvm.databinding.DialogTimePickerBinding
import java.util.*

/**
 * Fix the dialog height.
 */
fun Window.setDialogMaxWidthAndMinHeight() {
    setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
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
    negativeListener: DialogInterface.OnClickListener? = null,
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
    inputFieldCustomization: (editText: TextInputEditText) -> Unit,
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
    listener: (year: Int, month: Int) -> Unit,
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
        "Dec",
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
            "Dec",
        )[this]
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error"
    }
}

interface TimePickerListener {
    fun onSuccess(hour: Int, min: Int)
}

/**
 * Show a time picker with interval 30 minutes.
 *
 *                           AM                  |                         PM                      |  AM
 * 12 Hrs: 12  1  2  3  4  5  6  7  8  9  10  11 |  12   1   2   3   4   5   6   7   8   9  10  11 |  12
 * 24 Hrs:  0  1  2  3  4  5  6  7  8  9  10  11 |  12  13  14  15  16  17  18  19  20  21  22  23 |   0
 * MOD 12:  0  1  2  3  4  5  6  7  8  9  10  11 |   0   1   2   3   4   5   6   7   8   9  10  11 |   0
 */
fun Context.showTimePickerDialog(
    currentHour: Int = 0,
    currentMin: Int = 1,
    listener: TimePickerListener,
) {
    val hours = (1..12).map { "%02d".format(it) }.toTypedArray()
    val minutes = arrayOf("00", "30")
    val amPm = arrayOf("AM", "PM")

    val binding = DialogTimePickerBinding.inflate(LayoutInflater.from(this))

    val builder = AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
        .setView(binding.root)
        .setPositiveButton("Ok") { _, _ ->
            val hour = if (binding.pickerAmPm.value == 1) { // PM
                if (binding.pickerHour.value < 12) {
                    binding.pickerHour.value + 12
                } else {
                    binding.pickerHour.value
                }
            } else if (binding.pickerHour.value == 12) {
                // AM
                0
            } else {
                binding.pickerHour.value
            }

            listener.onSuccess(
                hour,
                minutes[binding.pickerMinute.value].toInt(),
            )
        }
        .setNegativeButton("Cancel") { _, _ ->
            // ...
        }

    val alertDialog = builder.create()

    binding.pickerHour.minValue = 1
    binding.pickerHour.maxValue = 12
    binding.pickerHour.displayedValues = hours

    binding.pickerMinute.minValue = 0
    binding.pickerMinute.maxValue = 1
    binding.pickerMinute.displayedValues = minutes

    binding.pickerAmPm.minValue = 0
    binding.pickerAmPm.maxValue = 1
    binding.pickerAmPm.displayedValues = amPm

    // We will select the next hour
    val nextHour = currentHour + 1
    val currentHourMod12 = nextHour % 12
    binding.pickerHour.value = if (currentHourMod12 == 0) 12 else currentHourMod12

    binding.pickerAmPm.value = if (nextHour <= 11) 0 else 1

    // Select minute
    binding.pickerMinute.value = if (currentMin >= 30) 1 else 0

    alertDialog.show()
}

/*
// TODO: add sample usage

    private void showTimePicker() {
        int mHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = myCalendar.get(Calendar.MINUTE);
        int mDayOfYear = myCalendar.get(Calendar.DAY_OF_YEAR);

        ExtDialogKt.showTimePickerDialog(
                this,
                mHour,
                mMinute,
                (hour, minutes) -> {
                    Timber.e("hour: " + hour + " | minutes: " + minutes);
                }
        );
    }
 */

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
        },
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
                    Uri.parse("tel:$number"),
                ),
            )
        },
        "No",
        { _, _ ->
            /* no-op */
        },
    )
}

/**
 * Ask to a open email client.
 */
fun Activity.askAndOpenEmailClient(
    fromEmail: String,
    subject: String,
    body: String = "",
) {
    showAlertDialog(
        null,
        "Do you want to send email here― $fromEmail ?",
        "Yes",
        { _, _ ->
            sendEmail(
                fromEmail,
                subject,
                body,
            )
        },
        "No",
        { _, _ ->
            /* no-op */
        },
    )
}
