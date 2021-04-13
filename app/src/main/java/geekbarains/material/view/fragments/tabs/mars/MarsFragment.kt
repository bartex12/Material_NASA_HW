package geekbarains.material.view.fragments.tabs.mars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.model.earth.entity.PictureEarthSealed
import geekbarains.material.model.earth.entity.PictureOfEarth
import geekbarains.material.model.mars.entity.FotosOfMars
import geekbarains.material.model.mars.entity.MarsResponseData
import geekbarains.material.model.mars.entity.MarsSealed
import geekbarains.material.util.snackBarLong
import geekbarains.material.view.fragments.adapters.EarthRecyclerAdapter
import geekbarains.material.view.fragments.adapters.MarsRVAdapter
import geekbarains.material.view.fragments.tabs.earth.EarthFragment
import geekbarains.material.viewmodel.EarthViewModel
import geekbarains.material.viewmodel.MarsViewModel
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_mars.*

class MarsFragment: Fragment() {

    companion object{
        const val TAG = "33333"
    }

    private lateinit var viewModelMars: MarsViewModel
    private var adapter: MarsRVAdapter? = null
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelMars = ViewModelProvider(requireActivity()).get(MarsViewModel::class.java)

        viewModelMars.loadData("2015-6-3")

        viewModelMars.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        initAdapter()
    }

    private fun renderData(data: MarsSealed) {
        when(data){
            is MarsSealed.Success ->{
                renderLoadingStop()
                renderMarsFotos(data.marsResponseData)
            }
            is MarsSealed.Error -> {
                renderLoadingStop()
                renderError(data.error)
            }
            is MarsSealed.Loading -> {
                renderLoadingStart()
            }
        }
    }

    private fun renderMarsFotos(data: MarsResponseData){
        data.photos?. let{
            Log.d(TAG, "### MarsFragment renderMarsFotos  photos.size = ${it.size}")
            adapter?.data = it
        }
    }

    private fun renderLoadingStart(){
        progressBarMars.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarMars.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this.requireView(),
            "Сегодня нет изображений со спутника \n " +
                    "Ошибка $error")
    }

    private fun  initAdapter(){
        rv_mars.layoutManager = LinearLayoutManager(requireActivity())
        adapter = MarsRVAdapter(getOnClickListener())
        rv_mars.adapter = adapter
    }

    fun getOnClickListener(): MarsRVAdapter.OnItemClickListener=
        object : MarsRVAdapter.OnItemClickListener {
            override fun onItemClick(url: String) {
                Log.d(TAG, "### MarsFragment getOnClickListener $url")
            }
        }
}