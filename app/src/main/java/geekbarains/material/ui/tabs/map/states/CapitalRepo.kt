package geekbarains.material.ui.tabs.map.states

class CapitalRepo:
    ICapitalRepo {

    override fun loadData(): List<CapitalOfState> {
        val list =   mutableListOf<CapitalOfState>()
        for (mapp in MapOfCapital.mapCapitals){
            list.add(
                CapitalOfState(
                    name = mapp.key,
                    capital = mapp.value
                )
            )
        }
     return  list
    }
}