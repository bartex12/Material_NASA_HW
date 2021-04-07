package geekbarains.material.ui.tabs.map.coord

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CoordsRepo(val api: IDataSourceCoords):
    ICoordsRepo {

    override fun geCoordsOfCapital(capital: String?,keyApi: String?,
        units: String?,lang: String?): Single<CapitalCoords> =

        api.loadCoordsOfCapitalRu(capital, keyApi, units, lang )
            .subscribeOn(Schedulers.io())
}