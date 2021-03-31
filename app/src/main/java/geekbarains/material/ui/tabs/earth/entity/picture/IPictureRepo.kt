package geekbarains.material.ui.tabs.earth.entity.picture

import geekbarains.material.ui.tabs.earth.entity.coord.CapitalCoords
import io.reactivex.rxjava3.core.Single

interface IPictureRepo {
    fun getPictureOfCapital(lon:Float, lat:Float,/* date:String,*/  api_key:String)
            : Single<Assets>
}