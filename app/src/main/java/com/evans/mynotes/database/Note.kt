package com.evans.mynotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "note_title")
    val title: String,
    @ColumnInfo(name = "note_body")
    val note: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}