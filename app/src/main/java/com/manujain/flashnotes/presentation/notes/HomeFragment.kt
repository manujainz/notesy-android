package com.manujain.flashnotes.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.manujain.flashnotes.R
import com.manujain.flashnotes.core.getNotesOrderFromStr
import com.manujain.flashnotes.core.selected
import com.manujain.flashnotes.databinding.FragmentHomeBinding
import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.utils.NotesUiEvent
import com.manujain.flashnotes.domain.utils.OrderType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnNoteItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel by viewModels<NotesViewModel>()
    private val notesAdapter: NotesAdapter by lazy { NotesAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotesUi()
        initUiControls()
        initObservers()
    }

    private fun initNotesUi() {
        // Notes List
        binding.notesRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = notesAdapter
        }
    }

    private fun initUiControls() {
        // Notes Order Switching
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.notes_order_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.switchNotesOrder.adapter = adapter
        }
    }

    private fun initObservers() {
        // Fetch Notes
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.notes.collectLatest {
                    notesAdapter.submitList(it)
                }
            }
        }

        binding.apply {
            // Add Notes
            addNotes.setOnClickListener {
                navigateToAddEditNoteFragment()
            }

            // on Notes Order Switched
            switchNotesOrder.selected { selected ->
                val order = getNotesOrderFromStr(selected, OrderType.DESCENDING)
                notesViewModel.onEvent(NotesUiEvent.Order(order))
            }
        }
    }

    override fun onNoteItemClicked(note: Note) {
        navigateToAddEditNoteFragment(note.id)
    }

    private fun navigateToAddEditNoteFragment(noteId: String? = null) {
        val action = HomeFragmentDirections.actionHomeFragmentToAddEditNoteFragment(noteId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
