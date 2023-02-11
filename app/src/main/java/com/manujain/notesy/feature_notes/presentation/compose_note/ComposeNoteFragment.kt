package com.manujain.notesy.feature_notes.presentation.compose_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.manujain.notesy.R
import com.manujain.notesy.core.adjustPaddingWithStatusBar
import com.manujain.notesy.core.launchCoroutineOnStart
import com.manujain.notesy.databinding.FragmentComposeNoteBinding
import com.manujain.notesy.feature_notes.domain.utils.ComposeNoteUiEvent
import com.manujain.notesy.feature_notes.presentation.background_chooser.NotesyBackgroundSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ComposeNoteFragment : Fragment() {

    private var _binding: FragmentComposeNoteBinding? = null
    private val binding get() = _binding!!

    private val composeVM by viewModels<ComposeNoteViewModel>()
    private val backgroundVM: NotesyBackgroundSelectionViewModel by navGraphViewModels(R.id.compose_note_nav_graph) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentComposeNoteBinding.inflate(inflater, container, false)
        adjustPaddingWithStatusBar(binding.composeParent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initUi() {
        binding.titleEditText.doAfterTextChanged { text ->
            composeVM.onEvent(
                ComposeNoteUiEvent.OnTitleChange(text.toString())
            )
        }

        binding.contentEditText.doAfterTextChanged { text ->
            composeVM.onEvent(
                ComposeNoteUiEvent.OnContentChange(text.toString())
            )
        }

        binding.switchColor.setOnClickListener {
            val action = ComposeNoteFragmentDirections.toColorSelectionBottomSheet()
            findNavController().navigate(action)
        }

        binding.appBar.backButton.setOnClickListener {
            handleBackPress()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    handleBackPress()
                }
            }
        )
    }

    private fun initObservers() {
        launchCoroutineOnStart {
            lifecycleScope.launch {
                composeVM.noteState.collectLatest { noteState ->
                    if (!noteState.isLoading) {
                        binding.titleEditText.setText(noteState.title)
                        binding.contentEditText.setText(noteState.content)
                        backgroundVM.updateBackground(noteState.color)
                        // INFO: Cancel collecting flow when existing note (if any) is retrieved
                        this.cancel(CancellationException("Note content retrieved. Upstream not required"))
                    }
                }
            }

            lifecycleScope.launch {
                backgroundVM.selectedBackground.collectLatest { background ->
                    composeVM.onEvent(ComposeNoteUiEvent.OnBackgroundChange(background))
                    setBackground(backgroundVM.getColor(background))
                }
            }
        }
    }

    private fun handleBackPress() {
        Timber.d("Back Button Pressed. Add Note Event dispatched.")
        composeVM.onEvent(ComposeNoteUiEvent.AddNote)
        findNavController().popBackStack()
    }

    private fun setBackground(color: Int) {
        binding.root.setBackgroundColor(color)
    }
}
