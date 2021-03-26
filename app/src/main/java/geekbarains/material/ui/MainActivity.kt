package geekbarains.material.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import geekbarains.material.R
import geekbarains.material.ui.api.ApiFragment
import geekbarains.material.ui.picture.BottomNavigationDrawerFragment
import geekbarains.material.ui.picture.BottomSheet
import geekbarains.material.ui.picture.PictureOfTheDayFragment
import geekbarains.material.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

//в макете активити только FrameLayout - ни тулбара, ни шторки нет
//все навороты - во фрагменте с фото дня
class MainActivity : AppCompatActivity(){

    companion object{
        const val TAG = "33333"
    }
    private var oldTheme:Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate " )

        //читаем сохранённную в настройках тему
        oldTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        //устанавливаем сохранённную в настройках тему
        when(oldTheme){
            1->setTheme(R.style.AppTheme)
            2->setTheme(R.style.AppThemePurple)
            3->setTheme(R.style.AppThemeBlack)
        }
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ApiFragment.newInstance())
                .commitNow()
        }

        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        
        with(toolbar.findViewById<TextView>(R.id.main_title)){
           textSize = 16f
           setTextColor(Color.WHITE)
           text = context.getString(R.string.The_universe_in_the_palm)
       }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume " )
        //при изменении темы и возвращении из настроек проверяем - какая тема установлена
        val newTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        Log.d(TAG, "MainActivity onResume newTheme = $newTheme  oldTheme = $oldTheme ")
        if (newTheme != oldTheme){
            recreate()
        }
    }
}
