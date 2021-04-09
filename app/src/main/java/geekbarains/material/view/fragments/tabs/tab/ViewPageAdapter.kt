package geekbarains.material.view.fragments.tabs.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import geekbarains.material.view.fragments.tabs.pictureofday.PictureOfTheDayFragment
import geekbarains.material.view.fragments.tabs.map.MapFragment
import geekbarains.material.view.fragments.tabs.earth.EarthFragment
import geekbarains.material.view.fragments.tabs.mars.MarsFragment

class ViewPageAdapter(fragmentManager : FragmentManager)
    : FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object{
       const val PICTURE_OF_DAY = 0
       const val EARTH = 1
       const val WORLD_MAP = 2
        const val MARS = 3
    }

   private val fragments = arrayOf(
       PictureOfTheDayFragment(),
       EarthFragment(),
       MapFragment(),
       MarsFragment()
       )

    override fun getItem(position: Int): Fragment {
        return  when(position){
            0->fragments[PICTURE_OF_DAY]
            1->fragments[EARTH]
            2->fragments[WORLD_MAP]
            3->fragments[MARS]
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
            2->"Карта мира"
            3->"Марс"
            else -> "Фото дня"
        }
    }
}