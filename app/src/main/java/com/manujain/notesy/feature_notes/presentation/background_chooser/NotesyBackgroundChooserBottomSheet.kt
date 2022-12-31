package com.manujain.notesy.feature_notes.presentation.background_chooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.manujain.notesy.R
import com.manujain.notesy.core.theme.model.NotesyBackground
import com.manujain.notesy.databinding.BottomsheetBackgroundSelectionBinding

class NotesyBackgroundChooserBottomSheet : BottomSheetDialogFragment(), OnColorSelectionListener {

    private var _binding: BottomsheetBackgroundSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesyBackgroundSelectionViewModel by navGraphViewModels(R.id.compose_note_nav_graph) { defaultViewModelProviderFactory }
    private val notesyColorAdapter: NotesyBackgroundColorAdapter by lazy { NotesyBackgroundColorAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = BottomsheetBackgroundSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.colorSelectionRV.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = notesyColorAdapter
        }
        notesyColorAdapter.submitList(viewModel.getBackgrounds())
    }

    override fun onBackgroundSelected(background: NotesyBackground) {
        binding.apply { colorSheet.setBackgroundColor(viewModel.getColor(background)) }
        viewModel.updateColor(background)
    }
}
