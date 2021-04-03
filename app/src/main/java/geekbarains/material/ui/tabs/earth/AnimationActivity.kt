package geekbarains.material.ui.tabs.earth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.squareup.picasso.Picasso
import geekbarains.material.R
import geekbarains.material.ui.MainActivity
import geekbarains.material.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_animations.*

class AnimationActivity : AppCompatActivity() {

    companion object{
        const val URL_ANIMATION = "URL_ANIMATION"
        const val TAG = "33333"
    }
    private var oldTheme:Int = 1
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //читаем сохранённную в настройках тему
        oldTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        //устанавливаем сохранённную в настройках тему
        when(oldTheme){
            1->setTheme(R.style.AppTheme)
            2->setTheme(R.style.AppThemePurple)
            3->setTheme(R.style.AppThemeBlack)
        }
        setContentView(R.layout.activity_animations)

        //поддержка экшенбара
        setSupportActionBar(toolbar_anim)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        //ставим слушатель нажатия на стрелку Назад в тулбаре
        toolbar_anim.setNavigationOnClickListener {
            onBackPressed()
        }

        //текстовое поле в тулбаре
        with(toolbar_anim.findViewById<TextView>(R.id.anim_title)){
            textSize = 16f
            setTextColor(Color.WHITE)
            text = "" /*context.getString(R.string.The_universe_in_the_palm)*/
        }

        val url = intent.getStringExtra(URL_ANIMATION)

        url?. let{
            Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(image_view_animate)
        }

        image_view_animate.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                container_animate, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = image_view_animate.layoutParams
            params.height =
                if (isExpanded){ ViewGroup.LayoutParams.MATCH_PARENT }
                else{ ViewGroup.LayoutParams.WRAP_CONTENT}
            image_view_animate.layoutParams = params

            image_view_animate.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP
                else ImageView.ScaleType.FIT_CENTER

            //ставим чёрный по всем краям
            container_animate.setBackgroundColor(Color.BLACK)
            image_view_animate.setBackgroundColor(Color.BLACK)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(MainActivity.TAG, "AnimationActivity onResume " )
        //при изменении темы и возвращении из настроек проверяем - какая тема установлена
        val newTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        Log.d(MainActivity.TAG, "AnimationActivity onResume newTheme = $newTheme  oldTheme = $oldTheme ")
        if (newTheme != oldTheme){
            recreate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar_anim, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_anim ->
                startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}