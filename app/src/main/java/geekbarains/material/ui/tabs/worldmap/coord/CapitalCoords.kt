package geekbarains.material.ui.tabs.worldmap.coord

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CapitalCoords (
    @Expose val name:String? = null,
    @Expose val coord: Coord? = null
): Parcelable