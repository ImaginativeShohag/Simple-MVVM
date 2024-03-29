/*
 * Copyright 2016 Md. Mahmudul Hasan Shohag
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

@file:Suppress("NOTHING_TO_INLINE")

package org.imaginativeworld.simplemvvm.utils.extensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import org.imaginativeworld.simplemvvm.ui.components.customsnackbar.CustomSnackbar

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun View.snackbar(@StringRes message: Int) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun View.longSnackbar(@StringRes message: Int) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun View.indefiniteSnackbar(@StringRes message: Int) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun View.snackbar(message: CharSequence) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun View.longSnackbar(message: CharSequence) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun View.indefiniteSnackbar(message: CharSequence) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun View.snackbar(
    message: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun View.longSnackbar(
    @StringRes message: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
) =
    CustomSnackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .setAction(actionText, action)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun View.indefiniteSnackbar(
    @StringRes message: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
) =
    CustomSnackbar
        .make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action)
        .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun View.snackbar(
    message: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun View.longSnackbar(
    message: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun View.indefiniteSnackbar(
    message: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
) = CustomSnackbar
    .make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }
