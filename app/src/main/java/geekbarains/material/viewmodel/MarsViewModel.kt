package geekbarains.material.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.model.mars.api.ApiFactoryMars
import geekbarains.material.model.mars.entity.MarsSealed
import geekbarains.material.model.mars.repo.IMarsRepo
import geekbarains.material.model.mars.repo.MarsRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MarsViewModel:ViewModel() {

    companion object{
        const val TAG = "33333"
    }

    private val marsRepo: IMarsRepo = MarsRepo(ApiFactoryMars.API)
    private val marsSealed = MutableLiveData<MarsSealed>()

    fun getData(): LiveData<MarsSealed>{
       // loadData()
        return marsSealed
    }

   fun  loadData(date:String){
       Log.d(TAG, "### MarsViewModel loadData")
       val apiKey = "wX1Eamf7gnFJj4cgU1U1pl5LGNyxvuLhT7FQ9wPg"
       marsSealed.value = MarsSealed.Loading(null )
       marsRepo.getFotosOfMarsCuriosity(date, apiKey)
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               marsSealed.value = MarsSealed.Success(marsResponseData = it )
           },{
               marsSealed.value = MarsSealed.Error(error = it)
           })
   }
}