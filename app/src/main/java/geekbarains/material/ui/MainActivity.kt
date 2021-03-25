package geekbarains.material.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import geekbarains.material.R
import geekbarains.material.ui.api.ApiFragment
import geekbarains.material.ui.picture.PictureOfTheDayFragment

//в макете активити только FrameLayout - ни тулбара, ни шторки нет
//все навороты - во фрагменте с фото дня
class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ApiFragment.newInstance())
                .commitNow()
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
