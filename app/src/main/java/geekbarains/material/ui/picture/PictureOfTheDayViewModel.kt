package geekbarains.material.ui.picture

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.BuildConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {

    fun getData(date:String): LiveData<PictureOfTheDayData> {
        sendServerRequest(date)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(date:String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        //val apiKey: String = BuildConfig.NASA_API_KEY
        val apiKey: String = "wX1Eamf7gnFJj4cgU1U1pl5LGNyxvuLhT7FQ9wPg"

            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    liveDataForViewToObserve.value =
                        PictureOfTheDayData.Success(serverResponseData = it)
                },{error ->
                    if (error.message.isNullOrEmpty()){
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Error(Throwable("Unidentified error"))
                    }else{
                        liveDataForViewToObserve.value =PictureOfTheDayData.Error(error = error)
                    }
                })
    }
}
