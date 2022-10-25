package com.manujain.flashnotes.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.manujain.flashnotes.databinding.FragmentHomeBinding
import com.manujain.flashnotes.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel by viewModels<NotesViewModel>()

    private lateinit var notesAdapter: NotesAdapter

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
        initObservers()
    }

    private fun initNotesUi() {
        val notesRecyclerView = binding.notesRV
        notesRecyclerView.layoutManager = LinearLayoutManager(activity)

        notesAdapter = NotesAdapter(object : OnNoteItemClickListener {
            override fun onNoteItemClicked(note: Note) {
                navigateToAddEditNoteFragment(note.id)
            }
        }).apply {
            stateRestorationPolicy = StateRestorationPolicy.ALLOW
        }

        notesRecyclerView.adapter = notesAdapter
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.notes.collectLatest {
                    notesAdapter.submitList(it)
                }
            }
        }

        binding.addFAB.setOnClickListener {
            navigateToAddEditNoteFragment()
        }
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
