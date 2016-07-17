package com.backkoms.stock.ui.chart.config

import org.jfree.chart.axis.AxisLocation
import org.jfree.chart.plot.PlotOrientation
import java.awt.Color

/**
 * Created by xiaorui.guo on 2016/7/14.
 */
class StockChartConfig {
    val gap = 20.0
    val orientation = PlotOrientation.VERTICAL
    val volumeWeight = 1
    val priceWeight = 2
    val priceAxisLocation = AxisLocation.BOTTOM_OR_LEFT
    val rateAxisLocation = priceAxisLocation.opposite
    val volumeAxisLocation = AxisLocation.BOTTOM_OR_LEFT
    val volumeColor = Color.white
    val dateAxisLocation = AxisLocation.BOTTOM_OR_LEFT

    var centralValue: Double
    var maxValue: Double
    var minValue: Double


    constructor(centralValue: Double, maxValue: Double, minValue: Double) {
        this.centralValue = centralValue
        this.maxValue = maxValue
        this.minValue = minValue
    }
}