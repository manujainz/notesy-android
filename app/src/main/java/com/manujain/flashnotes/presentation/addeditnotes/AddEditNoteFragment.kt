package com.manujain.flashnotes.presentation.addeditnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.manujain.flashnotes.databinding.FragmentAddEditNoteBinding
import com.manujain.flashnotes.domain.utils.AddEditNoteUiEvent
import dagger.hilt.android.AndroidEntryPoint
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

        initUi()

        return binding.root
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
}
