package geekbarains.material.ui.tabs.worldmap.coord

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSourceCoords {
    @GET("data/2.5/weather")
    fun loadCoordsOfCapitalRu(
        @Query("q") capital: String?,
        @Query("appid") keyApi: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?
    ): Single<CapitalCoords>
}