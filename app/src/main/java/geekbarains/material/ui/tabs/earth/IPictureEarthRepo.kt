package geekbarains.material.ui.tabs.earth

import io.reactivex.rxjava3.core.Single

interface IPictureEarthRepo {
    fun getPictureOfEarth(): Single<List<PictureOfEarth>>
}