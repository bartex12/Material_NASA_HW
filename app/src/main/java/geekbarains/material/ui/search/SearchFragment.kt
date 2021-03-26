package geekbarains.material.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import geekbarains.material.R
import geekbarains.material.ui.settings.SettingsActivity
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(){

    companion object{
        const val TAG = "33333"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search,container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //разрешаем показ меню во фрагменте
        setHasOptionsMenu(true)

        initChipGroup()

        input_layout_search.setEndIconOnClickListener {
            if (input_edit_text_search.text.toString().isNotBlank()){
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://ru.wikipedia.org/wiki/${input_edit_text_search.text.toString()}")
                })
            }else{
                toast(resources.getString(R.string. input_any_text))
            }
        }
    }

    private fun initChipGroup() {
        chipGroupSearch.setOnCheckedChangeListener { group, id ->
            Log.d(TAG, "SearchFragment initChipGroup ")

            val chip: Chip = group.findViewById(id)
            input_edit_text_search.setText(chip.text.toString())

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.app_bar_settings ->
                startActivity(Intent(requireActivity(), SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}