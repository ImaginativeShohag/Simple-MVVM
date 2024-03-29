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

package org.imaginativeworld.simplemvvm.utils

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.models.GeneralSpinnerItem
import timber.log.Timber

// ----------------------------------------------------------------
// RecyclerView
// ----------------------------------------------------------------

/**
 * Set data to recyclerView adapter.
 *
 * Help: https://android.jlelse.eu/how-to-bind-a-list-of-items-to-a-recyclerview-with-android-data-binding-1bd08b4796b4
 */
@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T> RecyclerView.items(items: LiveData<T>?) {
    items?.apply {
        if (adapter is BindableAdapter<*>) {
            (adapter as BindableAdapter<T>).setItems(items.value)
        }
    }
}

// ----------------------------------------------------------------

/**
 * Set data to recyclerView adapter.
 *
 * @param items The item List<>.
 */
@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T> RecyclerView.items(items: T?) {
    items?.apply {
        if (adapter is BindableAdapter<*>) {
            (adapter as BindableAdapter<T>).setItems(items)
        }
    }
}

// ----------------------------------------------------------------
// Spinner
// ----------------------------------------------------------------

/**
 * Set data to Spinner.
 *
 * @param items The item List<> wrap with LiveData<>.
 */
@BindingAdapter("items")
fun Spinner.setItems(items: LiveData<List<GeneralSpinnerItem>>?) {
    items?.let {
        it.value?.let { listOfType ->
            val finalItems = mutableListOf<Map<String, String>>()

            for (item in listOfType) {
                finalItems.add(
                    mapOf(
                        Pair("id", item.id.toString()),
                        Pair("name", item.name)
                    )
                )
            }

            val adapter = SimpleAdapter(
                this.context,
                finalItems,
                R.layout.item_spinner_default,
                arrayOf(
                    "name"
                ),
                intArrayOf(
                    android.R.id.text1
                )
            )

            this.adapter = adapter
        }
    }
}

/**
 * Set data to Spinner.
 * This also store the last selected position of the spinner.
 * It will restore the last position (if found) after data change.
 *
 * @param items The item List<> wrap with LiveData<>.
 */
@BindingAdapter("itemsWithRestoreLastPosition")
fun Spinner.setItemsWithRestoreLastPosition(items: LiveData<List<GeneralSpinnerItem>>?) {
    items?.let {
        Timber.e("last position: selectedItemPosition: ${this.selectedItemPosition}")

        it.value?.let { listOfType ->
            val finalItems = mutableListOf<Map<String, String>>()

            for (item in listOfType) {
                finalItems.add(
                    mapOf(
                        Pair("id", item.id.toString()),
                        Pair("name", item.name)
                    )
                )
            }

            val adapter = SimpleAdapter(
                this.context,
                finalItems,
                R.layout.item_spinner_default,
                arrayOf(
                    "name"
                ),
                intArrayOf(
                    android.R.id.text1
                )
            )

            this.adapter = adapter

            val lastPosition = this.tag

            if (lastPosition != null) {
                if (lastPosition is Int) {
                    Timber.e("last position: lastPosition: $lastPosition")
                    this.setSelection(lastPosition)
                }
            }
        }
    }
}

// ----------------------------------------------------------------

/**
 * Set data to Spinner.
 *
 * @param items The item List<>.
 */
@BindingAdapter("items")
fun Spinner.setItems(items: List<GeneralSpinnerItem>?) {
    items?.let { listOfType ->
        val finalItems = mutableListOf<Map<String, String>>()

        for (item in listOfType) {
            finalItems.add(
                mapOf(
                    Pair("id", item.id.toString()),
                    Pair("name", item.name)
                )
            )
        }

        val adapter = SimpleAdapter(
            this.context,
            finalItems,
            R.layout.item_spinner_default,
            arrayOf(
                "name"
            ),
            intArrayOf(
                android.R.id.text1
            )
        )

        this.adapter = adapter
    }
}

// ----------------------------------------------------------------
// ImageView
// ----------------------------------------------------------------

/**
 * Load image from url.
 */
@BindingAdapter("srcUrlProfile")
fun ImageView.setProfileImageFromUrl(url: String) {
    GlideApp.with(context)
        .load(url)
        .profilePhoto()
        .into(this)
}

@BindingAdapter("srcUrl")
fun ImageView.setImageFromUrl(url: String) {
    GlideApp.with(context)
        .load(url)
        .into(this)
}

// ----------------------------------------------------------------
// View
// ----------------------------------------------------------------

/**
 * Update view visibility.
 */
@BindingAdapter("visibility")
fun View.setVisibility(visible: Boolean?) {
    visible?.apply {
        if (this) {
            this@setVisibility.visibility = View.VISIBLE
        } else {
            this@setVisibility.visibility = View.GONE
        }
    }
}

// ----------------------------------------------------------------
// TextView
// ----------------------------------------------------------------

/**
 * Set time.
 */
@BindingAdapter("time")
fun TextView.setTimeFromDate(date: Date?) {
    text = if (date != null) {
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        try {
            simpleDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Error!"
        }
    } else {
        "null"
    }
}

// ----------------------------------------------------------------

@BindingAdapter("datetime")
fun TextView.setDateTimeFromDate(date: Date?) {
    text = if (date != null) {
        val simpleDateFormat = SimpleDateFormat("d MMM yyyy hh:mm a", Locale.ENGLISH)
        try {
            simpleDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Error!"
        }
    } else {
        "null"
    }
}

// ----------------------------------------------------------------
// TextInput
// ----------------------------------------------------------------

/**
 * Set data to AutoCompleteTextView.
 *
 * @param items The item List<> wrap with LiveData<>.
 */
@BindingAdapter("items")
fun <T> AutoCompleteTextView.setItems(items: LiveData<T>?) {
    items?.apply {
        if (items.value is List<*>) {
            val arrayAdapter = ArrayAdapter(
                this@setItems.context,
                R.layout.item_dropdown_menu_popup_default,
                (items.value as List<*>)
            )

            this@setItems.setAdapter(arrayAdapter)
        }
    }
}
