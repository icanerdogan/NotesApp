package com.icanerdogan.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.fragment.app.FragmentManager
import com.icanerdogan.notesapp.util.ReplaceFragment.Companion.replaceFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
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
        floatingButtonCreateNote.setOnClickListener {
            replaceFragment(parentFragmentManager, CreateNoteFragment.newInstance(), true)
        }
    }
}