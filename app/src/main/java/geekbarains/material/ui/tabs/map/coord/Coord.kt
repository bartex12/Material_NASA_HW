package geekbarains.material.ui.tabs.map.coord

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coord (
    @Expose  val lon:Float? = null,
    @Expose  val lat:Float? = null
): Parcelable