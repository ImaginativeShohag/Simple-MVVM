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

package org.imaginativeworld.simplemvvm.models.todo

import androidx.annotation.ColorRes
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date
import java.util.Locale
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.utils.extensions.getHumanReadableDate
import org.imaginativeworld.simplemvvm.utils.extensions.toLower

@JsonClass(generateAdapter = true)
data class Todo(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "title")
    val title: String,
    @Json(name = "due_on")
    val dueOn: Date?,
    @Json(name = "status")
    val status: String,
    @Json(name = "user_id")
    val userId: Int = 1 // This is used for demo only.
) {
    fun getDueDate(): String {
        return dueOn?.getHumanReadableDate() ?: "No"
    }

    fun getStatusLabel(): String {
        return status.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    @ColorRes
    fun getStatusColor(): Int {
        return if (status.toLower() == "completed") {
            R.color.flat_awesome_green_2
        } else {
            R.color.flat_red_2
        }
    }
}

fun Todo.asEntity() = TodoEntity(
    id = id,
    title = title,
    dueOn = dueOn,
    status = status,
    userId = userId
)
