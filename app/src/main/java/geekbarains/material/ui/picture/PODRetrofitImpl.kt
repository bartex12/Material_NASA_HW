package geekbarains.material.ui.picture

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

class PODRetrofitImpl {

    private val baseUrl = "https://api.nasa.gov/"

    fun getRetrofitImpl(): PictureOfTheDayAPI {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson()))
            //.client(createOkHttpClient(PODInterceptor()))
            .build()
            .create(PictureOfTheDayAPI::class.java)
    }

    fun gson(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()



//    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(interceptor)
//        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//        return httpClient.build()
//    }
//
//    inner class PODInterceptor : Interceptor {
//
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//            return chain.proceed(chain.request())
//        }
//    }
}
