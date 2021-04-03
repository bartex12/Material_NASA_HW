package geekbarains.material.ui.tabs.map.states

interface ICapitalRepo {
    fun loadData(): List<CapitalOfState>
}