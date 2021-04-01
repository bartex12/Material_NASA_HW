package geekbarains.material.ui.tabs.earth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.ui.capitalpicture.CapitalPictureFragment
import geekbarains.material.ui.maps.MapsActivity
import geekbarains.material.ui.tabs.earth.entity.capital.CapitalOfState
import geekbarains.material.ui.tabs.earth.entity.capital.MapOfCapital
import geekbarains.material.ui.tabs.earth.entity.coord.CapitalCoords
import geekbarains.material.ui.tabs.earth.entity.coord.CoordSealed
import geekbarains.material.ui.tabs.earth.entity.picture.Assets
import geekbarains.material.ui.tabs.earth.entity.picture.PictureSealed
import geekbarains.material.util.snackBarLong
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class EarthFragment : Fragment(){

    companion object{
        const val TAG = "33333"
    }
    lateinit var viewModelEarth:EarthViewModel
    private var adapter: EarthRecyclerAdapter? = null
    private var picture_type:Int = 1

    var temp  = 0

    var capitalOfState = listOf<CapitalOfState>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth,container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //читаем сохранённный в настройках тип картинки
        picture_type = PreferenceManager.getDefaultSharedPreferences(requireActivity())
            .getString("ListEarth", "1")!!.toInt()

        viewModelEarth = ViewModelProvider(this).get(EarthViewModel::class.java)
        //так как используются вкладки, а список на второй вкладке - для его отображения
        //используем LiveData, которая сама выдаст данные, когда перейдём на вкладку со списком
        //если делать не через LiveData? а просто получать список, он не отображается
        viewModelEarth.loadData().observe(viewLifecycleOwner, Observer {list->
            Log.d(TAG, "EarthFragment onViewCreated вкладка со списком " )
            adapter?.listCapitals = list
            capitalOfState = list //сохраняем список во viewModel
        })

        initAdapter()

        //слушатель на изменение текста в поле поиска
        input_edit_text_earth.addTextChangedListener {et->
            if (et.toString().isNotBlank()) {
                val listSearched = mutableListOf<CapitalOfState>()
                for (state in capitalOfState) {
                    state.name?. let{ name->
                        if((name.toUpperCase(Locale.ROOT)
                                .startsWith(et.toString().toUpperCase(Locale.ROOT)))){
                            listSearched.add(state)
                        }

//                        if ((name.toUpperCase(Locale.ROOT))
//                                .contains(et.toString().toUpperCase(Locale.ROOT))){
//                            listSearched.add(state)
//                            }
                        }
                    }
                adapter?.listCapitals = listSearched
            }else{
                adapter?.listCapitals = capitalOfState
            }
        }
        //слушатель на иконку  в конце поля поиска
        input_layout_earth.setEndIconOnClickListener {
            input_edit_text_earth.setText("") // очищаем поле и возвращаем исходный список
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "EarthFragment onPause " )
    }

    private fun  initAdapter(){
    rv_earth.layoutManager =LinearLayoutManager(requireActivity())
        adapter =  EarthRecyclerAdapter(getOnClickListener())
        rv_earth.adapter = adapter
    }

    private fun getOnClickListener(): EarthRecyclerAdapter.OnitemClickListener =
        object : EarthRecyclerAdapter.OnitemClickListener{
            override fun onItemclick(capitalOfState: CapitalOfState) {
                Log.d(TAG, "EarthFragment getOnClickListener " +
                        "${capitalOfState.capital} ${capitalOfState.name}")

                viewModelEarth.getCoordSealed(capitalOfState)
                    .observe(viewLifecycleOwner, Observer {renderData(it)})
            }
        }

    private fun renderData(data:CoordSealed) {
        when(data){
            is CoordSealed.Success ->{
    //          Log.d(TAG, "EarthFragment renderData data =  $data" )
                renderLoadingStop()
                renderCoords(data.capitalCoords)
            }
            is CoordSealed.Error -> {
                renderLoadingStop()
                renderError(data.error)
            }
            is CoordSealed.Loading -> {
                renderLoadingStart()
            }
        }
    }

    private fun renderCoords(capitalCoords: CapitalCoords){
        temp++
        val lat = capitalCoords.coord?.lat //широта для столицы государства
        val lon = capitalCoords.coord?.lon //долгота для столицы государства

        Log.d(TAG, "EarthFragment renderCoords " +
                "Координаты для ${capitalCoords.name}  lon = $lon  lat = $lat  temp =$temp")

        when(picture_type){
            1->{//запускаем активити с картой
                MapsActivity.start(requireActivity(), lat, lon)
                }
            2 -> {//  получение картинки со спутника
                if(lon!=null && lat!=null){
                     viewModelEarth.getPictureSealed(lon, lat).observe(viewLifecycleOwner, Observer {
                      renderAssets(it)})
                 }
            }
        }
    }



    private fun renderAssets(data:PictureSealed) {
        when(data){
            is PictureSealed.Success ->{
                //Log.d(TAG, "EarthFragment renderData data =  $data" )
                renderLoadingStop()
                renderPicture(data.assets)
            }
            is PictureSealed.Error -> {
                renderLoadingStop()
                renderError(data.error)
            }
            is PictureSealed.Loading -> {
                renderLoadingStart()
            }
        }
    }

    private fun renderPicture(assets: Assets){
        assets.url?. let{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, CapitalPictureFragment.newInstance(it))
                .addToBackStack("capitalPicture")
                .commit()
        }
    }

    private fun renderLoadingStart(){
       progressBarEarth.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarEarth.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this@EarthFragment.requireView(),
            "Сегодня нет изображений со спунника \n " +
                    "Ошибка $error")
    }
}