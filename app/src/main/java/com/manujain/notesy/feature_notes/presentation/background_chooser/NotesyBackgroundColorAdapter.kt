package com.manujain.notesy.feature_notes.presentation.background_chooser

import android.view.LayoutInflater
import android.view.View
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

data class SelectableNotesyBackground(
    val background: NotesyBackground,
    var isSelected: Boolean = false
)

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
) : ListAdapter<SelectableNotesyBackground, NotesyBackgroundColorAdapter.ColorViewHolder>(DIFF_CALLBACK) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication(MainApplication.INSTANCE, NotesyColorAdapterEntryPoint::class.java)
    private val backgroundProvider = hiltEntryPoint.getNotesyBackgroundProvider()

    init {
        stateRestorationPolicy = StateRestorationPolicy.ALLOW
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ViewholderNotesyBackgroundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding, backgroundProvider)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(currentList[position], listener)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].background.name.hashCode().toLong()
    }

    class ColorViewHolder(
        private val binding: ViewholderNotesyBackgroundBinding,
        private val backgroundProvider: NotesyBackgroundProvider
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wrapper: SelectableNotesyBackground, listener: OnColorSelectionListener) {
            binding.colorCircle.apply {
                background.setTint(backgroundProvider.getColor(wrapper.background))
                setOnClickListener {
                    listener.onBackgroundSelected(wrapper.background)
                }
            }

            val shouldShowTick = if (wrapper.isSelected) View.VISIBLE else View.INVISIBLE
            binding.selectedTickMark.visibility = shouldShowTick
        }
    }
}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SelectableNotesyBackground>() {
    override fun areItemsTheSame(
        oldItem: SelectableNotesyBackground,
        newItem: SelectableNotesyBackground
    ): Boolean {
        return oldItem.background.name == newItem.background.name && oldItem.isSelected == newItem.isSelected
    }

    override fun areContentsTheSame(
        oldItem: SelectableNotesyBackground,
        newItem: SelectableNotesyBackground
    ): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
