package geekbarains.material.ui.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import geekbarains.material.R
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "33333"
    }

    lateinit var  listener:OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(date:String)
    }
     fun setOnItemClickListener(listener:OnItemClickListener){
         this.listener = listener
     }

    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_video -> {
                    val todayVideoAsString = "2021-01-11"
                    listener.onItemClick(todayVideoAsString)
                }
                R.id.navigation_3days_ago ->{
                    val todayAsString =
                        dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, -3)}.time)
                    listener.onItemClick(todayAsString)
                }
                R.id.navigation_4days_ago ->{
                    val yesterdayAsString =
                        dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, -4)}.time)
                    listener.onItemClick(yesterdayAsString)
                }
                R.id.navigation_5days_ago ->{
                    val beforeYesterdayAsString =
                        dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, -5)}.time)
                    listener.onItemClick(beforeYesterdayAsString)
                }
            }
            this.dismiss()
            true
        }
    }
}
