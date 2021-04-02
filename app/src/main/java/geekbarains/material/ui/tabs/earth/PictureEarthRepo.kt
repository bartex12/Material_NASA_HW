package geekbarains.material.ui.tabs.earth

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PictureEarthRepo(val api: IDataSourcePictureEarth):IPictureEarthRepo {

    override fun getPictureOfEarth(): Single<List<PictureOfEarth>> {
            return   api.getPictureOfEarth().subscribeOn(Schedulers.io())
    }
}