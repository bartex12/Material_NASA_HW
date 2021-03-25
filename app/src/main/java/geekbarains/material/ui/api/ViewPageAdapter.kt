package geekbarains.material.ui.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import geekbarains.material.ui.picture.PictureOfTheDayFragment

class ViewPageAdapter(val fragmentManager : FragmentManager)
    : FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
   private val fragments = arrayOf(PictureOfTheDayFragment(),
        EarthFragment(),MarsFragment(), WeatherFragment())

    override fun getItem(position: Int): Fragment {
        return  when(position){
            0->fragments[0]
            1->fragments[1]
            2->fragments[2]
            3->fragments[3]
            else -> fragments[0]
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
            else -> "Фото дня"
        }
    }
}