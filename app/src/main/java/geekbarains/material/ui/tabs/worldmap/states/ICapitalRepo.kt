package geekbarains.material.ui.tabs.worldmap.states

interface ICapitalRepo {
    fun loadData(): List<CapitalOfState>
}