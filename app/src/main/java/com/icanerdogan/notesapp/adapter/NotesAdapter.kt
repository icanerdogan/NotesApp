package com.icanerdogan.notesapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icanerdogan.notesapp.R
import com.icanerdogan.notesapp.model.Notes
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter(private val notesList: List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text = notesList[position].title
        holder.itemView.tvDesc.text = notesList[position].noteText
        holder.itemView.tvDateTime.text = notesList[position].dateTime

        if (notesList[position].color != null){
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(notesList[position].color))
        }else{
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor("#171C26"))
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}