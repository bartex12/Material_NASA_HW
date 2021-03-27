package geekbarains.material.util

import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.TOP,0,0)
        show()
    }
}

fun Fragment.snackBar(view: View, string: CharSequence) {
    Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show()
    }
