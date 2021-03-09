package geekbarains.material.ui.picture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {

    companion object {
        const val TAG = "33333"
    }

    fun getData(date:String): LiveData<PictureOfTheDayData> {
        sendServerRequest(date)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(date:String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        //val apiKey: String = BuildConfig.NASA_API_KEY
        val apiKey: String = "wX1Eamf7gnFJj4cgU1U1pl5LGNyxvuLhT7FQ9wPg"

        Log.d(TAG, "PictureOfTheDayViewModel sendServerRequest ")

            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    Log.d(TAG, "PictureOfTheDayViewModel sendServerRequest Success")
                    liveDataForViewToObserve.value =
                        PictureOfTheDayData.Success(serverResponseData = it)
                },{error ->
                    Log.d(TAG, "PictureOfTheDayViewModel sendServerRequest Error")
                    if (error.message.isNullOrEmpty()){
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Error(Throwable("Unidentified error"))
                    }else{
                        liveDataForViewToObserve.value =PictureOfTheDayData.Error(error = error)
                    }
                })
    }
}
