package geekbarains.material.ui.picture

import android.app.ProgressDialog.show
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.clear
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import geekbarains.material.R
import geekbarains.material.ui.MainActivity
import geekbarains.material.ui.chips.ChipsFragment
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    companion object {
        const val TAG = "33333"
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

    val dateFormat: DateFormat =SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "PictureOfTheDayFragment onViewCreated ")
        //находим корневой лейаут и подключаем BottomSheet
        val bottomSheet: ConstraintLayout = view.findViewById(R.id.bottom_sheet_container)
        setBottomSheetBehavior(bottomSheet)

        input_layout.setEndIconOnClickListener {
            if (input_edit_text.text.toString().isNotBlank()){
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
                })
            }else{
                toast(resources.getString(R.string. input_any_text))
            }
        }
        //подключаем BottomBar с fab
        setBottomAppBar(view)

        chipGroupMain.setOnCheckedChangeListener { group, id ->
            Log.d(TAG, "PictureOfTheDayFragment onViewCreated setOnCheckedChangeListener")
            //нужно проверять на null иначе крэш при повторном нажатии на ту же иконку  - id = -1
            //val id = group.findViewById<Chip>(position)?.id
                Log.d(TAG, " id = $id")
                when(id){
                    R.id.chip1->{
                        val todayAsString =
                            dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, 0)}.time)
                        Log.d(TAG, "PictureOfTheDayFragment onActivityCreated todayAsString = $todayAsString")
                        image_view.clear()
                        viewModel.getData(todayAsString)
                    }
                    R.id.chip2-> {
                        val yesterdayAsString =
                            dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, -1)}.time)
                        Log.d(TAG, "PictureOfTheDayFragment onActivityCreated yesterdayAsString = $yesterdayAsString")
                        image_view.clear()
                        viewModel.getData(yesterdayAsString)
                    }
                    R.id.chip3-> {
                        val beforeYesterdayAsString =
                        dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, -2)}.time)
                        Log.d(TAG, "PictureOfTheDayFragment onActivityCreated beforeYesterdayAsString = $beforeYesterdayAsString")
                        image_view.clear()
                        viewModel.getData(beforeYesterdayAsString)
                    }
                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//
//        val todayAsString =
//            dateFormat.format( Calendar.getInstance().apply {add(Calendar.DATE, 0)}.time)
        //грузим видео с фазами луны в 2021
       val todayAsString = "2021-01-11"
        Log.d(TAG, "PictureOfTheDayFragment onActivityCreated todayAsString = $todayAsString")

        viewModel.getData(todayAsString)
            .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                with(bottom_app_bar){
                    navigationIcon = null
                    fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    replaceMenu(R.menu.menu_bottom_bar_other_screen)
                }
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
            } else {
                isMain = true
                with(bottom_app_bar){
                    navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                    fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    replaceMenu(R.menu.menu_bottom_bar)
                }
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, ChipsFragment())?.addToBackStack(null)?.commit()

            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                //прекращаем показ прогрессбара
                progressBarNasa.visibility = View.GONE

                if(data.serverResponseData.mediaType == "image"){
                    if (url.isNullOrEmpty()) {
                        //showError("Сообщение, что ссылка пустая")
                        toast("Link is empty")
                    } else {
                        image_view.visibility =View.VISIBLE
                        web_view.visibility =View.GONE

                        //Koil image download  (аналог Picasso и Glide, написанный на Kotlin)
                        image_view.load(url) {
                            lifecycle(this@PictureOfTheDayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                    }
                }else {
                    web_view.clearCache(true)
                    web_view.clearHistory()
                    web_view.settings.javaScriptEnabled = true  // небезопасно тащить скрипты
                    web_view.settings.javaScriptCanOpenWindowsAutomatically = true

                    image_view.visibility =View.GONE
                    web_view.visibility =View.VISIBLE
                    web_view.loadUrl(url)
                }
                //тестовый текст / в макете выставлены значения выезжания bottom sheet
                bottom_sheet_description_header.text = serverResponseData.title
                bottom_sheet_description.text = serverResponseData.explanation
            }

            is PictureOfTheDayData.Loading -> {
                //показываем прогресс-бар
                progressBarNasa.visibility = View.VISIBLE
            }

            is PictureOfTheDayData.Error -> {
                //прекращаем показ прогрессбара
                progressBarNasa.visibility = View.GONE
                toast(data.error.message)
                image_view.load(R.drawable.ic_load_error_vector)
                }
            }
        }
    }
