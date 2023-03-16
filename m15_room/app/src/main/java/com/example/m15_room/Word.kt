package com.example.m15_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Word")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "value")
    val value: String,
    @ColumnInfo(name = "count")
    val count: Int,
)