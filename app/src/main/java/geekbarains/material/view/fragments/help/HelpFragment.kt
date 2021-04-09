package geekbarains.material.view.fragments.help

import android.graphics.Typeface.BOLD
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import geekbarains.material.R
import geekbarains.material.viewmodel.HelpViewModel
import kotlinx.android.synthetic.main.fragment_help.*

class HelpFragment: Fragment(){

    companion object{
        const val TAG = "33333"
    }

    private lateinit var helpViewModel: HelpViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?)=
        View.inflate(context, R.layout.fragment_help, null)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helpViewModel = ViewModelProvider(this).get(HelpViewModel::class.java)

        val helpText = helpViewModel.getHelpText()

        val spanHelp:Spannable =
            SpannableString(helpText)

        spanHelp.setSpan(
            ForegroundColorSpan(requireActivity().getColor(R.color.colorPrimaryDarkPurple)),
            0,
            28,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            StyleSpan(BOLD),
            0,
            28,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            ForegroundColorSpan(requireActivity().getColor(R.color.colorPrimaryDarkPurple)),
            101,
            110,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            StyleSpan(BOLD),
            101,
            110,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            ForegroundColorSpan(requireActivity().getColor(R.color.colorPrimaryDarkPurple)),
            419,
            428,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            ForegroundColorSpan(requireActivity().getColor(R.color.colorPrimaryDarkPurple)),
            442,
            447,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            StyleSpan(BOLD),
            442,
            447,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            ForegroundColorSpan(requireActivity().getColor(R.color.colorPrimaryDarkPurple)),
            568,
            579,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            StyleSpan(BOLD),
            568,
            579,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            ForegroundColorSpan(requireActivity().getColor(R.color.colorPrimaryDarkPurple)),
            676,
            695,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanHelp.setSpan(
            ForegroundColorSpan(requireActivity().getColor(R.color.colorPrimaryDarkPurple)),
            spanHelp.length-9,
            spanHelp.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_help.text = spanHelp
    }
}