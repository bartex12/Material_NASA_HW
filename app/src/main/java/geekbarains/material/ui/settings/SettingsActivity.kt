package geekbarains.material.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import geekbarains.material.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object{
        const val TAG = "33333"
    }
    private var oldTheme:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SettingsActivity onCreate ")
        //читаем сохранённную в настройках тему
        oldTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        //устанавливаем сохранённную в настройках тему
        when(oldTheme){
            1->setTheme(R.style.AppTheme)
            2->setTheme(R.style.AppThemePurple)
            3->setTheme(R.style.AppThemeBlack)
        }

        setContentView(R.layout.activity_settings)
        //при запуске
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        //поддержка экшенбара
        setSupportActionBar(toolbar_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //ставим слушатель нажатия на стрелку Назад в тулбаре
        toolbar_settings.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            findPreference<ListPreference>("ListColor")
                ?.setOnPreferenceChangeListener { _, newValue ->
                    val  oldTheme = PreferenceManager.getDefaultSharedPreferences(requireActivity())
                        .getString("ListColor", "1")
                    if (newValue != oldTheme){
                        Log.d(TAG, "1 SettingsFragment ListPreferenceTheme newValue = $newValue" +
                                " oldTheme = $oldTheme ")
                        requireActivity().recreate()
                    }else{
                        Log.d(TAG, "2 SettingsFragment ListPreferenceTheme newValue = $newValue" +
                                " oldTheme = $oldTheme ")
                    }
                    true
                }
        }
    }

}