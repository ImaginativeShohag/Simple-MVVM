package org.imaginativeworld.simplemvvm.models.awesometodos

import androidx.annotation.ColorRes
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date
import java.util.Locale
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.utils.extensions.getHumanReadableDate
import org.imaginativeworld.simplemvvm.utils.extensions.toLower

@Keep
@JsonClass(generateAdapter = true)
data class TodoItem(
    @Json(name = "id")
    val id: Int? = null,
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
