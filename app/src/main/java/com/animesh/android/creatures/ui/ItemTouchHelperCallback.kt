
package com.animesh.android.creatures.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper


class ItemTouchHelperCallback(private val listener: ItemTouchHelperListener) : ItemTouchHelper.Callback() {

  override fun isLongPressDragEnabled() = false

  override fun isItemViewSwipeEnabled() = true

  override fun getMovementFlags(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder): Int {
    return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.END)
  }

  override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
    return listener.onItemMove(recyclerView, viewHolder.adapterPosition, target.adapterPosition)
  }

  override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
    listener.onItemDismiss(viewHolder, viewHolder.adapterPosition)
  }

  override fun onSelectedChanged(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, actionState: Int) {
    if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
      if (viewHolder is ItemSelectedListener) {
        viewHolder.onItemSelected()
      }
    }
    super.onSelectedChanged(viewHolder, actionState)
  }

  override fun clearView(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
    super.clearView(recyclerView, viewHolder)
    if (viewHolder is ItemSelectedListener) {
      viewHolder.onItemCleared()
    }
  }
}