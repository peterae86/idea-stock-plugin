package com.backkoms.stock.ui.chart.dataset

import org.jfree.data.time.*
import java.util.*

/**
 * Created by xiaorui.guo on 2016/7/13.
 */

class StockDataSet {
    var priceSet: TimeSeriesCollection;
    var volumeSet: TimeSeriesCollection;
    var priceSeries: TimeSeries;
    var volumeSeries: TimeSeries;
    var centralValue: Double = 0.0
    var lastTime: Date = Date(0)
    var nowTime: Date = Date(0)
    var lastVolume = 0L
    var nowVolume = 0L

    init {
        priceSeries = TimeSeries("price");
        priceSet = TimeSeriesCollection();
        priceSet.addSeries(priceSeries)
        volumeSeries = TimeSeries("volume")
        volumeSet = TimeSeriesCollection();
        volumeSet.addSeries(volumeSeries)
    }

    fun add(time: Date, price: Double, volume: Long) {
        priceSeries.addOrUpdate(TimeSeriesDataItem(Second(time), price))
        volumeSeries.addOrUpdate(TimeSeriesDataItem(Second(time), volume - lastVolume))
        if (!nowTime.equals(time)) {
            lastTime = nowTime
            lastVolume = nowVolume
        }
        nowTime = time
        nowVolume = volume
    }

}