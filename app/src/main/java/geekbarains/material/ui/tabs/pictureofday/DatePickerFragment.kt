package geekbarains.material.ui.tabs.pictureofday

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import geekbarains.material.R
import geekbarains.material.util.toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(val listener:OnItemClickListener) : DialogFragment(),  DatePickerDialog.OnDateSetListener {

    companion object{
        const val TAG = "33333"
    }

    interface OnItemClickListener{
        fun onItemClick(date:String)
    }

    //открываем DatePicker с текущей датой
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "DatePickerFragment onCreateDialog ")
        val calendar = Calendar.getInstance()
        val yy = calendar[Calendar.YEAR]
        val mm = calendar[Calendar.MONTH]
        val dd = calendar[Calendar.DAY_OF_MONTH]

        val dateFormat: DateFormat = SimpleDateFormat("dd–MM–yyyy", Locale.getDefault())
        val first = getString(R.string.first_foto_apod)
        val now  = dateFormat.format(GregorianCalendar(yy, mm, dd).time)

        //обязательно в конструктор передавать слушатель, иначе onDateSet не сработает
        val datePicker = DatePickerDialog(requireContext(), this, yy, mm, dd )
        datePicker.setTitle("от  $first \nдо  $now")
        return datePicker
    }
    //выбираем дату и после нажатия ok попадаем в этот метод
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val cal = GregorianCalendar(year, month, dayOfMonth).time
        if(cal.before(GregorianCalendar(1995, 6, 16).time)){
            Log.d(TAG, "DatePickerFragment onDateSet before")
            toast("нужно после 16-06-1995")
        }else if(cal.after(GregorianCalendar().time)){
            Log.d(TAG, "DatePickerFragment onDateSet after")
            toast("нужно до сегодня ")
        }else{
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date:String = dateFormat.format(GregorianCalendar(year, month, dayOfMonth).time)
            listener.onItemClick(date)
            Log.d(TAG, "DatePickerFragment onDateSet  $date")
        }
    }
}