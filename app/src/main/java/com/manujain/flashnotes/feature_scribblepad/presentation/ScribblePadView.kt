package com.manujain.flashnotes.feature_scribblepad.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.manujain.flashnotes.databinding.LayoutScribblepadBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class ScribblePadView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    private var _binding: LayoutScribblepadBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[ScribblePadViewModel::class.java] }
    private var scope: CoroutineScope? = null

    companion object {
        const val TAG = "[ScribblePad]"
    }

    init {
        _binding = LayoutScribblepadBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        addView(binding.root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        initUi()
    }

    private fun initUi() {
        scope?.launch {
            viewModel.scribble.collectLatest { scribble ->
                Timber.d("$TAG Scribble fetched. Updating...")
                binding.scribbleEditText.setText(scribble.data)
                invalidate()
                cancel("Stored Scribble fetched. Not observing anymore.")
            }
        }

        binding.scribbleEditText.doAfterTextChanged {
            viewModel.onScribbleUpdate(it.toString())
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope?.cancel()
        scope = null
    }
}
