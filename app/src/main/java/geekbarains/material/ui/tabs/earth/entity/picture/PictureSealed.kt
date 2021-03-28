package geekbarains.material.ui.tabs.earth.entity.picture

import geekbarains.material.ui.tabs.earth.entity.coord.CapitalCoords
import geekbarains.material.ui.tabs.earth.entity.coord.CoordSealed

sealed class PictureSealed {
    data class Success(val assets: Assets): PictureSealed()
    data class Error(val error:Throwable): PictureSealed()
    data class Loading(val progress: Int?): PictureSealed()
}