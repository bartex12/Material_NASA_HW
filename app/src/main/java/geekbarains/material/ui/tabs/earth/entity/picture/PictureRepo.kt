package geekbarains.material.ui.tabs.earth.entity.picture

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PictureRepo(val api:IDataSourcePicture):IPictureRepo {

    override fun getPictureOfCapital(
        lon: Float,lat: Float, api_key: String )
            : Single<Assets> =

        api.loadAssets(lon, lat,  api_key)
            .subscribeOn(Schedulers.io())
}