package geekbarains.material.model.pref

import android.util.Log
import androidx.preference.PreferenceManager
import geekbarains.material.App
import geekbarains.material.R
import geekbarains.material.view.constants.Constants
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PrefRepo(val app: App) :IPrefRepo{

    companion object {
        const val TAG = "33333"
    }

    override fun saveDatePickerDate(date: String) {
        PreferenceManager.getDefaultSharedPreferences(app)
            .edit()
            .putString(Constants.DATE_DATA_PICKER, date)
            .apply()
        Log.d(TAG,"PreferenceHelper saveDatePickerDate date = $date"
        )
    }

    override fun getDatePickerDate():String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now  = dateFormat.format(GregorianCalendar().time)
        val date = PreferenceManager.getDefaultSharedPreferences(app)
            .getString(Constants.DATE_DATA_PICKER, now)
        Log.d(TAG, "PreferenceHelper getDatePickerDate date = $date")
        return date!!
    }
}