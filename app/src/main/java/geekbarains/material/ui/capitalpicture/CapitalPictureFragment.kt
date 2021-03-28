package geekbarains.material.ui.capitalpicture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import geekbarains.material.R
import kotlinx.android.synthetic.main.fragment_picture.*

class CapitalPictureFragment:Fragment() {

    companion object{
        const val TAG = "33333"
        const val URL_CAPITAL = "URL_CAPITAL"

        fun newInstance(url:String):CapitalPictureFragment{
            val fragment = CapitalPictureFragment()
            fragment.arguments = Bundle().apply { putString(URL_CAPITAL, url) }
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picture,container, false )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d( TAG, "CapitalPictureFragment onViewCreated backStackEntryCount =" +
                "${requireActivity().supportFragmentManager.backStackEntryCount}" )

        val url:String = arguments?.get(URL_CAPITAL) as String

        imageViewEarth.load(url) {
            lifecycle(this@CapitalPictureFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }

    }
}