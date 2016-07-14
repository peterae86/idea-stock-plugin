package com.backkoms.stock.chart

import org.jfree.chart.axis.AxisLocation
import org.jfree.chart.plot.PlotOrientation
import java.awt.Color

/**
 * Created by xiaorui.guo on 2016/7/14.
 */
class StockTimeSeriesArea {
    val gap = 10.0
    val orientation = PlotOrientation.VERTICAL
    val volumeWeight = 1
    val priceWeight = 2
    val priceAxisLocation = AxisLocation.BOTTOM_OR_LEFT
    val rateAxisLocation = priceAxisLocation.opposite
    val volumeAxisLocation = AxisLocation.BOTTOM_OR_LEFT
    val volumeColor = Color.white
    val dateAxisLocation = AxisLocation.BOTTOM_OR_LEFT

    var centralValue = null
}