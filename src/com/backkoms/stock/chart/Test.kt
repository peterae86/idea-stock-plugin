package com.backkoms.stock.chart

import org.jfree.chart.axis.ValueAxis
import org.jfree.chart.plot.CombinedDomainXYPlot
import org.jfree.chart.plot.ValueMarker
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYBarRenderer
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.time.TimeSeriesCollection
import org.jfree.data.xy.XYDataset
import org.jstockchart.area.TimeseriesArea
import org.jstockchart.axis.TimeseriesNumberAxis
import org.jstockchart.dataset.TimeseriesDataset
import java.awt.BasicStroke
import java.awt.Color

/**
 * Created by xiaorui.guo on 2016/7/13.
 */

private val backgroundColor: Color = Color(60, 63, 65)
fun test(var2: StockDateAxis, timeseriesArea: TimeseriesArea, dataSet: StockDateSet): CombinedDomainXYPlot {
    val var3 = CombinedDomainXYPlot(var2)
    var3.gap = timeseriesArea.gap
    var3.orientation = timeseriesArea.orientation
    var3.domainAxis = var2
    var3.domainAxisLocation = timeseriesArea.dateAxisLocation
    if (timeseriesArea.priceWeight <= 0 && timeseriesArea.volumeWeight <= 0) {
        throw IllegalArgumentException("Illegal weight value: priceWeight=" + timeseriesArea.priceWeight + ", volumeWeight=" + timeseriesArea.volumeWeight)
    } else {
        var var4: XYPlot
        if (timeseriesArea.priceWeight > 0) {
            var4 = createPricePlot(timeseriesArea, dataSet)
            var3.add(var4, timeseriesArea.priceWeight)
        }

        if (timeseriesArea.volumeWeight > 0) {
            var4 = createVolumePlot(timeseriesArea, dataSet)
            var3.add(var4, timeseriesArea.volumeWeight)
        }

        return var3
    }
}

private fun createPricePlot(timeseriesArea: TimeseriesArea, dataSet: StockDateSet): XYPlot {
    val var1 = timeseriesArea.priceArea

    //    if (var1.isAverageVisible()) {
    //        var2.addSeries(dataSet.getAverageTimeSeries().getTimeSeries())
    //    }

    val var3 = var1.logicPriceAxis
    val var4 = TimeseriesNumberAxis(var3.logicTicks)
    val var5 = XYLineAndShapeRenderer(true, false)
    var4.upperBound = var3.upperBound
    var4.lowerBound = var3.lowerBound
    var5.setSeriesPaint(0, var1.priceColor)
    var5.setSeriesPaint(1, var1.averageColor)
    val var6 = TimeseriesNumberAxis(var3.ratelogicTicks)
    var6.upperBound = var3.upperBound
    var6.lowerBound = var3.lowerBound
    val var7 = XYPlot(dataSet.priceSet, null, var4, var5)
    var7.backgroundPaint = backgroundColor
    var7.orientation = var1.orientation
    var7.rangeAxisLocation = var1.priceAxisLocation
    if (var1.isRateVisible) {
        var7.setRangeAxis(1, var6)
        var7.setRangeAxisLocation(1, var1.rateAxisLocation)
        var7.setDataset(1, null)
        var7.mapDatasetToRangeAxis(1, 1)
    }

    if (var1.isMarkCentralValue) {
        val var8 = var3.centralValue
        if (var8 != null) {
            var7.addRangeMarker(ValueMarker(var8.toDouble(), var1.centralPriceColor, BasicStroke()))
        }
    }

    return var7
}

private fun createVolumePlot(timeseriesArea: TimeseriesArea, dataSet: StockDateSet): XYPlot {
    val var1 = timeseriesArea.volumeArea
    val var2 = var1.logicVolumeAxis
    val var3 = TimeseriesNumberAxis(var2.logicTicks)
    var3.upperBound = var2.upperBound
    var3.lowerBound = var2.lowerBound
    var3.autoRangeIncludesZero = false
    val var4 = XYBarRenderer()
    var4.setSeriesPaint(0, var1.volumeColor)
    var4.setShadowVisible(false)
    val var5 = XYPlot(dataSet.volumeSet, null, var3, var4)
    var5.backgroundPaint = backgroundColor
    var5.orientation = var1.orientation
    var5.rangeAxisLocation = var1.volumeAxisLocation
    return var5
}