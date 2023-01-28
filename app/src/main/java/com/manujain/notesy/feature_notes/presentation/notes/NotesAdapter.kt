package com.manujain.notesy.feature_notes.presentation.notes

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manujain.notesy.MainApplication
import com.manujain.notesy.core.theme.repository.NotesyBackgroundProvider
import com.manujain.notesy.databinding.ViewholderNoteBinding
import com.manujain.notesy.di.NotesyBackgroundProviderEntryPoint
import com.manujain.notesy.feature_notes.domain.model.Note
import dagger.hilt.android.EntryPointAccessors

interface OnNoteItemUserActivityListener {
    fun onNoteItemClicked(note: Note)
    fun onNoteItemDeleted(note: Note)
}

class NotesAdapter(
    private val userActivityListener: OnNoteItemUserActivityListener
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication(MainApplication.INSTANCE, NotesyBackgroundProviderEntryPoint::class.java)
    private val backgroundProvider = hiltEntryPoint.getNotesyBackgroundProvider()

    init {
        stateRestorationPolicy = StateRestorationPolicy.ALLOW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ViewholderNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(currentList[position], backgroundProvider, userActivityListener)
    }

    fun deleteNote(position: Int) {
        // INFO: No need to update the dataset and notify here.
        // Host caller needs to invoke updating the repository as single source of truth
        userActivityListener.onNoteItemDeleted(note = currentList[position])
    }

    class NoteViewHolder(private val binding: ViewholderNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            note: Note,
            backgroundProvider: NotesyBackgroundProvider,
            listener: OnNoteItemUserActivityListener
        ) {
            binding.noteTitle.text = note.title
            binding.noteContent.text = note.content
            binding.container.backgroundTintList = ColorStateList.valueOf(backgroundProvider.getColor(note.color))
            binding.root.setOnClickListener {
                listener.onNoteItemClicked(note)
            }
        }
    }
}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.content == newItem.content &&
            newItem.title == oldItem.title &&
            newItem.color == oldItem.color
    }
}
