package geekbarains.material.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import geekbarains.material.R
import kotlinx.android.synthetic.main.fragment_api.*

class ApiFragment:Fragment() {

    companion object{
        fun newInstance() = ApiFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_api,container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter =ViewPageAdapter(childFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }
}