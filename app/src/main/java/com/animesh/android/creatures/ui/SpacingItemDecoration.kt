package com.animesh.android.creatures.ui

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View


class SpacingItemDecoration(private val spanCount: Int, private val spacing: Int)
  : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
    val position = parent.getChildAdapterPosition(view)

    outRect.top = spacing / 2
    outRect.bottom = spacing / 2
    outRect.left = spacing / 2
    outRect.right = spacing / 2

    // Adjust top edge
    if (position < spanCount) {
      outRect.top = spacing
    }

    // Adjust left edge
    if (position % spanCount == 0) {
      outRect.left = spacing
    }

    // Adjust right edge
    if ((position + 1) % spanCount == 0) {
      outRect.right = spacing
    }
  }
}