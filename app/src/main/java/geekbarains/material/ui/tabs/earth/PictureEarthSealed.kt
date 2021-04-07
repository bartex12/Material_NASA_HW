package geekbarains.material.ui.tabs.earth

sealed class PictureEarthSealed {
    data class Success(val listPicturesOfEarth:List<PictureOfEarth>): PictureEarthSealed()
    data class Error(val error:Throwable): PictureEarthSealed()
    data class Loading(val progress: Int?): PictureEarthSealed()

}