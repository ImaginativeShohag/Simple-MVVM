package org.imaginativeworld.simplemvvm.views.fragments.post

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.adapters.PostListAdapter
import org.imaginativeworld.simplemvvm.models.PostResponse

@BindingAdapter("submitList")
fun RecyclerView.submitList(postsResponse: LiveData<List<PostResponse>>?) {
    postsResponse?.apply {
        if (adapter is PostListAdapter) {
            (adapter as PostListAdapter).apply {
                postsResponse.value?.apply {
                    submitList(this)

                    checkEmptiness()
                }
            }
        }
    }
}