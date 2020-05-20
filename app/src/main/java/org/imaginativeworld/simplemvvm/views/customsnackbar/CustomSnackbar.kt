package org.imaginativeworld.simplemvvm.views.customsnackbar

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import org.imaginativeworld.simplemvvm.R

class CustomSnackbar private constructor(
    parent: ViewGroup,
    val content: CustomSnackbarContentLayout
) : BaseTransientBottomBar<CustomSnackbar>(parent, content, content) {

    @Nullable
    private var accessibilityManager: AccessibilityManager? = null
    private var hasAction: Boolean = false

    init {
        accessibilityManager = parent.context
            .getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(
            @NonNull view: View,
            @NonNull text: CharSequence,
            @Duration duration: Int
        ): CustomSnackbar {

            val parent = view.findSuitableParent()
                ?: throw IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view."
                )

            val inflater = LayoutInflater.from(parent.context)
            val content = inflater.inflate(
                R.layout.custom_snackbar_layout_inflate,
                parent,
                false
            ) as CustomSnackbarContentLayout

            val snackbar = CustomSnackbar(parent, content)
            snackbar.setText(text)
            snackbar.duration = duration
            return snackbar
        }

        private fun View?.findSuitableParent(): ViewGroup? {
            var view: View? = this
            var fallback: ViewGroup? = null
            do {
                if (view is CoordinatorLayout) {
                    // We've found a CoordinatorLayout, use it
                    return view
                } else if (view is FrameLayout) {
                    if (view.getId() == android.R.id.content) {
                        // If we've hit the decor content view, then we didn't find a CoL in the
                        // hierarchy, so use it.
                        return view
                    } else {
                        // It's not the content view but we'll use it as our fallback
                        fallback = view
                    }
                }
                if (view != null) {
                    // Else, we will loop and crawl up the view hierarchy and try to find a parent
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)

            // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
            return fallback
        }

    }

    fun setText(text: CharSequence): CustomSnackbar {
        content.messageView.text = text
        return this
    }

    fun setAction(
        @Nullable text: CharSequence,
        @Nullable listener: View.OnClickListener
    ): CustomSnackbar {
        val actionView = content.actionView
        if (TextUtils.isEmpty(text) || listener == null) {
            content.visibility = View.GONE
            actionView.setOnClickListener(null)
            hasAction = false
        } else {
            hasAction = true
            actionView.text = text
            actionView.visibility = View.VISIBLE
            actionView.setOnClickListener {
                listener.onClick(view)

                dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION)
            }
        }
        return this
    }

    @Duration
    override fun getDuration(): Int {
        val userSetDuration = super.getDuration()
        if (userSetDuration == LENGTH_INDEFINITE) {
            return LENGTH_INDEFINITE
        }
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            val controlsFlag = if (hasAction) AccessibilityManager.FLAG_CONTENT_CONTROLS else 0
            return accessibilityManager!!.getRecommendedTimeoutMillis(
                userSetDuration,
                controlsFlag or AccessibilityManager.FLAG_CONTENT_ICONS or AccessibilityManager.FLAG_CONTENT_TEXT
            )
        }

        // If touch exploration is enabled override duration to give people chance to interact.
        return if (hasAction && accessibilityManager!!.isTouchExplorationEnabled) LENGTH_INDEFINITE else userSetDuration
    }

}