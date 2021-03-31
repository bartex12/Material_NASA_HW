package geekbarains.material.ui.tabs.earth.entity.picture

import com.google.gson.GsonBuilder
import geekbarains.material.ui.tabs.earth.entity.coord.IDataSourceCoords
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactoryPicture {

    val API:IDataSourcePicture

    init {
        API =getPictureService()
    }

   private fun getPictureService():IDataSourcePicture{
       //Создаем  объект,  при  помощи  которого  будем  выполнять  запросы
       return Retrofit.Builder()
           //Базовая  часть  адреса
           .baseUrl("https://api.nasa.gov/")
           //прикручиваем RxJava
           .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
           //Конвертер,  необходимый  для  преобразования  JSON  в   объекты
           .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
           .build()
           .create(IDataSourcePicture::class.java)
   }
}