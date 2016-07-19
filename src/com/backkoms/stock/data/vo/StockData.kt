package com.backkoms.stock.data.vo

import java.util.*

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
class StockData {
    lateinit var time: Date
    var price: Double = 0.0
    var volume: Long = 0
    var centralValue: Double = 0.0
    var maxValue: Double = 0.0
    var minValue: Double = 0.0
    lateinit var name: String
}