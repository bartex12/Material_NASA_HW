package geekbarains.material.ui.tabs.earth

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.squareup.picasso.Picasso
import geekbarains.material.R
import kotlinx.android.synthetic.main.activity_animations.*
import kotlinx.android.synthetic.main.item_mars.view.*

class EarthRecyclerAdapter(val onItemClickListener:OnItemClickListener): RecyclerView.Adapter<EarthRecyclerAdapter.ViewHolder>() {

    companion object{
        const val TAG = "33333"
    }
   lateinit var context:Context
   var  isExpanded = false

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

//                //определяем анимацию увеличения по щелску
//                    isExpanded = !isExpanded
//                    TransitionManager.beginDelayedTransition(
//                        itemView.contaner_earth, TransitionSet()
//                            .addTransition(ChangeBounds())
//                            .addTransition(ChangeImageTransform())
//                    )
//
//                    val params: ViewGroup.LayoutParams = itemView.iv_mars.layoutParams
//                    params.height =
//                        if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
//                itemView.iv_mars.layoutParams = params
//                itemView.iv_mars.scaleType =
//                        if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER

            }
        }

    }
}