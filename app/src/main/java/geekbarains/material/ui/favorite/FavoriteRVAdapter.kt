package geekbarains.material.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.R
import geekbarains.material.room.Favorite
import kotlinx.android.synthetic.main.favorite_foto.view.*

class FavoriteRVAdapter (private val onitemClickListener: OnitemClickListener)
    : RecyclerView.Adapter<FavoriteRVAdapter.ViewHolder> (){

    interface OnitemClickListener{
        fun onItemclick(favorite: Favorite)
    }

    //так сделано чтобы передавать список в адаптер без конструктора
    // - через присвоение полю значения
    var listFavorites: List<Favorite> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_foto, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFavorites.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite){
            itemView.fotoTitleEdit.text = favorite.title
            itemView.fotoDateEdit.text = favorite.date

            itemView.setOnClickListener {
                onitemClickListener.onItemclick(favorite)
            }
        }
    }
}