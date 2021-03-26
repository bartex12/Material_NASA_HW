package geekbarains.material.ui.api

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import geekbarains.material.R
import geekbarains.material.ui.picture.BottomNavigationDrawerFragment
import geekbarains.material.ui.picture.BottomSheet
import geekbarains.material.ui.search.SearchFragment
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

        view_pager.adapter =ViewPageAdapter(childFragmentManager)
        tab_layout.setupWithViewPager(view_pager)

//        //находим корневой лейаут и подключаем BottomSheet, передавая туда контекст и вью для манипуляций
//        val bottomSheet = BottomSheet(view, bottom_navigation_view)
//        Log.d(TAG, "ApiFragment onViewCreated bottomSheet = $bottomSheet" )
//        //инициализация нижнего меню фрагмента
//        initBottomNavigationView(bottomSheet.bottomSheet)
//
//        //находим корневой лейаут и подключаем BottomSheet
//        val bottomSheet: ConstraintLayout = view.findViewById(R.id.bottom_sheet_container)
//        //настраиваем  Behavior
//        setBottomSheetBehavior(bottomSheet)
//        //инициализация нижнего меню фрагмента - слушатель на нажатие пункта меню
//        initBottomNavigationView(bottomSheet)
    }

//    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        //скрываем bottom_app_bar при движении bottomSheet и показываем при сворачивании
//        bottomSheetBehavior.addBottomSheetCallback( object : BottomSheetBehavior.BottomSheetCallback(){
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                if (newState == BottomSheetBehavior.STATE_DRAGGING ){
//                    bottom_navigation_view.visibility =View.GONE
//                    //fab.visibility = View.GONE
//                }else if (newState == BottomSheetBehavior.STATE_COLLAPSED){
//                    bottom_navigation_view.visibility = View.VISIBLE
//                    //fab.visibility = View.VISIBLE
//                }
//            }
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//            }
//        })
//    }

//    private fun initBottomNavigationView(bottomSheet: ConstraintLayout) {
//        bottom_navigation_view.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.bottom_view_description -> {
//                    bottomSheet.visibility = View.VISIBLE
//                    true
//                }
//                R.id.bottom_view_search -> {
//                    view_pager.setCurrentItem(4)
//                    true
//                }
//                R.id.bottom_view_more -> {
//                        val dialog = BottomNavigationDrawerFragment()
//                        dialog.setOnItemClickListener(this)
//                        dialog.show(childFragmentManager, "tag_dialog_more")
//
//                    true
//                }
//                else -> false
//            }
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")

            R.id.app_bar_settings ->
                startActivity(Intent(requireActivity(), SettingsActivity::class.java))

            android.R.id.home -> {
                //todo
            }
        }
        return super.onOptionsItemSelected(item)
    }

}