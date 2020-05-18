package com.evans.mynotes.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            noteTitle.setText(note?.title)
            noteBody.setText(note?.note)
        }

        if (note != null){
            (activity as AppCompatActivity).supportActionBar?.title = "Edit Note"
        } else{
            (activity as AppCompatActivity).supportActionBar?.title = "Add Note"
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
                    toHomeFragment(view)
                }
            }
        }
    }

    private fun toHomeFragment(view: View) {
        val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
        Navigation.findNavController(view).navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete ->
                if (note != null) deleteNote() else context?.toast("Delete operation denied")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Are you sure?")
            setMessage("This action cannot be undone")
            setPositiveButton("Yes"){_, _ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    toHomeFragment(requireView())
                }
            }
            setNegativeButton("Cancel"){dialog, _ -> dialog.dismiss() }
        }.create().show()
    }
}
