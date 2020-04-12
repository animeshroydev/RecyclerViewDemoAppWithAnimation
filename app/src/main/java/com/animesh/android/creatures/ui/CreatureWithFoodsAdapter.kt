package com.animesh.android.creatures.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.animesh.android.creatures.R
import com.animesh.android.creatures.app.inflate
import com.animesh.android.creatures.model.Creature
import com.animesh.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.list_item_creature_with_foods.view.*

class CreatureWithFoodsAdapter(private val creatures: MutableList<Creature>) : androidx.recyclerview.widget.RecyclerView.Adapter<CreatureWithFoodsAdapter.ViewHolder>() {

  private val viewPool = androidx.recyclerview.widget.RecyclerView.RecycledViewPool()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val holder = ViewHolder(parent.inflate(R.layout.list_item_creature_with_foods))
    holder.itemView.foodRecyclerView.recycledViewPool = viewPool
    androidx.recyclerview.widget.LinearSnapHelper().attachToRecyclerView(holder.itemView.foodRecyclerView)
    return holder
  }

  override fun onBindViewHolder(holder: CreatureWithFoodsAdapter.ViewHolder, position: Int) {
    holder.bind(creatures[position])
  }

  override fun getItemCount() = creatures.size

  class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var creature: Creature

    private val adapter = FoodAdapter(mutableListOf())

    init {
      itemView.setOnClickListener(this)
    }

    fun bind(creature: Creature) {
      this.creature = creature
      val context = itemView.context
      itemView.creatureImage.setImageResource(context.resources.getIdentifier(creature.thumbnail, null, context.packageName))
      setupFoods()
    }

    override fun onClick(view: View) {
      val context = view.context
      val intent = CreatureActivity.newIntent(context, creature.id)
      context.startActivity(intent)
    }

    private fun setupFoods() {
      itemView.foodRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(itemView.context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
      itemView.foodRecyclerView.adapter = adapter

      val foods = CreatureStore.getCreatureFoods(creature)
      adapter.updateFoods(foods)
    }
  }
}