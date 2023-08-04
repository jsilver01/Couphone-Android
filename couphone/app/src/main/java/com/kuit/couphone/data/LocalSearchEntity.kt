package com.kuit.couphone.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_search_table")
data class LocalSearchEntity(
    var id: Int,
    var userId: String,
    @PrimaryKey val keyword : String,
)