package geekbarains.material.ui.picture

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>
    //fun getPictureOfTheDay(@Query("api_key") apiKey: String): Single<List<PODServerResponseData>>

}
