package com.evans.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.evans.mynotes.R
import com.evans.mynotes.adapters.NoteAdapter
import com.evans.mynotes.database.Note
import com.evans.mynotes.database.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(), NoteAdapter.ItemInteraction {

    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                notesRecycler.apply {
                    setHasFixedSize(true)
                    layoutManager =GridLayoutManager(it, 2)
                    noteAdapter = NoteAdapter(it, this@HomeFragment)
                    noteAdapter.setNotes(notes)
                    adapter = noteAdapter
                }
            }
        }

        btnAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun noteClicked(note: Note) {
        requireContext().toast("clicked ${note.title}")
    }
}
