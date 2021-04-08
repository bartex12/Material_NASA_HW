package geekbarains.material.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.google.android.material.navigation.NavigationView
import geekbarains.material.R
import geekbarains.material.ui.favorite.FavoriteFragment
import geekbarains.material.ui.search.SearchFragment
import geekbarains.material.ui.settings.SettingsActivity
import geekbarains.material.ui.tabs.ApiFragment
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    companion object{
        const val TAG = "33333"
    }
    private var oldTheme:Int = 1
    private var oldType:Int = 1
    private var toggle:ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate " )

        //без этого что- то не работает - google требует установить
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(applicationContext)
            val sslContext: SSLContext = SSLContext.getInstance("TLSv1.2")
            sslContext.init(null, null, null)
            sslContext.createSSLEngine()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

//        val favoriteListFotos = PreferenceManager.getDefaultSharedPreferences(this)
//            .getStringSet("ListFavoriteFotos", mutableSetOf())
//        if (favoriteListFotos != null) {
//            if (favoriteListFotos.size == 0) {
//
//            }
//        }

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

        setSupportActionBar(toolbar) //поддержка экшенбара
        //отключаем показ заголовка тулбара, так как там свой макет с main_title
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toggle = ActionBarDrawerToggle(this,drawer_layout,
            toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close )//гамбургер
        toggle?. let{ drawer_layout.addDrawerListener(it)}  //слушатель гамбургера
        toggle?.syncState() //синхронизация гамбургера

        //https://stackoverflow.com/questions/28531503/toolbar-switching-from-drawer-to-back-
        // button-with-only-one-activity/29292130#29292130
        //если в BackStack больше одного фрагмента (там почему то всегда есть 1 фрагмент)
        //то отображаем стрелку назад и устанавливаем слушатель на щелчок по ней с действием
        //onBackPressed(), иначе отображаем гамбургер и по щелчку открываем шторку
        supportFragmentManager.addOnBackStackChangedListener {  //слушатель BackStack
            if(supportFragmentManager.backStackEntryCount > 0){
                supportActionBar?.setDisplayHomeAsUpEnabled(true) //показать стрелку
                toolbar.setNavigationOnClickListener { // слушатель кнопки навигации- стрелка
                    onBackPressed()
                }
            }else{
                supportActionBar?.setDisplayHomeAsUpEnabled(false) //не показывать стрелку
                toggle?.syncState()
                toolbar.setNavigationOnClickListener {// слушатель кнопки навигации- гамбургер
                    drawer_layout.openDrawer(GravityCompat.START)
                }
            }
        }

        nav_view.setNavigationItemSelectedListener(this) //слушатель меню шторки

        //текстовое поле в тулбаре
        with(toolbar.findViewById<TextView>(R.id.main_title)){
            textSize = 16f
            setTextColor(Color.WHITE)
            text = context.getString(R.string.app_name)
        }
    }

//        //поддержка экшенбара если бы не было шторки
//        setSupportActionBar(toolbar)
//        //отключаем показ заголовка тулбара, так как там свой макет с main_title
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        Log.d(TAG, "MainActivity onCreate backStackEntryCount =" +
//                "${supportFragmentManager.backStackEntryCount}" )
//        //https://stackoverflow.com/questions/28531503/toolbar-switching-from-drawer-to-back-
//        // button-with-only-one-activity/29292130#29292130
//        //если в BackStack есть  фрагменты
//        //то отображаем стрелку назад и устанавливаем слушатель на щелчок по ней с действием
//        //onBackPressed(), иначе не показываем стрелку
//        supportFragmentManager.addOnBackStackChangedListener {  //слушатель BackStack
//            if(supportFragmentManager.backStackEntryCount > 0){
//                supportActionBar?.setDisplayHomeAsUpEnabled(true) //показать стрелку
//                toolbar.setNavigationOnClickListener { // слушатель кнопки навигации- стрелка
//                    onBackPressed()
//                }
//            }else{
//                supportActionBar?.setDisplayHomeAsUpEnabled(false)//не показывать стрелку
//            }
//        }



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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_favorites -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_favorites")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FavoriteFragment())
                    .addToBackStack("FavoriteFragment")
                    .commit()
            }
            R.id.nav_setting -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_setting")
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_search_wiki ->{
               supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SearchFragment())
                    .addToBackStack("SearchFragment")
                    .commit()
            }
//            R.id.nav_help -> {
//                Log.d(TAG, "MainActivity onNavigationItemSelected nav_help")
//               //showHelp()
//            }

            R.id.nav_share -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_share")
                //поделиться - передаём ссылку на приложение в маркете
                //shareApp()
                toast(getString(R.string.stub))
            }
            R.id.nav_rate -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_send")
                //оценить приложение - попадаем на страницу приложения в маркете
                //rateApp()
                toast(getString(R.string.stub))
            }
        }
        // Выделяем выбранный пункт меню в шторке
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }
}
