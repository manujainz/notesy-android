package com.manujain.notesy.feature_notes.presentation.background_chooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.manujain.notesy.R
import com.manujain.notesy.core.launchCoroutineOnStart
import com.manujain.notesy.core.theme.model.NotesyBackground
import com.manujain.notesy.databinding.BottomsheetBackgroundSelectionBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotesyBackgroundChooserBottomSheet : BottomSheetDialogFragment(), OnColorSelectionListener {

    private var _binding: BottomsheetBackgroundSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesyBackgroundSelectionViewModel by navGraphViewModels(R.id.compose_note_nav_graph) { defaultViewModelProviderFactory }
    private val backgroundAdapter: NotesyBackgroundColorAdapter by lazy { NotesyBackgroundColorAdapter(this) }

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
            adapter = backgroundAdapter
            addItemDecoration(SpacingItemDecoration(60, 32))
        }

        backgroundAdapter.submitList(viewModel.getSelectableBackgrounds()).also {
            binding.colorSelectionRV.smoothScrollToPosition(getSelectedBackgroundIndex())
        }

        launchCoroutineOnStart {
            lifecycleScope.launch {
                viewModel.selectedBackground.collectLatest {
                    setBackground(viewModel.getColor(it))
                }
            }
        }
    }

    override fun onBackgroundSelected(background: NotesyBackground) {
        val previouslySelectedIndex = backgroundAdapter.currentList.indexOfFirst { it.isSelected }
        val newlySelectedIndex = backgroundAdapter.currentList.indexOfFirst { it.background == background }

        backgroundAdapter.apply {
            currentList[previouslySelectedIndex].isSelected = false
            currentList[newlySelectedIndex].isSelected = true
            notifyItemChanged(previouslySelectedIndex)
            notifyItemChanged(newlySelectedIndex)
        }

        viewModel.updateBackground(background)
    }

    private fun getSelectedBackgroundIndex() = backgroundAdapter.currentList.indexOfFirst { it.isSelected }

    private fun setBackground(color: Int) {
        binding.colorSheet.setBackgroundColor(color)
    }
}
