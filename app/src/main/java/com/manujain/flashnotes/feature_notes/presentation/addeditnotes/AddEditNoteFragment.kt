package com.manujain.flashnotes.feature_notes.presentation.addeditnotes

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
import com.manujain.flashnotes.core.launchCoroutineOnStart
import com.manujain.flashnotes.databinding.FragmentAddEditNoteBinding
import com.manujain.flashnotes.feature_notes.domain.model.Note
import com.manujain.flashnotes.feature_notes.domain.utils.AddEditNoteUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AddEditNoteFragment : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AddEditNoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initUi() {
        binding.titleEditText.doAfterTextChanged { text ->
            viewModel.onEvent(
                AddEditNoteUiEvent.OnTitleChange(text.toString())
            )
        }

        binding.contentEditText.doAfterTextChanged { text ->
            viewModel.onEvent(
                AddEditNoteUiEvent.OnContentChange(text.toString())
            )
        }

        binding.switchColor.setOnClickListener {
            viewModel.onEvent(AddEditNoteUiEvent.OnColorChange(Note.colors.random()))
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Timber.d("Back Button Pressed. Add Note Event dispatched.")
                    viewModel.onEvent(AddEditNoteUiEvent.AddNote)
                    findNavController().popBackStack()
                }
            }
        )
    }

    private fun initObservers() {
        launchCoroutineOnStart {
            lifecycleScope.launch {
                viewModel.noteState.collectLatest { noteState ->
                    if (!noteState.isLoading) {
                        binding.titleEditText.setText(noteState.title)
                        binding.contentEditText.setText(noteState.content)
                        setBackground(noteState.color)
                        // INFO: Cancel collecting flow when existing note (if any) is retrieved
                        this.cancel(CancellationException("Note content retrieved. Upstream not required"))
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.colorState.collectLatest { color ->
                    setBackground(color)
                }
            }
        }
    }

    private fun setBackground(color: Int) {
        binding.root.setBackgroundColor(color)
    }
}
