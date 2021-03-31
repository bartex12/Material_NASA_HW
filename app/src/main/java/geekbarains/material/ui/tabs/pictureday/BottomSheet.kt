package geekbarains.material.ui.tabs.pictureday

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import geekbarains.material.R

class BottomSheet(val view: View, val bottom_navigation_view:BottomNavigationView?) {

    var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
    val bottomSheet: ConstraintLayout = initBottomSheet(view)

    //находим корневой лейаут и подключаем BottomSheet
    private fun initBottomSheet(view: View): ConstraintLayout {
        val bottomSheet: ConstraintLayout = view.findViewById(R.id.bottom_sheet_container)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheet.visibility = View.INVISIBLE
        setBottomSheetBehavior(bottomSheet)
        return bottomSheet
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {

        bottomSheetBehavior?. let {it.state = BottomSheetBehavior.STATE_HALF_EXPANDED}

        //скрываем bottom_app_bar при движении bottomSheet и показываем при сворачивании
        bottomSheetBehavior?.addBottomSheetCallback( object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING ){
                    if (bottom_navigation_view != null) {
                        bottom_navigation_view.visibility =View.GONE
                    }
                }else if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    if (bottom_navigation_view != null) {
                        bottom_navigation_view.visibility = View.VISIBLE
                       // bottomSheet.visibility =View.GONE // убирается при сворачивании
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

}