package geekbarains.material.ui.tabs.weather

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import geekbarains.material.R
import geekbarains.material.ui.search.SearchFragment
import geekbarains.material.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //инициализация нижнего меню фрагмента - слушатель на нажатие пункта меню
        initBottomNavigationView(view)
    }

    private fun initBottomNavigationView(view:View) {

        bottom_navigation_weather.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val viewPager= view.rootView.findViewById<ViewPager>(R.id.view_pager)
                    viewPager.setCurrentItem(0)
                    true
                }
                R.id.bottom_view_search -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment())
                        .addToBackStack("search")
                        .commit()
                    true
                }
                R.id.app_bar_settings -> {
                    startActivity(Intent(requireActivity(), SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}