package com.backkoms.stock.ui.chart

import com.backkoms.stock.context.Common
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.Plot

/**
 * Created by test on 2016/7/15.
 */
class StockSeriesChart : JFreeChart {
    constructor(plot: Plot) : super(null, null, plot, false) {

    }
}