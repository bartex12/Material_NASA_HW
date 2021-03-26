package geekbarains.material.ui.picture

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import coil.api.clear
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import geekbarains.material.R
import geekbarains.material.ui.search.SearchFragment
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() , BottomNavigationDrawerFragment.OnItemClickListener {

    companion object {
        const val TAG = "33333"
        private var isExpanded = false
    }

    val dateFormat: DateFormat =SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onItemClick(date: String) {
        viewModel. sendServerRequest(date)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "PictureOfTheDayFragment onViewCreated ")

//        //разрешаем показ меню во фрагменте
           setHasOptionsMenu(true)

        //находим корневой лейаут и подключаем BottomSheet
        val bottomSheet: ConstraintLayout = initBottomSheet(view)

        //инициализация нижнего меню фрагмента - слушатель на нажатие пункта меню
        initBottomNavigationView(view, bottomSheet)

        //инициализация группы чипсов фрагмента
        initChipGroup()
    }

        //находим корневой лейаут и подключаем BottomSheet
    private fun initBottomSheet(view: View): ConstraintLayout {
        val bottomSheet: ConstraintLayout = view.findViewById(R.id.bottom_sheet_container)
        bottomSheet.visibility = View.INVISIBLE
        setBottomSheetBehavior(bottomSheet)
        return bottomSheet
    }

        private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        //скрываем bottom_app_bar при движении bottomSheet и показываем при сворачивании
        bottomSheetBehavior.addBottomSheetCallback( object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING ){
                    bottom_navigation_view.visibility =View.GONE
                }else if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    bottom_navigation_view.visibility = View.VISIBLE
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

        private fun initBottomNavigationView(view:View, bottomSheet: ConstraintLayout) {

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val viewPager= view.rootView.findViewById<ViewPager>(R.id.view_pager)
                    viewPager.setCurrentItem(0)
                    true
                }

                R.id.bottom_view_description -> {
                    isExpanded = !isExpanded
                    if(isExpanded){
                        bottomSheet.visibility = View.VISIBLE
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    }else{
                        bottomSheet.visibility = View.GONE
                    }
                    true
                }

//                R.id.bottom_view_search -> {
//                    val viewPager= view.rootView.findViewById<ViewPager>(R.id.view_pager)
//                    viewPager.setCurrentItem(4)
//                    true
//                }
                R.id.bottom_view_search -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container,SearchFragment())
                        .addToBackStack("search")
                        .commit()
                    true
                }

                R.id.bottom_view_more -> {
                        val dialog = BottomNavigationDrawerFragment()
                        dialog.setOnItemClickListener(this)
                        dialog.show(childFragmentManager, "tag_dialog_more")

                    true
                }
                else -> false
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //грузим видео с фазами луны в 2021
        val todayAsString = "2021-01-11"
        Log.d(TAG, "PictureOfTheDayFragment onActivityCreated todayAsString = $todayAsString")

        if (savedInstanceState == null){
            Log.d(TAG, "savedInstanceState == null")
            viewModel. sendServerRequest(todayAsString)
        }

        viewModel.getData()
            .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "PictureOfTheDayFragment onPause ")
    }


    private fun initChipGroup() {
        chipGroupMain.setOnCheckedChangeListener { group, id ->
            Log.d(TAG, "PictureOfTheDayFragment onViewCreated setOnCheckedChangeListener")
            //нужно проверять на null иначе крэш при повторном нажатии на ту же иконку  - id = -1
            //val id = group.findViewById<Chip>(position)?.id
            when (id) {
                R.id.chip1 -> {
                    val todayAsString =
                        dateFormat.format(
                            Calendar.getInstance().apply { add(Calendar.DATE, 0) }.time
                        )
                    Log.d(
                        TAG,
                        "PictureOfTheDayFragment onActivityCreated todayAsString = $todayAsString"
                    )
                    image_view.clear()
                    viewModel.sendServerRequest(todayAsString)
                }
                R.id.chip2 -> {
                    val yesterdayAsString =
                        dateFormat.format(
                            Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time
                        )
                    Log.d(
                        TAG,
                        "PictureOfTheDayFragment onActivityCreated yesterdayAsString = $yesterdayAsString"
                    )
                    image_view.clear()
                    viewModel.sendServerRequest(yesterdayAsString)
                }
                R.id.chip3 -> {
                    val beforeYesterdayAsString =
                        dateFormat.format(
                            Calendar.getInstance().apply { add(Calendar.DATE, -2) }.time
                        )
                    Log.d(
                        TAG,
                        "PictureOfTheDayFragment onActivityCreated beforeYesterdayAsString = $beforeYesterdayAsString"
                    )
                    image_view.clear()
                    viewModel.sendServerRequest(beforeYesterdayAsString)
                }
            }
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        Log.d(TAG, "*** PictureOfTheDayFragment renderData ")
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                //прекращаем показ прогрессбара
                progressBarNasa.visibility = View.GONE

                if(data.serverResponseData.mediaType == "image"){
                    Log.d(TAG, "*** PictureOfTheDayFragment renderData  mediaType = image")
                    if (url.isNullOrEmpty()) {
                        //showError("Сообщение, что ссылка пустая")
                        toast("Link is empty")
                    } else {
                        image_view.visibility =View.VISIBLE
                        web_view.visibility =View.GONE
                        //грузим несуществующий файл
                        //web_view.loadUrl("file:///android_asset/nonexistent.html")
                        web_view.loadUrl(  "about:blank") //или так

                        //Koil image download  (аналог Picasso и Glide, написанный на Kotlin)
                        image_view.load(url) {
                            lifecycle(this@PictureOfTheDayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                    }
                }else {
                    Log.d(TAG, "*** PictureOfTheDayFragment renderData  mediaType = video")
                    web_view.clearCache(true)
                    web_view.clearHistory()
                    web_view.settings.javaScriptEnabled = true  // небезопасно тащить скрипты
                    web_view.settings.javaScriptCanOpenWindowsAutomatically = true

                    image_view.visibility =View.GONE
                    web_view.visibility =View.VISIBLE
                    web_view.loadUrl(url)
                }
                //тестовый текст / в макете выставлены значения выезжания bottom sheet
                bottom_sheet_description_header?.text = serverResponseData.title
                bottom_sheet_description?.text = serverResponseData.explanation
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
