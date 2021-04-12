package geekbarains.material.model.pref

interface IPrefRepo {
    fun saveDatePickerDate(date:String)
    fun getDatePickerDate(): String
}