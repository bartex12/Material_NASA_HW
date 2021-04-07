package geekbarains.material.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import geekbarains.material.R
import geekbarains.material.room.Favorite
import geekbarains.material.ui.animation.AnimationActivity
import geekbarains.material.ui.search.SearchFragment
import geekbarains.material.ui.settings.SettingsActivity
import geekbarains.material.ui.tabs.ApiFragment
import geekbarains.material.ui.tabs.pictureofday.PictureOfTheDayFragment
import geekbarains.material.ui.tabs.pictureofday.PictureOfTheDayViewModel
import kotlinx.android.synthetic.main.favorite_foto.*
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

        //разрешаем показ меню во фрагменте
        setHasOptionsMenu(true)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        favoriteViewModel.getFavorite().observe(viewLifecycleOwner, Observer<List<Favorite>>{ favorites->
            Log.d(TAG, "FavoriteFragment onViewCreated")
            favorites?. let{ renderData(it) }})

        initAdapter()

        //приводим меню тулбара в соответствии с onPrepareOptionsMenu в MainActivity
        setHasOptionsMenu(true)
        requireActivity().invalidateOptionsMenu()

        //если во всех холдерах одинаковый дивайдер, можно так
        //rv_favorite.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
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
                Log.d(TAG, "FavoriteFragment setOnClickListener ")

                    val intent = Intent(requireActivity(), AnimationActivity::class.java)
                    intent.putExtra(AnimationActivity.URL_ANIMATION, favorite.url)
                    intent.putExtra(AnimationActivity.MEDIA_TYPE_ANIMATION, favorite.type)
                    startActivity(intent)
            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.app_bar_favorites).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {


            R.id.app_bar_settings ->
                startActivity(Intent(requireActivity(), SettingsActivity::class.java))

            R.id.app_bar_search_wiki ->
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SearchFragment())
                    .addToBackStack("search")
                    .commit()
        }
        return super.onOptionsItemSelected(item)
    }
}