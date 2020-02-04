package org.imaginativeworld.simplemvvm.utils

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import timber.log.Timber

// Help: https://android.jlelse.eu/how-to-bind-a-list-of-items-to-a-recyclerview-with-android-data-binding-1bd08b4796b4
@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T> RecyclerView.items(postsResponse: LiveData<T>?) {
    postsResponse?.apply {
        if (adapter is BindableAdapter<*>) {

            postsResponse.value?.apply {
                Timber.d("Data: $this")
            }

            (adapter as BindableAdapter<T>).setItems(postsResponse.value)
        }
    }
}