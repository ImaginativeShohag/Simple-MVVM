package org.imaginativeworld.simplemvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class DemoUserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var name: String,
    var phone: String,
    var image: String?
)