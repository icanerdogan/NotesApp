package com.icanerdogan.notesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.icanerdogan.notesapp.R
import com.icanerdogan.notesapp.model.Notes
import com.icanerdogan.notesapp.service.NotesDatabase
import com.icanerdogan.notesapp.util.ReplaceFragment.Companion.replaceFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {
    private var currentDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateNoteFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = date.format(Date())

        textViewDateTime.text = currentDate

        imgDone.setOnClickListener {
            saveNote()
            replaceFragment(parentFragmentManager, HomeFragment.newInstance(), false)
        }

        imgBack.setOnClickListener {
            replaceFragment(parentFragmentManager, HomeFragment.newInstance(), false)
        }
    }

    private fun saveNote() {
        if (etNoteTitle.text.isNullOrEmpty() ||
            etNoteSubTitle.text.isNullOrEmpty() ||
            etNoteDesc.text.isNullOrEmpty()
        ) {
            Toast.makeText(
                context,
                "Title, Sub Title and Description are Required!",
                Toast.LENGTH_SHORT
            ).show()
        }

        launch {
            val notes = Notes()
            notes.title = etNoteTitle.text.toString()
            notes.subTitle = etNoteSubTitle.text.toString()
            notes.noteText = etNoteDesc.text.toString()
            notes.dateTime = currentDate

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                etNoteTitle.setText("")
                etNoteSubTitle.setText("")
                etNoteDesc.setText("")
            }
        }
    }
}