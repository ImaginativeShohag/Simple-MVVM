/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.models

/**
 * The observer/collector of LiveData, Channel, Flow etc. ignored consequent identical values.
 * So, before we send an event we update the `id` with an unique value. Which makes it
 * non-identical.
 *
 * @param value T The target value.
 * @param id Int The unique event id. It will be auto generated.
 */
data class Event<out T>(
    val value: T,
    val id: Int = ++lastId,
) {
    companion object {
        private var lastId = 0
    }
}