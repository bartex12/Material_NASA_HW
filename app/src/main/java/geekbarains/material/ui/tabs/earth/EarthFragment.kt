package geekbarains.material.ui.tabs.earth

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import coil.api.load
import geekbarains.material.R
import geekbarains.material.ui.capitalpicture.CapitalPictureFragment
import geekbarains.material.ui.maps.MapsActivity
import geekbarains.material.ui.search.SearchFragment
import geekbarains.material.ui.settings.SettingsActivity
import geekbarains.material.ui.tabs.earth.entity.capital.CapitalOfState
import geekbarains.material.ui.tabs.earth.entity.coord.CapitalCoords
import geekbarains.material.ui.tabs.earth.entity.coord.CoordSealed
import geekbarains.material.ui.tabs.earth.entity.picture.Assets
import geekbarains.material.ui.tabs.earth.entity.picture.PictureSealed
import geekbarains.material.util.snackBarLong
import geekbarains.material.util.snackBarShort
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_main.*

class EarthFragment : Fragment(){

    companion object{
        const val TAG = "33333"
    }
    lateinit var viewModelEarth:EarthViewModel
    private var adapter: EarthRecyclerAdapter? = null

    var temp  = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth,container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelEarth = ViewModelProvider(this).get(EarthViewModel::class.java)

        //так как используются вкладки, а список на второй вкладке - для его отображения
        //используем LiveData, которая сама выдаст данные, когда перейдём на вкладку со списком
        //если делать не через LiveData? а просто получать список, он не отображается
        viewModelEarth.loadData().observe(viewLifecycleOwner, Observer {list->
            Log.d(TAG, "EarthFragment onViewCreated вкладка со списком " )
            adapter?.listCapitals = list
        })

        initAdapter()
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

        //запускаем активити с картой
        MapsActivity.start(requireActivity(), lat, lon)

            //  получение картинки со спутника
//        if(lon!=null && lat!=null){
//            viewModelEarth.getPictureSealed(lon, lat).observe(viewLifecycleOwner, Observer {
//                renderAssets(it)
//                //startActivity(Intent(requireActivity(), MapsActivity::class.java))
//            })
//        }
//        snackBarShort(this@EarthFragment.requireView(),
//            "Координаты для ${capitalCoords.name} \n lon = $lon  lat = $lat")

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


//        assets.url?. let{
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.container, CapitalPictureFragment.newInstance(it))
//                .addToBackStack("capitalPicture")
//                .commit()
//        }
    }

    private fun renderLoadingStart(){
       progressBarEarth.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarEarth.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this@EarthFragment.requireView(),
            "Ошибка $error")
      //  tv_capital_description.text = error.message
       // iv_icon.setImageDrawable( ContextCompat.getDrawable(requireContext(),R.drawable.whatcanido))
    }
}