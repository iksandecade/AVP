package project.iksandecade.avp.util

import android.annotation.SuppressLint
import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat

object AVPUtils {
    @SuppressLint("SimpleDateFormat")
    fun getSimplifiedDate(date: String?): String {
        return try {
            val dates = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(date)
            DateUtils.getRelativeTimeSpanString(dates.time).toString()
        } catch (e: ParseException) {
            ""
        }
    }
}