package com.backkoms.stock.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

/**
 * Created by test on 2016/7/15.
 */
fun localTimeToDate(localDate: LocalDateTime): Date {
    var zone = ZoneId.systemDefault();
    var instant = localDate.atZone(zone).toInstant();
    return Date.from(instant);
}