package org.imaginativeworld.simplemvvm.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import timber.log.Timber

/**
 * Set data to recyclerView adapter.
 *
 * Help: https://android.jlelse.eu/how-to-bind-a-list-of-items-to-a-recyclerview-with-android-data-binding-1bd08b4796b4
 */
@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T> RecyclerView.items(postsResponse: LiveData<T>?) {

    Timber.e("fun <T> RecyclerView.items(postsResponse: LiveData<T>?) {")

    postsResponse?.apply {

        Timber.e("postsResponse?.apply {")

        if (adapter is BindableAdapter<*>) {

            Timber.e("if (adapter is BindableAdapter<*>) {")

            postsResponse.value?.apply {
                Timber.d("Data: $this")
            }

            (adapter as BindableAdapter<T>).setItems(postsResponse.value)
        }
    }
}


/**
 * Load image from url.
 */
@BindingAdapter("srcUrlProfile")
fun ImageView.setImage(url: String) {
    GlideApp.with(context)
        .load(url)
        .profilePhoto()
        .into(this)
}


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