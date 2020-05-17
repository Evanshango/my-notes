package com.evans.mynotes.ui

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.evans.mynotes.R
import com.evans.mynotes.database.Note
import com.evans.mynotes.database.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnDone.setOnClickListener {
            val title = noteTitle.text.toString().trim()
            val body = noteBody.text.toString().trim()

            if (title.isEmpty()){
                noteTitle.error = " Title is required"
                noteTitle.requestFocus()
                return@setOnClickListener
            }

            if (body.isEmpty()){
                noteBody.error = " Description is required"
                noteBody.requestFocus()
                return@setOnClickListener
            }

            val note = Note(title, body)
            saveNote(note)
        }
    }

    private fun saveNote(note: Note){
        class SaveNote: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                NoteDatabase(requireActivity()).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(requireContext(), "Note Saved", Toast.LENGTH_SHORT).show()
            }
        }
        SaveNote().execute()
    }
}
