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


    init {
        priceSeries = TimeSeries("price");
        priceSet = TimeSeriesCollection();
        priceSet.addSeries(priceSeries)
        volumeSeries = TimeSeries("volume")
        volumeSet = TimeSeriesCollection();
        volumeSet.addSeries(volumeSeries)
    }

    fun add(time: Date, price: Double, volume: Long) {
        priceSeries.add(TimeSeriesDataItem(Second(time), price))
        volumeSeries.add(TimeSeriesDataItem(Second(time), volume))
    }
}