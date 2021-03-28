package geekbarains.material.ui.tabs.earth.entity.coord

import io.reactivex.rxjava3.core.Single

interface ICoordsRepo {
    fun geCoordsOfCapital(
        capital: String?,keyApi: String?,units: String?, lang:String?): Single<CapitalCoords>
}