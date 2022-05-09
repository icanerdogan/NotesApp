package com.icanerdogan.notesapp.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.icanerdogan.notesapp.R
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*

class NoteBottomSheetFragment : BottomSheetDialogFragment() {
    var selectedColor = "#171C26"

    companion object {
        fun newInstance(): NoteBottomSheetFragment{
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context)
            .inflate(R.layout.fragment_notes_bottom_sheet,
            null)

        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = param.behavior

        if (behavior is BottomSheetBehavior<*>){
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }

                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }

                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    dismiss();
                }

            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setImageRes(note1 : Int = 0, note2 : Int = 0, note3 : Int = 0, note4 : Int = 0, note5 : Int = 0, note6 : Int = 0,){
        imgNote1.setImageResource(note1)
        imgNote2.setImageResource(note2)
        imgNote3.setImageResource(note3)
        imgNote4.setImageResource(note4)
        imgNote5.setImageResource(note5)
        imgNote6.setImageResource(note6)
    }
    private fun setListener(){
        fNote1.setOnClickListener{
            setImageRes(note1 = R.drawable.ic_tick)
            selectedColor = "#4e33ff"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Blue")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fNote2.setOnClickListener{
            setImageRes(note2 = R.drawable.ic_tick)
            selectedColor = "#ffd633"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Yellow")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fNote3.setOnClickListener{
            setImageRes(note3 = R.drawable.ic_tick)
            selectedColor = "#ae3b76"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Purple")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fNote4.setOnClickListener{
            setImageRes(note4 = R.drawable.ic_tick)
            selectedColor = "#0aebaf"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Green")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fNote5.setOnClickListener{
            setImageRes(note5 = R.drawable.ic_tick)
            selectedColor = "#ff7746"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Orange")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fNote6.setOnClickListener{
            setImageRes(note6 = R.drawable.ic_tick)
            selectedColor = "#202734"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Black")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        layoutImage.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
        layoutWebUrl.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "WebUrl")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
    }
}