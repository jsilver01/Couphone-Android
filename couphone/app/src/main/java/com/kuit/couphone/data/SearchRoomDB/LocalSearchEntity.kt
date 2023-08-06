package com.kuit.couphone.data.SearchRoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_search_table")
data class LocalSearchEntity(
    var type : Int, //1은 home 2 는 지도
    var id: Int,
    var userId: String,
    @PrimaryKey val keyword : String,
)