package com.manujain.notesy.feature_notes.presentation.background_chooser

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    private val vertical: Int,
    private val horizontal: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            top = vertical
            left = horizontal
            right = horizontal
            bottom = vertical
        }
    }
}
