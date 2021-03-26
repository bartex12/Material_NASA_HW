package geekbarains.material.ui.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import geekbarains.material.ui.picture.PictureOfTheDayFragment
import geekbarains.material.ui.tabs.earth.EarthFragment
import geekbarains.material.ui.tabs.mars.MarsFragment
import geekbarains.material.ui.tabs.weather.WeatherFragment

class ViewPageAdapter(val fragmentManager : FragmentManager)
    : FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object{
       const val PICTURE_OF_DAY = 0
       const val EARTH = 1
       const val MARS = 2
       const val WEATHER = 3
    /*   const val SEARCH = 4*/

    }

   private val fragments = arrayOf(PictureOfTheDayFragment(),
       EarthFragment(),
       MarsFragment(),
       WeatherFragment()/*, SearchFragment()*/)

    override fun getItem(position: Int): Fragment {
        return  when(position){
            0->fragments[PICTURE_OF_DAY]
            1->fragments[EARTH]
            2->fragments[MARS]
            3->fragments[WEATHER]
           /* 4 -> fragments[SEARCH]*/
            else -> fragments[PICTURE_OF_DAY]
        }
    }

    override fun getCount(): Int {
      return  fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return  when(position){
            0->"Фото дня"
            1->"Земля"
            2->"Марс"
            3->"Погода"
            /*4-> "Поиск"*/
            else -> "Фото дня"
        }
    }
}