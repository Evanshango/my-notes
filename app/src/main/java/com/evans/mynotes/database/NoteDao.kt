package com.evans.mynotes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = REPLACE)
    fun addNote(note: Note)

    @get:Query("SELECT * FROM notes")
    val getAllNotes : List<Note>
}