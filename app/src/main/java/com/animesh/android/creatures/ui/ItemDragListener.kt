package com.animesh.android.creatures.ui

import androidx.recyclerview.widget.RecyclerView



interface ItemDragListener {
  fun onItemDrag(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder)
}