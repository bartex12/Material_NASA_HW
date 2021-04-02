package geekbarains.material.ui.tabs.earth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import geekbarains.material.R
import geekbarains.material.util.snackBarLong
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_mars.*
import kotlinx.android.synthetic.main.item_mars.*

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
                Log.d(TAG, "### EarthFragment getOnClickListener $url")

                val intent = Intent(requireActivity(), AnimationActivity::class.java)
                intent.putExtra(AnimationActivity.URL_ANIMATION, url)
                startActivity(intent)
            }
        }

}