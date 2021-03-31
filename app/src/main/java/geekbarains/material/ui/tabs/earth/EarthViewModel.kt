package geekbarains.material.ui.tabs.earth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.ui.tabs.earth.entity.capital.CapitalOfState
import geekbarains.material.ui.tabs.earth.entity.capital.CapitalRepo
import geekbarains.material.ui.tabs.earth.entity.capital.ICapitalRepo
import geekbarains.material.ui.tabs.earth.entity.coord.*
import geekbarains.material.ui.tabs.earth.entity.picture.ApiFactoryPicture
import geekbarains.material.ui.tabs.earth.entity.picture.IPictureRepo
import geekbarains.material.ui.tabs.earth.entity.picture.PictureRepo
import geekbarains.material.ui.tabs.earth.entity.picture.PictureSealed
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class EarthViewModel: ViewModel() {
    companion object{
        const val TAG = "33333"
    }
    //для получения списка столиц государств
    private val map = MutableLiveData<List<CapitalOfState>>()

    //для получения картинки столицы
    private val pictureSealed =  MutableLiveData<PictureSealed>()
    private val pictureRepo: IPictureRepo = PictureRepo(ApiFactoryPicture.API)
    //для получения координат столиц
    private val coordsSealed =  MutableLiveData<CoordSealed>()
    //зависимость для получения координат столиц
    private var coordsRepo: ICoordsRepo = CoordsRepo(ApiFactory.API)

   private val  statesRepo: ICapitalRepo =
        CapitalRepo()

    //метод для получения списка столиц
    fun loadData():LiveData<List<CapitalOfState>>{
        map.value = statesRepo.loadData()
       return map
    }

    //метод для получения координат столиц
    fun getCoordSealed(capitalOfState: CapitalOfState):LiveData<CoordSealed>{
        loadCoordSealed(capitalOfState)
        return coordsSealed
    }

    //метод для  данных по координатам с прогресом и ошибкой
    private fun  loadCoordSealed(capitalOfState: CapitalOfState){
        coordsSealed.value = CoordSealed.Loading(null)//далее запускаем прогресс бар
        coordsRepo.geCoordsOfCapital(capitalOfState.capital,
            "80bb32e4a0db84762bb04ab2bd724646", "metric", "RU" )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "EarthViewModel loadCoordSealed capital = ${it.name}" )
                coordsSealed.value = CoordSealed.Success(capitalCoords = it)
            },{error->
                coordsSealed.value = CoordSealed.Error(error = error)
            })
    }

    //метод для получения картинки столицы с сайта NASA
    fun getPictureSealed(lon:Float, lat:Float):LiveData<PictureSealed>{
        loadPictureSealed(lon, lat)
        return pictureSealed
    }

    //метод для  данных по координатам с прогресом и ошибкой
    private fun  loadPictureSealed(lon:Float, lat:Float){
        Log.d(TAG, "EarthViewModel loadPictureSealed lon = $lon lat = $lat" )
        pictureSealed.value = PictureSealed.Loading(null)//далее запускаем прогресс бар
        pictureRepo.getPictureOfCapital(lon, lat,/*"2021-01-01",*/
            "wX1Eamf7gnFJj4cgU1U1pl5LGNyxvuLhT7FQ9wPg" )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "EarthViewModel loadPictureSealed url = ${it.url}" )
                pictureSealed.value = PictureSealed.Success(assets = it)
            },{error->
                Log.d(TAG, "EarthViewModel loadPictureSealed error = $error" )
                pictureSealed.value = PictureSealed.Error(error = error)
            })
    }

}