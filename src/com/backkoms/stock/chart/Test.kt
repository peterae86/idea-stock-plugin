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
    var3.setGap(timeseriesArea.getGap())
    var3.setOrientation(timeseriesArea.getOrientation())
    var3.setDomainAxis(var2)
    var3.setDomainAxisLocation(timeseriesArea.getDateAxisLocation())
    if (timeseriesArea.getPriceWeight() <= 0 && timeseriesArea.getVolumeWeight() <= 0) {
        throw IllegalArgumentException("Illegal weight value: priceWeight=" + timeseriesArea.getPriceWeight() + ", volumeWeight=" + timeseriesArea.getVolumeWeight())
    } else {
        var var4: XYPlot
        if (timeseriesArea.getPriceWeight() > 0) {
            var4 = createPricePlot(timeseriesArea, dataSet)
            var3.add(var4, timeseriesArea.getPriceWeight())
        }

        if (timeseriesArea.getVolumeWeight() > 0) {
            var4 = createVolumePlot(timeseriesArea, dataSet)
            var3.add(var4, timeseriesArea.getVolumeWeight())
        }

        return var3
    }
}

private fun createPricePlot(timeseriesArea: TimeseriesArea, dataSet: StockDateSet): XYPlot {
    val var1 = timeseriesArea.getPriceArea()

    //    if (var1.isAverageVisible()) {
    //        var2.addSeries(dataSet.getAverageTimeSeries().getTimeSeries())
    //    }

    val var3 = var1.getLogicPriceAxis()
    val var4 = TimeseriesNumberAxis(var3.getLogicTicks())
    val var5 = XYLineAndShapeRenderer(true, false)
    var4.upperBound = var3.getUpperBound()
    var4.lowerBound = var3.getLowerBound()
    var5.setSeriesPaint(0, var1.getPriceColor())
    var5.setSeriesPaint(1, var1.getAverageColor())
    val var6 = TimeseriesNumberAxis(var3.getRatelogicTicks())
    var6.upperBound = var3.getUpperBound()
    var6.lowerBound = var3.getLowerBound()
    val var7 = XYPlot(dataSet.priceSet, null, var4, var5)
    var7.backgroundPaint = backgroundColor
    var7.orientation = var1.getOrientation()
    var7.rangeAxisLocation = var1.getPriceAxisLocation()
    if (var1.isRateVisible()) {
        var7.setRangeAxis(1, var6)
        var7.setRangeAxisLocation(1, var1.getRateAxisLocation())
        var7.setDataset(1, null)
        var7.mapDatasetToRangeAxis(1, 1)
    }

    if (var1.isMarkCentralValue()) {
        val var8 = var3.getCentralValue()
        if (var8 != null) {
            var7.addRangeMarker(ValueMarker(var8!!.toDouble(), var1.getCentralPriceColor(), BasicStroke()))
        }
    }

    return var7
}

private fun createVolumePlot(timeseriesArea: TimeseriesArea, dataSet: StockDateSet): XYPlot {
    val var1 = timeseriesArea.getVolumeArea()
    val var2 = var1.getLogicVolumeAxis()
    val var3 = TimeseriesNumberAxis(var2.getLogicTicks())
    var3.upperBound = var2.getUpperBound()
    var3.lowerBound = var2.getLowerBound()
    var3.autoRangeIncludesZero = false
    val var4 = XYBarRenderer()
    var4.setSeriesPaint(0, var1.getVolumeColor())
    var4.setShadowVisible(false)
    val var5 = XYPlot(dataSet.volumeSet, null, var3, var4)
    var5.backgroundPaint = backgroundColor
    var5.orientation = var1.getOrientation()
    var5.rangeAxisLocation = var1.getVolumeAxisLocation()
    return var5
}