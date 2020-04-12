package com.animesh.android.creatures.ui

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent


class NoSwipeViewPager(context: Context, attrs: AttributeSet) : androidx.viewpager.widget.ViewPager(context, attrs) {

  override fun onTouchEvent(event: MotionEvent) = false

  override fun onInterceptTouchEvent(event: MotionEvent) = false
}