package geekbarains.material.ui.tabs.earth.entity.coord

sealed class CoordSealed {
    data class Success(val capitalCoords:CapitalCoords):CoordSealed()
    data class Error(val error:Throwable):CoordSealed()
    data class Loading(val progress: Int?):CoordSealed()
}