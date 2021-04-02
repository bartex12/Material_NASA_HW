package geekbarains.material.ui.tabs.worldmap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.ui.maps.MapsActivity
import geekbarains.material.ui.tabs.worldmap.states.CapitalOfState
import geekbarains.material.ui.tabs.worldmap.coord.CapitalCoords
import geekbarains.material.ui.tabs.worldmap.coord.CoordSealed
import geekbarains.material.util.snackBarLong
import kotlinx.android.synthetic.main.fragment_earth.*
import java.util.*

class WorldMapFragment : Fragment(){

    companion object{
        const val TAG = "33333"
    }
    lateinit var viewModelEarth:WorldMapViewModel
    private var adapter: WorldMapRecyclerAdapter? = null

    private var temp  = 0

    private var capitalOfState = listOf<CapitalOfState>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth,container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelEarth = ViewModelProvider(this).get(WorldMapViewModel::class.java)
        //так как используются вкладки, а список на второй вкладке - для его отображения
        //используем LiveData, которая сама выдаст данные, когда перейдём на вкладку со списком
        //если делать не через LiveData? а просто получать список, он не отображается
        viewModelEarth.loadData().observe(viewLifecycleOwner, Observer {list->
            Log.d(TAG, "WorldMapFragment onViewCreated вкладка со списком " )
            adapter?.listCapitals = list
            capitalOfState = list //сохраняем список
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
        Log.d(TAG, "WorldMapFragment onPause " )
    }

    private fun  initAdapter(){
    rv_earth.layoutManager =LinearLayoutManager(requireActivity())
        adapter =  WorldMapRecyclerAdapter(getOnClickListener())
        rv_earth.adapter = adapter
    }

    private fun getOnClickListener(): WorldMapRecyclerAdapter.OnitemClickListener =
        object : WorldMapRecyclerAdapter.OnitemClickListener{
            override fun onItemclick(capitalOfState: CapitalOfState) {
                Log.d(TAG, "WorldMapFragment getOnClickListener " +
                        "${capitalOfState.capital} ${capitalOfState.name}")

                viewModelEarth.getCoordSealed(capitalOfState)
                    .observe(viewLifecycleOwner, Observer {renderData(it)})
            }
        }

    private fun renderData(data: CoordSealed) {
        when(data){
            is CoordSealed.Success ->{
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

        Log.d(TAG, "WorldMapFragment renderCoords " +
                "Координаты для ${capitalCoords.name}  lon = $lon  lat = $lat  temp =$temp")

        MapsActivity.start(requireActivity(), lat, lon)
    }

    private fun renderLoadingStart(){
       progressBarEarth.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarEarth.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this@WorldMapFragment.requireView(),"Ошибка $error")
    }
}