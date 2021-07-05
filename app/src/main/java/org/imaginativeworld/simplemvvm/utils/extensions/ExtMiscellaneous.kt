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
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import org.imaginativeworld.oopsnointernet.utils.NoInternetUtils
import org.imaginativeworld.simplemvvm.R
import timber.log.Timber

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
 * Show soft keyboard
 */
fun EditText.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Download file using system download manager.
 */
fun Activity.downloadFile(
    url: String,
    downloadTitle: String = getString(R.string.app_name),
    downloadDescription: String = "Downloading file"
) {
    if (NoInternetUtils.isConnectedToInternet(this)) {
        val downloadManager =
            getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri = Uri.parse(url)

        Timber.d("URI: $uri")

        val request = DownloadManager.Request(uri).apply {
            setTitle(downloadTitle)
            setDescription(downloadDescription)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }

        downloadManager.enqueue(request)

        longToast("Starting download...")
    } else {
        longToast("You have no active Internet connection!")
    }
}

/**
 * Open permission setting.
 */
fun Activity.openPermissionSetting(message: String) {
    this.longToast(message)

    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    this.startActivity(intent)
}

fun Activity.openUrl(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()

        longToast("Cannot open the link!")
    }
}

fun Context.sendEmail(
    fromEmail: String,
    subject: String,
    body: String
) {
    try {
        val emailIntent = Intent(Intent.ACTION_VIEW)
        val data =
            Uri.parse("mailto:$fromEmail?subject=$subject&body=$body")
        emailIntent.data = data
        this.startActivity(emailIntent)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()

        longToast("No e-mail client found!")
    }
}
