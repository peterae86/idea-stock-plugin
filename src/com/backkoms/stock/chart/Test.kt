package com.backkoms.stock.chart

import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.axis.NumberTickUnit
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

fun test(var2: StockDateAxis, timeseriesArea: StockTimeSeriesArea, dataSet: StockDateSet): CombinedDomainXYPlot {
    val var3 = CombinedDomainXYPlot(var2)
    var3.gap = timeseriesArea.gap
    var3.orientation = timeseriesArea.orientation
    var3.domainAxis = var2
    var3.domainAxisLocation = timeseriesArea.dateAxisLocation
    var var4: XYPlot
    var4 = createPricePlot(timeseriesArea, dataSet)
    var3.add(var4, timeseriesArea.priceWeight)
    var4 = createVolumePlot(timeseriesArea, dataSet)
    var3.add(var4, timeseriesArea.volumeWeight)
    return var3

}

private fun createPricePlot(var1: StockTimeSeriesArea, dataSet: StockDateSet): XYPlot {


    //    if (var1.isAverageVisible()) {
    //        var2.addSeries(dataSet.getAverageTimeSeries().getTimeSeries())
    //    }
    val var4 = StockPriceAxis(0.0, 20.0, -20.0)
    var4.tickLabelPaint = Color.white
    var4.labelPaint = Color.white
    var4.isAutoRange = true
    var4.isTickLabelsVisible = true
    var4.tickMarkPaint = Color.white
    var4.autoRangeIncludesZero = false;
    var4.autoRangeMinimumSize = 0.001;
    var4.tickUnit = NumberTickUnit(0.01);
    val var5 = XYLineAndShapeRenderer(true, false)
    //    var5.setSeriesPaint(0, var1.priceColor)
    //    var5.setSeriesPaint(1, var1.averageColor)
    val var6 = StockRateAxis(0.0, 20.0, -20.0)
    var6.tickLabelPaint = Color.white
    var6.labelPaint = Color.white
    var6.isAutoRange = true
    var6.isTickLabelsVisible = true
    var6.tickMarkPaint = Color.white
    var6.autoRangeIncludesZero = false;
    var6.tickUnit = NumberTickUnit(0.01);
    val var7 = XYPlot(dataSet.priceSet, null, var4, var5)
    var7.backgroundPaint = backgroundColor
    var7.orientation = var1.orientation
    var7.rangeAxisLocation = var1.priceAxisLocation
    //    if (var1.isRateVisible) {
    var7.setRangeAxis(1, var6)
    var7.setRangeAxisLocation(1, var1.rateAxisLocation)
    var7.setDataset(1, null)
    var7.mapDatasetToRangeAxis(1, 1)
    //    }
    //
    //    if (var1.isMarkCentralValue) {
    //        val var8 = var3.centralValue
    //        if (var8 != null) {
    //            var7.addRangeMarker(ValueMarker(var8.toDouble(), var1.centralPriceColor, BasicStroke()))
    //        }
    //    }

    return var7
}

private fun createVolumePlot(var1: StockTimeSeriesArea, dataSet: StockDateSet): XYPlot {
    val var3 = NumberAxis()
    var3.labelPaint = Color.white
    var3.tickLabelPaint = Color.white
    var3.isAutoRange = true
    var3.autoRangeIncludesZero = true
    val var4 = XYBarRenderer()
    var4.setSeriesPaint(0, var1.volumeColor)
    var4.setShadowVisible(false)
    val var5 = XYPlot(dataSet.volumeSet, null, var3, var4)
    var5.backgroundPaint = backgroundColor
    var5.orientation = var1.orientation
    var5.rangeAxisLocation = var1.volumeAxisLocation
    return var5
}