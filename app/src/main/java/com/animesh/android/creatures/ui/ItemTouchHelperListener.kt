
package com.animesh.android.creatures.ui

import androidx.recyclerview.widget.RecyclerView


interface ItemTouchHelperListener {
  fun onItemMove(recyclerView: androidx.recyclerview.widget.RecyclerView, fromPosition: Int, toPosition: Int): Boolean
  fun onItemDismiss(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int)
}