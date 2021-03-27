package geekbarains.material.ui.tabs.earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.ui.tabs.earth.entity.capital.CapitalOfState
import geekbarains.material.ui.tabs.earth.entity.capital.CapitalRepo
import geekbarains.material.ui.tabs.earth.entity.capital.ICapitalRepo

class EarthViewModel: ViewModel() {
    val map = MutableLiveData<Map<String, String>>()
    val  statesRepo: ICapitalRepo =
        CapitalRepo()

    fun loadData():LiveData<Map<String, String>>{
        map.value = statesRepo.loadData()
       return map
    }

    fun loadPicture(capitalOfState: CapitalOfState){
        capitalOfState.name
    }
}