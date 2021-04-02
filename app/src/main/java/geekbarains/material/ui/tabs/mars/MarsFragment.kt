package geekbarains.material.ui.tabs.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import geekbarains.material.R

class MarsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false )

    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //инициализация нижнего меню фрагмента - слушатель на нажатие пункта меню
//        initBottomNavigationView(view)
//    }

//    private fun initBottomNavigationView(view:View) {
//
//        bottom_navigation_weather.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> {
//                    val viewPager= view.rootView.findViewById<ViewPager>(R.id.view_pager)
//                    viewPager.setCurrentItem(0)
//                    true
//                }
//                R.id.bottom_view_search -> {
//                    requireActivity().supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, SearchFragment())
//                        .addToBackStack("search")
//                        .commit()
//                    true
//                }
//                R.id.app_bar_settings -> {
//                    startActivity(Intent(requireActivity(), SettingsActivity::class.java))
//                    true
//                }
//                else -> false
//            }
//        }
//    }
}