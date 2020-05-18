package com.evans.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.evans.mynotes.R
import com.evans.mynotes.database.Note
import com.evans.mynotes.database.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            noteTitle.setText(note?.title)
            noteBody.setText(note?.note)
        }

        btnDone.setOnClickListener { view ->
            val title = noteTitle.text.toString().trim()
            val body = noteBody.text.toString().trim()

            if (title.isEmpty()) {
                noteTitle.error = " Title is required"
                noteTitle.requestFocus()
                return@setOnClickListener
            }

            if (body.isEmpty()) {
                noteBody.error = " Description is required"
                noteBody.requestFocus()
                return@setOnClickListener
            }

            launch {
                context?.let {
                    val mNote = Note(title, body)

                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")
                    }
                    val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }
}
