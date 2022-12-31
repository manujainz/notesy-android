package com.manujain.notesy.feature_notes.presentation.background_chooser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manujain.notesy.MainApplication
import com.manujain.notesy.core.theme.model.NotesyBackground
import com.manujain.notesy.core.theme.repository.NotesyBackgroundProvider
import com.manujain.notesy.databinding.ViewholderNotesyBackgroundBinding
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

interface OnColorSelectionListener {
    fun onBackgroundSelected(background: NotesyBackground)
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NotesyColorAdapterEntryPoint {
    fun getNotesyBackgroundProvider(): NotesyBackgroundProvider
}

class NotesyBackgroundColorAdapter(
    private val listener: OnColorSelectionListener
) : ListAdapter<NotesyBackground, NotesyBackgroundColorAdapter.ColorViewHolder>(DIFF_CALLBACK) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication(MainApplication.instance, NotesyColorAdapterEntryPoint::class.java)
    private val backgroundProvider = hiltEntryPoint.getNotesyBackgroundProvider()

    init {
        stateRestorationPolicy = StateRestorationPolicy.ALLOW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ViewholderNotesyBackgroundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding, backgroundProvider)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(currentList[position], listener)
    }

    class ColorViewHolder(
        private val binding: ViewholderNotesyBackgroundBinding,
        private val backgroundProvider: NotesyBackgroundProvider
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(background: NotesyBackground, listener: OnColorSelectionListener) {
            binding.apply {
                colorCircle.background.setTint(backgroundProvider.getColor(background))
                colorCircle.setOnClickListener {
                    listener.onBackgroundSelected(background)
                }
            }
        }
    }
}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NotesyBackground>() {
    override fun areItemsTheSame(oldItem: NotesyBackground, newItem: NotesyBackground): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: NotesyBackground, newItem: NotesyBackground): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
