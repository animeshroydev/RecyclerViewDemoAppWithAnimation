package com.animesh.android.creatures.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animesh.android.creatures.R
import com.animesh.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : androidx.fragment.app.Fragment(), ItemDragListener {

  private val adapter = CreatureAdapter(mutableListOf(), this)
  private lateinit var itemTouchHelper: ItemTouchHelper

  companion object {
    fun newInstance(): FavoritesFragment {
      return FavoritesFragment()
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_favorites, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    favoritesRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
    favoritesRecyclerView.adapter = adapter
    setupItemTouchHelper()

    val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
    favoritesRecyclerView.addItemDecoration(DividerItemDecoration(ContextCompat.getColor(context!!, R.color.black), heightInPixels))
  }

  override fun onResume() {
    super.onResume()
    val favorites = CreatureStore.getFavoriteCreatures(context!!)
    favorites?.let {
      adapter.updateCreatures(favorites)
    }
  }

  private fun setupItemTouchHelper() {
    itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
    itemTouchHelper.attachToRecyclerView(favoritesRecyclerView)
  }

  override fun onItemDrag(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
    itemTouchHelper.startDrag(viewHolder)
  }
}