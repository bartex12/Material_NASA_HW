package geekbarains.material.ui.tabs.earth.entity.picture

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Assets (
    @Expose val date:String? = null,
    @Expose val url:String? = null
): Parcelable