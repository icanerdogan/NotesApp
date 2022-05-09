package com.icanerdogan.notesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.icanerdogan.notesapp.R
import com.icanerdogan.notesapp.adapter.NotesAdapter
import com.icanerdogan.notesapp.model.Notes
import com.icanerdogan.notesapp.service.NotesDatabase
import com.icanerdogan.notesapp.util.ReplaceFragment.Companion.replaceFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    var arrNotes = ArrayList<Notes>()
    private var notesAdapter: NotesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {  }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {  }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter!!.setData(notes)
                recyclerView.adapter = notesAdapter

            }
        }

        // Karta Tıklanma!
        notesAdapter!!.setOnClickListener(onClicked)

        floatingButtonCreateNote.setOnClickListener {
            replaceFragment(parentFragmentManager, CreateNoteFragment.newInstance(), false)
        }
    }

    private val onClicked = object : NotesAdapter.OnItemClickListener{

        override fun onClicked(noteId: Int) {
            var fragment: Fragment
            var bundle =  Bundle()

            bundle.putInt("noteId", noteId)

            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(parentFragmentManager, fragment, false)
        }
    }
}