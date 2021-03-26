package geekbarains.material.ui.tabs

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import geekbarains.material.R
import geekbarains.material.ui.settings.SettingsActivity
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.fragment_api.*

class ApiFragment:Fragment() {

    companion object{
        const val TAG = "33333"
        fun newInstance() = ApiFragment()
    }

//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_api,container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //разрешаем показ меню во фрагменте
        setHasOptionsMenu(true)

        view_pager.adapter = ViewPageAdapter(childFragmentManager)
        tab_layout.setupWithViewPager(view_pager)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_settings ->
                startActivity(Intent(requireActivity(), SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}