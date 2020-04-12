package com.animesh.android.creatures.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.*
import com.animesh.android.creatures.R
import com.animesh.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.fragment_all.*


class AllFragment : androidx.fragment.app.Fragment() {

  private val adapter = CreatureCardAdapter(CreatureStore.getCreatures().toMutableList())
  private lateinit var layoutManager: androidx.recyclerview.widget.GridLayoutManager
  private lateinit var listItemDecoration: androidx.recyclerview.widget.RecyclerView.ItemDecoration
  private lateinit var gridItemDecoration: androidx.recyclerview.widget.RecyclerView.ItemDecoration
  private lateinit var listMenuItem: MenuItem
  private lateinit var gridMenuItem: MenuItem
  private var gridState = GridState.GRID

  companion object {
    fun newInstance(): AllFragment {
      return AllFragment()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater.inflate(R.menu.menu_all, menu)
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    super.onPrepareOptionsMenu(menu)
    listMenuItem = menu.findItem(R.id.action_span_1)
    gridMenuItem = menu.findItem(R.id.action_span_2)
    when (gridState) {
      GridState.LIST -> {
        listMenuItem.isEnabled = false
        gridMenuItem.isEnabled = true
      }
      GridState.GRID -> {
        listMenuItem.isEnabled = true
        gridMenuItem.isEnabled = false
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    when (id) {
      R.id.action_span_1 -> {
        gridState = GridState.LIST
        updateRecyclerView(1, listItemDecoration, gridItemDecoration)
        return true
      }
      R.id.action_span_2 -> {
        gridState = GridState.GRID
        updateRecyclerView(2, gridItemDecoration, listItemDecoration)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_all, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupRecyclerView()
    setupItemDecoration()
    setupScrollListener()
    setupItemTouchHelper()
  }

  private fun setupRecyclerView() {
    layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2, androidx.recyclerview.widget.GridLayoutManager.VERTICAL, false)
    layoutManager.spanSizeLookup = object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return (adapter.spanSizeAtPosition(position))
      }
    }
    creatureRecyclerView.layoutManager = layoutManager
    creatureRecyclerView.adapter = adapter
  }

  private fun setupItemDecoration() {
    val spacingInPixels = resources.getDimensionPixelSize(R.dimen.creature_card_grid_layout_margin)

    listItemDecoration = SpacingItemDecoration(1, spacingInPixels)
    gridItemDecoration = SpacingItemDecoration(2, spacingInPixels)

    creatureRecyclerView.addItemDecoration(gridItemDecoration)
  }

  private fun setupScrollListener() {
    creatureRecyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        adapter.scrollDirection = if (dy > 0) {
          CreatureCardAdapter.ScrollDirection.DOWN
        } else {
          CreatureCardAdapter.ScrollDirection.UP
        }
      }
    })
  }

  private fun updateRecyclerView(spanCount: Int, addItemDecoration: androidx.recyclerview.widget.RecyclerView.ItemDecoration, removeItemDecoration: androidx.recyclerview.widget.RecyclerView.ItemDecoration) {
    layoutManager.spanCount = spanCount
    adapter.jupiterSpanSize = spanCount
    creatureRecyclerView.removeItemDecoration(removeItemDecoration)
    creatureRecyclerView.addItemDecoration(addItemDecoration)
  }

  private fun setupItemTouchHelper() {
    val itemTouchHelper = ItemTouchHelper(GridItemTouchHelperCallback(adapter))
    itemTouchHelper.attachToRecyclerView(creatureRecyclerView)
  }

  private enum class GridState {
    LIST, GRID
  }
}