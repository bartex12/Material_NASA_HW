package geekbarains.material.ui.picture

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.api.clear
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import geekbarains.material.R
import geekbarains.material.ui.MainActivity
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
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

        val viewModel: PictureOfTheDayViewModel by lazy {
            ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        }

        navigation_view.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.navigation_video -> {
                    val todayVideoAsString = "2021-01-11"
                    listener.onItemClick(todayVideoAsString)
                }
                R.id.navigation_today ->{
                    val todayAsString =
                        dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, 0)}.time)
                    //Log.d(TAG, "BottomNavigationDrawerFragment onActivityCreated todayAsString = $todayAsString")
                    listener.onItemClick(todayAsString)
                }
                R.id.navigation_yesterday ->{
                    val yesterdayAsString =
                        dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, -1)}.time)
                    //Log.d(TAG, "BottomNavigationDrawerFragment onActivityCreated yesterdayAsString = $yesterdayAsString")
                    listener.onItemClick(yesterdayAsString)
                }
                R.id.navigation_before_yesterday ->{
                    val beforeYesterdayAsString =
                        dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, -2)}.time)
                    //Log.d(TAG, "BottomNavigationDrawerFragment onActivityCreated beforeYesterdayAsString = $beforeYesterdayAsString")
                    listener.onItemClick(beforeYesterdayAsString)
                }
            }
            this.dismiss()
            true
        }
    }
}
