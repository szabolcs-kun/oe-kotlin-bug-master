package com.skun.bugmaster

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface InsectDao {
    @Insert
    fun insert(insect: Insect)

    @Update
    fun update(insect: Insect)

    @Query("SELECT * from insect ORDER BY `danger_level` DESC")
    fun get(): List<Insect>
    //fun get(): LiveData<List<Insect>>

    @Query("SELECT * from insect WHERE id = :id ORDER BY `danger_level` DESC")
    fun get(id: Int): Insect?

    @Query("SELECT * from insect WHERE insect_name = :name ORDER BY `danger_level` DESC")
    fun get(name: String): Insect?
}