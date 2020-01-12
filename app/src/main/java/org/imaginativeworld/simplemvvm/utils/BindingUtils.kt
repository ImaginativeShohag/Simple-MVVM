package org.imaginativeworld.simplemvvm.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("postTitle")
fun TextView.setPostTitle(title: String) {
    text = title
}

@BindingAdapter("postBody")
fun TextView.setPostBody(body: String) {
    text = body
}