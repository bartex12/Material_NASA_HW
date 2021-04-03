package geekbarains.material.ui.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import geekbarains.material.ui.tabs.pictureofday.PictureOfTheDayFragment
import geekbarains.material.ui.tabs.map.MapFragment
import geekbarains.material.ui.tabs.earth.EarthFragment
import geekbarains.material.ui.tabs.mars.MarsFragment

class ViewPageAdapter(fragmentManager : FragmentManager)
    : FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object{
       const val PICTURE_OF_DAY = 0
       const val EARTH = 1
       const val MARS = 2
       const val WORLD_MAP = 3
    }

   private val fragments = arrayOf(
       PictureOfTheDayFragment(),
       EarthFragment(),
       MarsFragment(),
       MapFragment())

    override fun getItem(position: Int): Fragment {
        return  when(position){
            0->fragments[PICTURE_OF_DAY]
            1->fragments[EARTH]
            2->fragments[MARS]
            3->fragments[WORLD_MAP]
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
            3->"Карта мира"
            else -> "Фото дня"
        }
    }
}