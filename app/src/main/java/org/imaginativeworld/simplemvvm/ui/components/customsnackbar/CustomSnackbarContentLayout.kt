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
    defStyleAttr: Int = 0
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