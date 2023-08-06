package com.kuit.couphone.data.SearchRoomDB

import androidx.room.*

@Dao
interface  LocalSearchDAO{

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertSearchKeyword(searchKeyword: LocalSearchEntity)

    @Delete
    fun delete(searchKeyword: LocalSearchEntity)
    @Query("SELECT COUNT(*) FROM local_search_table")
    fun getCount() : Int
    @Query("SELECT * FROM local_search_table WHERE userId = :userId and type = :type order by id ")
    fun getKeyWordList(userId : String,type : Int) : List<LocalSearchEntity>
    @Query("SELECT * FROM local_search_table WHERE keyword = :keyword")
    fun getresultkeyword(keyword : String) : LocalSearchEntity?

}