package com.icanerdogan.notesapp.view

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.icanerdogan.notesapp.R
import com.icanerdogan.notesapp.model.Notes
import com.icanerdogan.notesapp.service.NotesDatabase
import com.icanerdogan.notesapp.util.NoteBottomSheetFragment
import com.icanerdogan.notesapp.util.ReplaceFragment.Companion.replaceFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_create_note.tvWebLink
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_note.*
import kotlinx.android.synthetic.main.item_note.view.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    var selectedColor = "#171C26"
    private val READ_STORAGE_PERM_CODE = 123
    private var selectedImagePath = ""
    private var webLink = ""

    private var noteId = -1

    private var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Home Fragment "fragment" arguments
        noteId = requireArguments().getInt("noteId", -1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateNoteFragment().apply { arguments = Bundle().apply {} } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (noteId != -1 ){
            launch {
                context?.let {
                    val notes = NotesDatabase.getDatabase(it).noteDao().getOneNote(noteId)
                    colorView.setBackgroundColor(Color.parseColor(notes.color))

                    etNoteTitle.setText(notes.title)
                    etNoteSubTitle.setText(notes.subTitle)
                    etNoteDesc.setText(notes.noteText)

                    if (notes.imagePath != ""){
                        imgNote.setImageBitmap(
                            BitmapFactory.decodeFile(notes.imagePath)
                        )
                        imgNote.visibility = View.VISIBLE
                        layoutImage.visibility = View.VISIBLE

                    } else {
                        imgNoteCreateNote.visibility = View.GONE
                        layoutImage.visibility = View.GONE
                    }

                    if (notes.webLink != ""){
                        webLink = notes.webLink!!
                        tvWebLink.text = notes.webLink
                        layoutWebUrl.visibility = View.VISIBLE
                        etWebLink.setText(notes.webLink)
                        imgUrlDelete.visibility = View.VISIBLE
                    } else {
                        imgUrlDelete.visibility = View.GONE
                        layoutWebUrl.visibility = View.GONE
                    }

                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val date = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = date.format(Date())

        colorView.setBackgroundColor(Color.parseColor(selectedColor))

        textViewDateTime.text = currentDate

        imgDone.setOnClickListener {
            saveNote()
        }

        imgBack.setOnClickListener {
            replaceFragment(parentFragmentManager, HomeFragment.newInstance(), false)
        }

        imgMore.setOnClickListener {
            val noteBottomSheetFragment = NoteBottomSheetFragment.newInstance()
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager, "Note Bottom Sheet Fragment")
        }
        btnOk.setOnClickListener {
            if (etWebLink.text.toString().trim().isNotEmpty()){
                checkWebUrl()
            } else {
                Toast.makeText(requireContext(), "URL is required!", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener {
            layoutWebUrl.visibility = View.GONE
        }
        tvWebLink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(etWebLink.text.toString()))
            startActivity(intent)
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
        } else {
            launch {
                val notes = Notes()
                notes.title = etNoteTitle.text.toString()
                notes.subTitle = etNoteSubTitle.text.toString()
                notes.noteText = etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                notes.imagePath = selectedImagePath
                notes.webLink = webLink

                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    etNoteTitle.setText("")
                    etNoteSubTitle.setText("")
                    etNoteDesc.setText("")
                    imgNoteCreateNote.visibility = View.GONE
                    tvWebLink.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun checkWebUrl(){
        if (Patterns.WEB_URL.matcher(etWebLink.text.toString()).matches()){
            layoutWebUrl.visibility =View.GONE
            etWebLink.isEnabled = false
            webLink = etWebLink.text.toString()
            tvWebLink.visibility =View.VISIBLE
            tvWebLink.text = etWebLink.text.toString()
        } else {
            Toast.makeText(requireContext(), "URL is not valid!", Toast.LENGTH_SHORT).show()
        }
    }

    private val broadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val actionColor = intent!!.getStringExtra("action")

            when(actionColor!!){
                "Blue" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Yellow" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Purple" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Green" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Orange" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Black" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Image" -> {
                    readStorageTask()
                    layoutWebUrl.visibility = View.GONE
                }
                "WebUrl" -> {
                    layoutWebUrl.visibility = View.VISIBLE
                }
                else -> {
                    layoutWebUrl.visibility = View.GONE
                    imgNoteCreateNote.visibility = View.GONE
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun hasReadStoragePerm() : Boolean{
        return EasyPermissions.hasPermissions(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask(){
        if (hasReadStoragePerm()){
            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.storage_permissions_text),
                READ_STORAGE_PERM_CODE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

   var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK){
            if (result.data != null){
                val selecetedImageUrl = result.data!!.data
                if (selecetedImageUrl != null){
                    try {
                        val inputStream = requireActivity().contentResolver.openInputStream(selecetedImageUrl)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        imgNoteCreateNote.setImageBitmap(bitmap)
                        imgNoteCreateNote.visibility = View.VISIBLE
                        layoutImage.visibility = View.VISIBLE
                        selectedImagePath = getPathFromUri(selecetedImageUrl)!!
                    } catch (e: Exception){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (intent.resolveActivity(requireActivity().packageManager) != null){
            resultLauncher.launch(intent)
        }
    }
    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath:String? = null
        var cursor = requireActivity().contentResolver.query(contentUri,null,null,null,null)
        if (cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, requireActivity())
    }
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}
    override fun onRationaleAccepted(requestCode: Int) {}
    override fun onRationaleDenied(requestCode: Int) {}
}