package com.animesh.android.creatures.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.animesh.android.creatures.R
import com.animesh.android.creatures.app.inflate
import com.animesh.android.creatures.model.Food
import kotlinx.android.synthetic.main.list_item_food.view.*


class FoodAdapter(private val foods: MutableList<Food>) : androidx.recyclerview.widget.RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_food))
  }

  override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {
    holder.bind(foods[position])
  }

  override fun getItemCount() = foods.size

  fun updateFoods(foods: List<Food>) {
    this.foods.clear()
    this.foods.addAll(foods)
    notifyDataSetChanged()
  }

  class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private lateinit var food: Food

    fun bind(food: Food) {
      this.food = food
      val context = itemView.context
      itemView.foodImage.setImageResource(context.resources.getIdentifier(food.thumbnail, null, context.packageName))
    }
  }
}