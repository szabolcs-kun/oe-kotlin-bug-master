package com.skun.bugmaster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "insect")
data class Insect(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "danger_level")
    var dangerLevel: Int = 0,

    @ColumnInfo(name = "insect_name")
    var insectName: String = "",

    @ColumnInfo(name = "insect_scientific_name")
    var insectScientificName : String = "",

    @ColumnInfo(name = "classification")
    var classification : String = "",

    @ColumnInfo(name = "image_asset")
    var imageAsset : String = ""
)