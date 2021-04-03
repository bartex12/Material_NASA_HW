package geekbarains.material.ui.tabs.map.coord

sealed class CoordSealed {
    data class Success(val capitalCoords: CapitalCoords):
        CoordSealed()
    data class Error(val error:Throwable):
        CoordSealed()
    data class Loading(val progress: Int?):
        CoordSealed()
}