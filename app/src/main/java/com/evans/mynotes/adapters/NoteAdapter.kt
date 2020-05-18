package com.evans.mynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evans.mynotes.R
import com.evans.mynotes.database.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(
    private val context: Context, private val itemInteraction: ItemInteraction
) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes: List<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(context).inflate(R.layout.note_item, parent, false), itemInteraction
        )
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    inner class NoteHolder(
        itemView: View, itemInteraction: ItemInteraction
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                itemInteraction.noteClicked(notes[adapterPosition])
            }
        }

        fun bind(note: Note) {
            itemView.note_title.text = note.title
            itemView.note_desc.text = note.note
        }
    }

    interface ItemInteraction {
        fun noteClicked(note: Note)
    }
}