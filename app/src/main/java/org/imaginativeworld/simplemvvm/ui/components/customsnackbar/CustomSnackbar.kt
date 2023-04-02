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
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import org.imaginativeworld.simplemvvm.R

class CustomSnackbar private constructor(
    parent: ViewGroup,
    val content: CustomSnackbarContentLayout,
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
                android.R.color.transparent,
            ),
        )
        getView().setPadding(0, 0, 0, 0)

        content.actionView.visibility = View.GONE
    }

    companion object {

        /**
         * Make a Snackbar to display a message
         *
         * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given to
         * {@code view}. Snackbar will walk up the view tree trying to find a suitable parent, which is
         * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
         * first.
         *
         * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable certain
         * features, such as swipe-to-dismiss and automatically moving of widgets.
         *
         * @param view The view to find a parent from. This view is also used to find the anchor view when
         *     calling {@link Snackbar#setAnchorView(int)}.
         * @param text The text to show. Can be formatted text.
         * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
         *     #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
         */
        fun make(
            @NonNull view: View,
            @NonNull text: CharSequence,
            @Duration duration: Int,
        ): CustomSnackbar {
            val parent = view.findSuitableParent()
                ?: throw IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view.",
                )

            val inflater = LayoutInflater.from(parent.context)
            val content = inflater.inflate(
                R.layout.custom_snackbar_layout_inflate,
                parent,
                false,
            ) as CustomSnackbarContentLayout

            val snackbar = CustomSnackbar(parent, content)
            snackbar.setText(text)
            snackbar.duration = duration
            return snackbar
        }

        /**
         * Make a Snackbar to display a message.
         *
         * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given to
         * {@code view}. Snackbar will walk up the view tree trying to find a suitable parent, which is
         * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
         * first.
         *
         * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable certain
         * features, such as swipe-to-dismiss and automatically moving of widgets.
         *
         * @param view The view to find a parent from.
         * @param resId The resource id of the string resource to use. Can be formatted text.
         * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
         *     #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
         */
        fun make(
            @NonNull view: View,
            @StringRes resId: Int,
            @Duration duration: Int,
        ): CustomSnackbar {
            return make(view, view.resources.getText(resId), duration)
        }

        /**
         * Find a suitable parent for the Snackbar.
         */
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

    /**
     * Update the text in this {@link Snackbar}.
     *
     * @param text The new text for this {@link BaseTransientBottomBar}.
     */
    fun setText(text: CharSequence): CustomSnackbar {
        content.messageView.text = text
        return this
    }

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param text Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    fun setAction(
        @Nullable text: CharSequence,
        @Nullable listener: View.OnClickListener?,
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

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param resId String resource to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    fun setAction(
        @StringRes resId: Int,
        @Nullable listener: (View) -> Unit,
    ): CustomSnackbar {
        return setAction(
            context.getText(resId),
            View.OnClickListener {
                listener.invoke(view)
            },
        )
    }

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param text Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    fun setAction(
        @Nullable text: CharSequence,
        @Nullable listener: (View) -> Unit,
    ): CustomSnackbar {
        return setAction(
            text,
            View.OnClickListener {
                listener.invoke(view)
            },
        )
    }

    /**
     * Return the duration.
     */
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
                controlsFlag or AccessibilityManager.FLAG_CONTENT_ICONS or AccessibilityManager.FLAG_CONTENT_TEXT,
            )
        }

        // If touch exploration is enabled override duration to give people chance to interact.
        return if (hasAction && accessibilityManager!!.isTouchExplorationEnabled) LENGTH_INDEFINITE else userSetDuration
    }
}
