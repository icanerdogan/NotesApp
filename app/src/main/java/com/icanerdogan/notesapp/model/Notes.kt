package com.icanerdogan.notesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "title")
    var title : String,

    @ColumnInfo(name = "sub_title")
    var subTitle : String,

    @ColumnInfo(name = "date_time")
    val dateTime : String,

    @ColumnInfo(name = "note_text")
    val noteText : String,

    @ColumnInfo(name = "img_path")
    val imagePath : String,

    @ColumnInfo(name = "web_link")
    val webLink : String,

    @ColumnInfo(name = "color")
    val color : String
): Serializable
{
    override fun toString(): String {

        return "$title : $dateTime"
    }
}
