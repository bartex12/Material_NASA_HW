package geekbarains.material.ui.tabs.earth.entity.picture

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSourcePicture {

    @GET("/planetary/earth/assets")
    fun loadAssets(
        @Query("lon") lon: Float?,
        @Query("lat") lat: Float?,
        @Query("api_key") api_key: String?

    ): Single<Assets>
}