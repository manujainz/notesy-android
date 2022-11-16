package com.manujain.flashnotes.presentation.scribblepad

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.manujain.flashnotes.R

class ScribblePadView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    init {
        inflate(context, R.layout.layout_scribblepad, this)
    }
}
