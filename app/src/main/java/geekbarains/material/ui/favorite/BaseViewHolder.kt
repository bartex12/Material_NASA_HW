package geekbarains.material.ui.favorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.room.Favorite

abstract class BaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(favorite:Favorite)
}