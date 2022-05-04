package com.icanerdogan.notesapp.service

import androidx.room.*
import com.icanerdogan.notesapp.model.Notes

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun  getAllNotes() : List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note : Notes)

    @Delete
    suspend fun deleteNote(note: Notes)
}