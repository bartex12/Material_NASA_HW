package geekbarains.material.ui.tabs.earth

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import geekbarains.material.R
import kotlinx.android.synthetic.main.item_mars.view.*

class EarthRecyclerAdapter(val onItemClickListener:OnItemClickListener): RecyclerView.Adapter<EarthRecyclerAdapter.ViewHolder>() {

    companion object{
        const val TAG = "33333"
    }
   lateinit var context:Context

interface OnItemClickListener{
    fun onItemClick(url:String)
}

    var listOfPictures : List<PictureOfEarth>  = listOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mars, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  =  listOfPictures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfPictures[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(pictureOfEarth:PictureOfEarth){
            val date = pictureOfEarth.date?.take(10)
            val dateWithDiv = date?.replace("-", "/")
            val url ="https://epic.gsfc.nasa.gov/archive/natural/$dateWithDiv/" +
                    "jpg/${pictureOfEarth.image}.jpg "
            Log.d(TAG, "EarthRecyclerAdapter bind $url " )

            Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(itemView.iv_mars)

            itemView.textViewMars.text = pictureOfEarth.date

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(url)
            }
        }

    }
}