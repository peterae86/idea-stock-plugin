package com.backkoms.stock.util

import org.joda.time.LocalDateTime
import java.util.*

/**
 * Created by test on 2016/7/15.
 */
object DateUtil {
    fun localTimeToDate(localDate: LocalDateTime): Date {
        return localDate.toDate()
    }
}