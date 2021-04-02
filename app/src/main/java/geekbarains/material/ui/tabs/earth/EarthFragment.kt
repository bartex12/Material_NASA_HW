package geekbarains.material.ui.tabs.earth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.util.snackBarLong
import kotlinx.android.synthetic.main.fragment_mars.*

class EarthFragment: Fragment() {

    companion object{
        const val TAG = "33333"
    }

    private lateinit var viewModelMars: EarthViewModel
    private var adapter: EarthRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars,container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelMars =ViewModelProvider(requireActivity()).get(EarthViewModel::class.java)

        initAdapter()

        viewModelMars.getPictures().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
    }

    private fun renderData(data: PictureEarthSealed) {
        when(data){
            is PictureEarthSealed.Success ->{
                renderLoadingStop()
                renderPicture(data.listPicturesOfEarth)
            }
            is PictureEarthSealed.Error -> {
                renderLoadingStop()
                renderError(data.error)
            }
            is PictureEarthSealed.Loading -> {
                renderLoadingStart()
            }
        }
    }

    private fun renderPicture(data: List<PictureOfEarth>){
        adapter?.listOfPictures = data
//       //val  url = "https://epic.gsfc.nasa.gov/archive/natural/2021/03/31/png/epic_1b_20210331001751.png "
//        val date = data[0].date?.take(10)
//        val dateWithDiv = date?.replace("-", "/")
//        val url ="https://epic.gsfc.nasa.gov/archive/natural/$dateWithDiv/" +
//                "jpg/${data[0].image}.jpg "
//        Log.d(PictureOfTheDayFragment.TAG, "### EarthFragment renderPicture url = $url")
//
//        imageViewTest.load(url){
//            lifecycle(this@EarthFragment)
//            placeholder(R.drawable.ic_no_photo_vector)
//            error(R.drawable.ic_load_error_vector)
//        }

//        Picasso.with(context)
//            .load(url)
//            .placeholder(R.drawable.ic_no_photo_vector)
//            .error(R.drawable.ic_load_error_vector)
//            .into(imageViewTest)

    }

    private fun renderLoadingStart(){
        progressBarMars.visibility = View.VISIBLE
    }

    private fun renderLoadingStop(){
        progressBarMars.visibility = View.GONE
    }

    private fun renderError(error: Throwable) {
        snackBarLong(this.requireView(),
            "Сегодня нет изображений со спунника \n " +
                    "Ошибка $error")
    }

    private fun  initAdapter(){
        rv_mars.layoutManager = LinearLayoutManager(requireActivity())
        adapter =  EarthRecyclerAdapter(getOnClickListener())
        rv_mars.adapter = adapter
    }

    private fun getOnClickListener(): EarthRecyclerAdapter.OnItemClickListener =
        object : EarthRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(url: String) {
                Log.d(TAG, "EarthFragment getOnClickListener $url")
                //todo увеличить картинку
            }
        }

}