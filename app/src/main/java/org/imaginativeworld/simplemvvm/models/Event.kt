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
    private val id: Int = if (lastId == Int.MAX_VALUE) {
        lastId = Int.MIN_VALUE
        Int.MAX_VALUE
    } else lastId++,
) {
    companion object {
        private var lastId = Int.MAX_VALUE
    }

    private var valueSent = false

    /**
     * Get the [value] only once.
     */
    fun getValueOnce(): T? {
        return if (!valueSent) {
            valueSent = true

            value
        } else null
    }
}