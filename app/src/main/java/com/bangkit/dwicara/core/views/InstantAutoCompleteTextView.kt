package com.bangkit.dwicara.core.views

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet


class InstantAutoCompleteTextView : androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView {
    constructor(context: Context) : super(context)
    constructor(arg0: Context, arg1: AttributeSet?) : super(arg0, arg1)
    constructor(arg0: Context, arg1: AttributeSet?, arg2: Int) : super(arg0, arg1, arg2)

    override fun enoughToFilter(): Boolean {
        return false
    }

    override fun onFocusChanged(
        focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused && filter != null) {
            showSoftInputOnFocus = false
            performFiltering(text, 0)
        }
    }
}