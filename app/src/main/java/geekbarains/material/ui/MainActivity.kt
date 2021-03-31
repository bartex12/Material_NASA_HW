package geekbarains.material.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.preference.PreferenceManager
import geekbarains.material.R
import geekbarains.material.ui.tabs.ApiFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*

class MainActivity : AppCompatActivity(){

    companion object{
        const val TAG = "33333"
    }
    private var oldTheme:Int = 1
    private var oldType:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate " )

        //читаем сохранённный в настройках тип картинки
        oldType = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListEarth", "1")!!.toInt()
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
        //поддержка экшенбара
        setSupportActionBar(toolbar)
        //отключаем показ заголовка тулбара, так как там свой макет с main_title
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        Log.d(TAG, "MainActivity onCreate backStackEntryCount =" +
                "${supportFragmentManager.backStackEntryCount}" )
        //https://stackoverflow.com/questions/28531503/toolbar-switching-from-drawer-to-back-
        // button-with-only-one-activity/29292130#29292130
        //если в BackStack есть  фрагменты
        //то отображаем стрелку назад и устанавливаем слушатель на щелчок по ней с действием
        //onBackPressed(), иначе не показываем стрелку
        supportFragmentManager.addOnBackStackChangedListener {  //слушатель BackStack
            if(supportFragmentManager.backStackEntryCount > 0){
                supportActionBar?.setDisplayHomeAsUpEnabled(true) //показать стрелку
                toolbar.setNavigationOnClickListener { // слушатель кнопки навигации- стрелка
                    onBackPressed()
                }
            }else{
                supportActionBar?.setDisplayHomeAsUpEnabled(false)//не показывать стрелку
            }
        }
        //текстовое поле в тулбаре
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
        //читаем сохранённный в настройках тип картинки
        val  newType = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListEarth", "1")!!.toInt()
        val newTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        Log.d(TAG, "MainActivity onResume newTheme = $newTheme  oldTheme = $oldTheme ")
        if (newTheme != oldTheme||newType!=oldType){
            recreate()
        }
    }
}
