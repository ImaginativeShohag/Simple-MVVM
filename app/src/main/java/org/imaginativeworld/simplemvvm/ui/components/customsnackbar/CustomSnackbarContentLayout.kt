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

package org.imaginativeworld.simplemvvm.ui.components.customsnackbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.ContentViewCallback
import org.imaginativeworld.simplemvvm.R

class CustomSnackbarContentLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), ContentViewCallback {

    var messageView: TextView
    var actionView: Button

    init {
        View.inflate(context, R.layout.custom_snackbar_layout, this)

        messageView = findViewById(R.id.snackbar_text)
        actionView = findViewById(R.id.snackbar_action)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        messageView.alpha = 0f
        messageView.animate().apply {
            alpha(1f)
            setDuration(duration.toLong())
            startDelay = delay.toLong()
            start()
        }

        if (actionView.visibility == View.VISIBLE) {
            actionView.alpha = 0f
            actionView.animate().apply {
                alpha(1f)
                setDuration(duration.toLong())
                startDelay = delay.toLong()
                start()
            }
        }
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        messageView.alpha = 1f
        messageView.animate().apply {
            alpha(0f)
            setDuration(duration.toLong())
            startDelay = delay.toLong()
            start()
        }

        if (actionView.visibility == View.VISIBLE) {
            actionView.alpha = 1f
            actionView.animate().apply {
                alpha(0f)
                setDuration(duration.toLong())
                startDelay = delay.toLong()
                start()
            }
        }
    }
}
