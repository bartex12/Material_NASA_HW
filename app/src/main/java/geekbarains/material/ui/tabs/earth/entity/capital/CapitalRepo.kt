package geekbarains.material.ui.tabs.earth.entity.capital

import geekbarains.material.ui.tabs.earth.entity.capital.ICapitalRepo
import geekbarains.material.ui.tabs.earth.entity.capital.MapOfCapital

class CapitalRepo:
    ICapitalRepo {

    override fun loadData(): Map<String, String> {
     return  MapOfCapital.mapCapitals
    }
}