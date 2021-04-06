package geekbarains.material.ui.favorite

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import geekbarains.material.R
import geekbarains.material.room.Favorite
import kotlinx.android.synthetic.main.favorite_foto.*
import kotlinx.android.synthetic.main.favorite_foto.view.*
import kotlinx.android.synthetic.main.item_earth.view.*

class FavoriteRVAdapter (private val onitemClickListener: OnitemClickListener)
    : RecyclerView.Adapter<FavoriteRVAdapter.ViewHolder> (){

    companion object {
        const val TAG = "33333"
    }
    lateinit var context: Context
    var isOpenDescription = false

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
        context = parent.context
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
            itemView.fotoDescriptionTextViewEdit.text = favorite.title

            //грузим картинку
            Picasso.with(context)
                .load(favorite.url)
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(itemView.fotoImageViewEdit)

            itemView.showTextFoto.setOnClickListener {
                Log.d(TAG, "FavoriteRVAdapter setOnClickListener ")
                 isOpenDescription  = !isOpenDescription
                if (isOpenDescription){
                    itemView.fotoDescriptionTextViewEdit.visibility = View.VISIBLE
                }else{
                    itemView.fotoDescriptionTextViewEdit.visibility = View.GONE
                }
            }

            itemView.fotoImageViewEdit.setOnClickListener {
                onitemClickListener.onItemclick(favorite)
            }
        }
    }
}