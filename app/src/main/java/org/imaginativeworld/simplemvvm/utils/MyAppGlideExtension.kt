package org.imaginativeworld.simplemvvm.utils

import androidx.annotation.NonNull
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.BaseRequestOptions
import org.imaginativeworld.simplemvvm.R

// help: https://futurestud.io/tutorials/glide-4-request-options-generated-api

@GlideExtension
object MyAppGlideExtension {

    @NonNull
    @GlideOption
    @JvmStatic
    fun profilePhoto(options: BaseRequestOptions<*>): BaseRequestOptions<*> {
        return options
            .fitCenter()
            .placeholder(R.drawable.ic_user)
            .circleCrop()
    }

}