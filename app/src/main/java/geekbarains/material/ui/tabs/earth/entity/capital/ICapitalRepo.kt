package geekbarains.material.ui.tabs.earth.entity.capital

interface ICapitalRepo {
    fun loadData(): List<CapitalOfState>
}