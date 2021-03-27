package geekbarains.material.ui.tabs.earth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import geekbarains.material.R
import geekbarains.material.ui.search.SearchFragment
import geekbarains.material.ui.settings.SettingsActivity
import geekbarains.material.ui.tabs.earth.entity.capital.CapitalOfState
import geekbarains.material.util.snackBar
import kotlinx.android.synthetic.main.fragment_earth.*

class EarthFragment : Fragment(){

    companion object{
        const val TAG = "33333"
    }
    lateinit var viewModelEarth:EarthViewModel
    private var adapter: EarthRecyclerAdapter? = null


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

        viewModelEarth.loadData().observe(viewLifecycleOwner, Observer { map->
          val list =   mutableListOf<CapitalOfState>()
            for (mapp in map){
                list.add(
                    CapitalOfState(
                        name = mapp.key,
                        capital = mapp.value
                    )
                )
            }
            adapter?.listCapitals = list
        })
        //инициализация нижнего меню фрагмента - слушатель на нажатие пункта меню
        initBottomNavigationView(view)

        initAdapter()
    }

    private fun  initAdapter(){
    rv_earth.layoutManager =LinearLayoutManager(requireActivity())
        adapter =  EarthRecyclerAdapter(getOnClickListener())
        rv_earth.adapter = adapter
    }

    private fun getOnClickListener(): EarthRecyclerAdapter.OnitemClickListener =
        object : EarthRecyclerAdapter.OnitemClickListener{
            override fun onItemclick(capitalOfState: CapitalOfState) {
                snackBar(this@EarthFragment.requireView(),
                    "Здесь будет взгляд на ${capitalOfState.capital}  со спутника ")
                viewModelEarth.loadPicture(capitalOfState)
                Log.d(TAG, "EarthFragment getOnClickListener capitalOfState.name = ${capitalOfState.name}" )
            }
        }

    private fun initBottomNavigationView(view:View) {

        bottom_navigation_earth.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val viewPager= view.rootView.findViewById<ViewPager>(R.id.view_pager)
                    viewPager.setCurrentItem(0)
                    true
                }
                R.id.bottom_view_search -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment())
                        .addToBackStack("search")
                        .commit()
                    true
                }
                R.id.app_bar_settings -> {
                    startActivity(Intent(requireActivity(), SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}