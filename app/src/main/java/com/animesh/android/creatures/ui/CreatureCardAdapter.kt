package com.animesh.android.creatures.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.animesh.android.creatures.R
import com.animesh.android.creatures.app.Constants
import com.animesh.android.creatures.app.inflate
import com.animesh.android.creatures.model.Creature
import kotlinx.android.synthetic.main.list_item_creature_card_jupiter.view.*
import java.util.*

class CreatureCardAdapter(private val creatures: MutableList<Creature>)
  : androidx.recyclerview.widget.RecyclerView.Adapter<CreatureCardAdapter.ViewHolder>(), ItemTouchHelperListener {

  var scrollDirection = ScrollDirection.DOWN
  var jupiterSpanSize = 2

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      ViewType.OTHER.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature_card))
      ViewType.MARS.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature_card_mars))
      ViewType.JUPITER.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature_card_jupiter))
      else -> throw IllegalArgumentException("Illegal value for viewType")
    }
  }

  override fun onBindViewHolder(holder: CreatureCardAdapter.ViewHolder, position: Int) {
    holder.bind(creatures[position])
  }

  override fun getItemCount() = creatures.size

  override fun getItemViewType(position: Int) =
    when (creatures[position].planet) {
      Constants.JUPITER -> ViewType.JUPITER.ordinal
      Constants.MARS -> ViewType.MARS.ordinal
      else -> ViewType.OTHER.ordinal
    }

  fun spanSizeAtPosition(position: Int): Int {
    return if (creatures[position].planet == Constants.JUPITER) {
      jupiterSpanSize
    } else {
      1
    }
  }

  override fun onItemMove(recyclerView: androidx.recyclerview.widget.RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
    if (fromPosition < toPosition) {
      for (i in fromPosition until toPosition) {
        Collections.swap(creatures, i, i + 1)
      }
    } else {
      for (i in fromPosition downTo toPosition + 1) {
        Collections.swap(creatures, i, i - 1)
      }
    }
    notifyItemMoved(fromPosition, toPosition)
    return true
  }

  override fun onItemDismiss(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
  }

  inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var creature: Creature

    init {
      itemView.setOnClickListener(this)
    }

    fun bind(creature: Creature) {
      this.creature = creature
      val context = itemView.context
      val imageResource = context.resources.getIdentifier(creature.thumbnail, null, context.packageName)
      itemView.creatureImage.setImageResource(imageResource)
      itemView.fullName.text = creature.fullName
      setBackgroundColors(context, imageResource)
      animateView(itemView)
    }

    override fun onClick(view: View) {
      val context = view.context
      val intent = CreatureActivity.newIntent(context, creature.id)
      context.startActivity(intent)
    }

    private fun setBackgroundColors(context: Context, imageResource: Int) {
      val image = BitmapFactory.decodeResource(context.resources, imageResource)
      androidx.palette.graphics.Palette.from(image).generate { palette ->
        val backgroundColor = palette.getDominantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        itemView.creatureCard.setBackgroundColor(backgroundColor)
        itemView.nameHolder.setBackgroundColor(backgroundColor)
        val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
        itemView.fullName.setTextColor(textColor)
        if (itemView.slogan != null) {
          itemView.slogan.setTextColor(textColor)
        }
      }
    }

    private fun isColorDark(color: Int): Boolean {
      val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
      return darkness >= 0.5
    }

    private fun animateView(viewToAnimate: View) {
      if (viewToAnimate.animation == null) {
        val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.scale_xy)
        viewToAnimate.animation = animation
      }
    }
  }

  enum class ScrollDirection {
    UP, DOWN
  }

  enum class ViewType {
    JUPITER, MARS, OTHER
  }
}