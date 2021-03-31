package geekbarains.material.ui.tabs.earth.entity.picture

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PictureRepo(val api:IDataSourcePicture):IPictureRepo {

    override fun getPictureOfCapital(
        lon: Float,lat: Float,/*date: String,*/ api_key: String )
            : Single<Assets> =

        api.loadAssets(lon, lat, /*date, */ api_key)
            .subscribeOn(Schedulers.io())
}