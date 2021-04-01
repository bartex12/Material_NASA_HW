package geekbarains.material.ui.tabs.mars

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
import kotlinx.android.synthetic.main.fragment_mars.*

class MarsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars,container, false )
    }

}