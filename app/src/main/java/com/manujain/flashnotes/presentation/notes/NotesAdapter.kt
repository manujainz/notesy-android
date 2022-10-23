package com.manujain.flashnotes.presentation.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manujain.flashnotes.databinding.NoteViewHolderBinding
import com.manujain.flashnotes.domain.model.Note

interface OnNoteItemClickListener {
    fun onNoteItemClicked(note: Note)
}

class NotesAdapter(
    private val noteClickListener: OnNoteItemClickListener
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    class NoteViewHolder(private val binding: NoteViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note, listener: OnNoteItemClickListener) {
            binding.noteTitle.text = note.title
            binding.root.setOnClickListener {
                listener.onNoteItemClicked(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(currentList[position], noteClickListener)
    }
}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.content == newItem.content && newItem.title == oldItem.title
    }
}
