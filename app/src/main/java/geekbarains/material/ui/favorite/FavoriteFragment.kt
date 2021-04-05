package geekbarains.material.ui.favorite

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
import geekbarains.material.room.Favorite
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment: Fragment() {

    private var adapter: FavoriteRVAdapter? = null

    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object {
        const val TAG = "33333"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_favorite, null)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FavoriteFragment onViewCreated ")

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        favoriteViewModel.getFavorite().observe(viewLifecycleOwner, Observer<List<Favorite>>{ favorites->
            Log.d(TAG, "FavoriteFragment onViewCreated")
            favorites?. let{ renderData(it) }})

        initAdapter()

        //приводим меню тулбара в соответствии с onPrepareOptionsMenu в MainActivity
        setHasOptionsMenu(true)
        requireActivity().invalidateOptionsMenu()
    }

    private fun renderData(favorites: List<Favorite>) {
        if(favorites.isEmpty()){
            rv_favorite.visibility = View.GONE
            empty_view_favorite.visibility = View.VISIBLE
        }else{
            rv_favorite.visibility =  View.VISIBLE
            empty_view_favorite.visibility =View.GONE

            adapter?.listFavorites = favorites //передаём список в адаптер
        }
    }

    private fun initAdapter() {
        rv_favorite.layoutManager = LinearLayoutManager(requireActivity())

        adapter = FavoriteRVAdapter( getOnClickListener())
        rv_favorite.adapter = adapter
    }

    private fun getOnClickListener(): FavoriteRVAdapter.OnitemClickListener =
        object : FavoriteRVAdapter.OnitemClickListener{
            override fun onItemclick(favorite: Favorite) {

            }
        }
}