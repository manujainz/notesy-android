package com.manujain.flashnotes.feature_scribblepad.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import com.manujain.flashnotes.R
import timber.log.Timber

class ScribblePadView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    companion object {
        const val TAG = "ScribblePadView"
    }

    // Creation:
    // Constructors
    // onFinishInflate

    init {
        inflate(context, R.layout.layout_scribblepad, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        Timber.d("[$TAG] onFinishInflate()")
    }

    // TODO: Following overrriding functions to be removed later
    // Layout:
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Timber.d("[$TAG] onAttachToWindow()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Timber.d("[$TAG] onLayout()")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Timber.d("[$TAG] onSizeChanged()")
    }

    // Drawing:
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Timber.d("[$TAG] onDraw()")
    }

    // Event Processing:
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Timber.d("[$TAG] onKeyDown()")
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Timber.d("[$TAG] onKeyUp()")
        return super.onKeyUp(keyCode, event)
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        Timber.d("[$TAG] onTrackballEvent()")
        return super.onTrackballEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Timber.d("[$TAG] onTouchEvent()")
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        Timber.d("[$TAG] performClick()")
        return super.performClick()
    }

    // Focus:
    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        Timber.d("[$TAG] onFocusChanged()")
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
    }

    // Attaching:
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Timber.d("[$TAG] onDetachedFromWindow()")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Timber.d("[$TAG] onAttachToWindow()")
    }
}
