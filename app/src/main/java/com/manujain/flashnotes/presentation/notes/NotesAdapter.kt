package com.manujain.flashnotes.presentation.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manujain.flashnotes.databinding.NoteViewHolderBinding
import com.manujain.flashnotes.domain.model.Note

class NotesAdapter : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    class NoteViewHolder(val binding: NoteViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.noteTitle.text = note.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(currentList[position])
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
