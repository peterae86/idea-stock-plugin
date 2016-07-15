package com.backkoms.stock.ui.chart

import com.backkoms.stock.ui.chart.axis.StockDateAxis
import com.backkoms.stock.ui.chart.axis.StockPriceAxis
import com.backkoms.stock.ui.chart.axis.StockRateAxis
import com.backkoms.stock.ui.chart.config.StockChartConfig
import com.backkoms.stock.ui.chart.dataset.StockDataSet
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.CombinedDomainXYPlot
import org.jfree.chart.plot.ValueMarker
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYBarRenderer
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import java.awt.BasicStroke
import java.awt.Color

/**
 * Created by test on 2016/7/15.
 */

private val backgroundColor: Color = Color(60, 63, 65)


fun createTimeSeriesChart(timeSeriesDataSet: StockDataSet, stockTimeSeriesArea: StockChartConfig): JFreeChart {
    var dataAxis = StockDateAxis();
    var priceAxis = StockPriceAxis(stockTimeSeriesArea.centralValue, stockTimeSeriesArea.maxValue, stockTimeSeriesArea.minValue);
    var rateAxis = StockRateAxis(stockTimeSeriesArea.centralValue, stockTimeSeriesArea.maxValue, stockTimeSeriesArea.minValue, priceAxis);

    val plot = CombinedDomainXYPlot(dataAxis)
    plot.gap = stockTimeSeriesArea.gap
    plot.orientation = stockTimeSeriesArea.orientation
    plot.domainAxisLocation = stockTimeSeriesArea.dateAxisLocation
    plot.add(createPricePlot(stockTimeSeriesArea, timeSeriesDataSet, priceAxis, rateAxis), stockTimeSeriesArea.priceWeight)
    plot.add(createVolumePlot(stockTimeSeriesArea, timeSeriesDataSet), stockTimeSeriesArea.volumeWeight)
    val chart = StockSeriesChart(plot)
    chart.backgroundPaint = Color(60, 63, 65)
    return chart
}


private fun createPricePlot(area: StockChartConfig, dataSet: StockDataSet, priceAxis: StockPriceAxis, rateAxis: StockRateAxis): XYPlot {
    val xyLineAndShapeRenderer = XYLineAndShapeRenderer(true, false)
    val res = XYPlot(dataSet.priceSet, null, priceAxis, xyLineAndShapeRenderer)
    res.backgroundPaint = backgroundColor
    res.orientation = area.orientation
    res.rangeAxisLocation = area.priceAxisLocation
    res.setRangeAxis(1, rateAxis)
    res.setRangeAxisLocation(1, area.rateAxisLocation)
    res.addRangeMarker(ValueMarker(priceAxis.centralValue, Color.white, BasicStroke()))
    return res
}


private fun createVolumePlot(area: StockChartConfig, dataSet: StockDataSet): XYPlot {
    val numberAxis = NumberAxis()
    numberAxis.labelPaint = Color.white
    numberAxis.tickLabelPaint = Color.white
    numberAxis.isAutoRange = true
    numberAxis.autoRangeIncludesZero = true
    val xyBarRenderer = XYBarRenderer()
    xyBarRenderer.setSeriesPaint(0, area.volumeColor)
    xyBarRenderer.setShadowVisible(false)
    val res = XYPlot(dataSet.volumeSet, null, numberAxis, xyBarRenderer)
    res.backgroundPaint = backgroundColor
    res.orientation = area.orientation
    res.rangeAxisLocation = area.volumeAxisLocation
    return res
}