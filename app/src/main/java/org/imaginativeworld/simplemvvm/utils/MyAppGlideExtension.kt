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
            .circleCrop()
            .placeholder(R.drawable.ic_user)
            .fallback(R.drawable.ic_user)
            .error(R.drawable.ic_user)
    }
}
