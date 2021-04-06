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
import kotlinx.android.synthetic.main.favorite_foto.view.fotoDescriptionTextViewEdit
import kotlinx.android.synthetic.main.favorite_foto.view.showTextFoto
import kotlinx.android.synthetic.main.favorite_video.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_earth.view.*

class FavoriteRVAdapter (private val onitemClickListener: OnitemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder> (){

    companion object {
        const val TAG = "33333"
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
        private const val TYPE_HEADER = 2

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)

        return  when(viewType){
            TYPE_IMAGE ->ImageViewHolder(inflater.inflate(R.layout.favorite_foto, parent, false))
            TYPE_VIDEO ->VideoViewHolder(inflater.inflate(R.layout.favorite_video, parent, false))
            else ->HeaderViewHolder(inflater.inflate(R.layout.favorite_header, parent, false))

        }
    }

    //здесь описываем как различаем типы
    override fun getItemViewType(position: Int): Int {
        return when{
            position == 0 -> TYPE_HEADER
            listFavorites[position].type == "image" -> TYPE_IMAGE
            //listFavorites[position].type == "video" -> TYPE_VIDEO
            else -> TYPE_VIDEO
        }
    }

    override fun getItemCount(): Int = listFavorites.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TYPE_IMAGE-> ( holder as ImageViewHolder ).bind(listFavorites[position])
            TYPE_VIDEO-> ( holder as VideoViewHolder ).bind(listFavorites[position])
            else-> ( holder as HeaderViewHolder ).bind(listFavorites[position])
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                Log.d(TAG, "FavoriteRVAdapter setOnClickListener ImageViewHolder")
                 isOpenDescription  = !isOpenDescription
                if (isOpenDescription){
                    itemView.fotoDescriptionTextViewEdit.visibility = View.VISIBLE
                }else{
                    itemView.fotoDescriptionTextViewEdit.visibility = View.GONE
                }
            }
            //переход на экран просмотра
            itemView.fotoImageViewEdit.setOnClickListener {
                onitemClickListener.onItemclick(favorite)
            }
        }
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite){

            itemView.fotoTitleVideo.text = favorite.title
            itemView.fotoDateVideo.text = favorite.date
            itemView.fotoDescriptionVideo.text = favorite.title

            itemView. web_view_video_anim.settings.javaScriptEnabled = true  // небезопасно тащить скрипты
            favorite.url?. let{itemView. web_view_video_anim.loadUrl(it)}

            itemView.showTextVideo.setOnClickListener {
                Log.d(TAG, "FavoriteRVAdapter setOnClickListener VideoViewHolder")
                isOpenDescription  = !isOpenDescription
                if (isOpenDescription){
                    itemView.fotoDescriptionVideo.visibility = View.VISIBLE
                }else{
                    itemView.fotoDescriptionVideo.visibility = View.GONE
                }
            }
            //переход на экран просмотра
            //web_view накрыто прозрачной шторкой videoScreen - поэтому не запускается а идёт переход
            itemView. videoScreen.setOnClickListener {
                onitemClickListener.onItemclick(favorite)
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite){
            //
        }
    }

}