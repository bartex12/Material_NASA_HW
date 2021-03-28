package geekbarains.material.ui.tabs.earth.entity.picture

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSourcePicture {

    @GET("/planetary/earth/imagery")
    fun loadAssets(
        @Query("lon") lon: Float?,
        @Query("lat") lat: Float?,
        @Query("date") date: String?,
        @Query("dim") dim: Float?,
        @Query("api_key") api_key: String?

    ): Single<Assets>
}