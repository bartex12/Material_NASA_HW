package geekbarains.material.ui.tabs.earth

class CapitalRepo: ICapitalRepo {

    override fun loadData(): Map<String, String> {
     return  MapOfCapital.mapCapitals
    }
}