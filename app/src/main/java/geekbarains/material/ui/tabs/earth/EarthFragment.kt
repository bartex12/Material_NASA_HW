package geekbarains.material.ui.tabs.earth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import geekbarains.material.R
import geekbarains.material.ui.MainActivity
import geekbarains.material.ui.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_earth.*

class EarthFragment : Fragment(){

    companion object{
        const val TAG = "33333"
    }

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

        val viewModel = ViewModelProvider(this).get(EarthViewModel::class.java)

        viewModel.loadData().observe(viewLifecycleOwner, Observer { map->
          val list =   mutableListOf<CapitalOfState>()
            for (mapp in map){
                list.add(CapitalOfState(name = mapp.key, capital = mapp.value))
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
            //todo
                Log.d(TAG, "EarthFragment getOnClickListener " )
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

//                R.id.bottom_view_more -> {
//                    val dialog = BottomNavigationDrawerFragment()
//                    dialog.setOnItemClickListener(this)
//                    dialog.show(childFragmentManager, "tag_dialog_more")
//
//                    true
//                }
                else -> false
            }
        }
    }
}