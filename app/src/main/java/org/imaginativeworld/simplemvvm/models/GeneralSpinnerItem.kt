package org.imaginativeworld.simplemvvm.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralSpinnerItem(
    val id: Int,
    val name: String
) : Parcelable {
    override fun toString(): String {
        return name
    }
}
