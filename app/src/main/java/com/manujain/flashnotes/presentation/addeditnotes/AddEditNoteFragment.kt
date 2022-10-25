package com.manujain.flashnotes.presentation.addeditnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.manujain.flashnotes.databinding.FragmentAddEditNoteBinding
import com.manujain.flashnotes.domain.utils.AddEditNoteUiEvent
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.CancellationException
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteState.collectLatest { noteState ->
                    if (!noteState.isLoading) {

                        binding.titleEditText.setText(noteState.title)
                        binding.contentEditText.setText(noteState.content)

                        this.cancel(CancellationException("Note content retrieved. Upstream not required"))
                    }
                }
            }
        }
    }
}
