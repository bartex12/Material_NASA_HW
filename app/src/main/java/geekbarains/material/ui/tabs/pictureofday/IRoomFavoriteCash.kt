package geekbarains.material.ui.tabs.pictureofday

import geekbarains.material.room.Favorite
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IRoomFavoriteCash {
    fun addToFavorite(favorite: Favorite): Completable
    fun removeFavorite(favorite: Favorite): Completable

    fun loadFavorite(): Single<List<Favorite>>
    fun isFavorite(favorite: Favorite):Single<Boolean>

}