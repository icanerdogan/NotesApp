package com.icanerdogan.notesapp.service

import androidx.room.*
import com.icanerdogan.notesapp.model.Notes

@Dao
interface NoteDao {

    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes : List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note : Notes)

    @Delete
    suspend fun deleteNote(note: Notes)
}