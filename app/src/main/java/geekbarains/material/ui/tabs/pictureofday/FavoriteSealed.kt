package geekbarains.material.ui.tabs.pictureofday

sealed class FavoriteSealed {
    data class Success(val isFavorite:Boolean): FavoriteSealed()
    data class Error(val error:Throwable): FavoriteSealed()
}