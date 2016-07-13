package com.backkoms.stock.chart

import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.SegmentedTimeline
import org.jfree.data.Range
import org.jfree.data.time.Minute
import org.jstockchart.area.PriceArea
import org.jstockchart.area.TimeseriesArea
import org.jstockchart.area.VolumeArea
import org.jstockchart.axis.TickAlignment
import org.jstockchart.axis.logic.CentralValueAxis
import org.jstockchart.axis.logic.LogicDateAxis
import org.jstockchart.axis.logic.LogicNumberAxis
import org.jstockchart.dataset.TimeseriesDataset
import org.jstockchart.model.TimeseriesItem
import org.jstockchart.plot.TimeseriesPlot
import org.jstockchart.util.DateUtils
import java.awt.Color
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by test on 2016/7/12.
 */

fun createTimeSeriesChart(title: String, timeSeriesDataSet: StockDateSet, timeSeriesArea: TimeseriesArea, createLegend: Boolean): JFreeChart {
    var stockAxis = StockDateAxis(Date(), SimpleDateFormat("HH:mm"));
    stockAxis.addDateTick("09:30", TickAlignment.START)
    stockAxis.addDateTick("10:00")
    stockAxis.addDateTick("10:30")
    stockAxis.addDateTick("11:00")
    stockAxis.addDateTick("11:30", TickAlignment.END)
    stockAxis.addDateTick("13:00", TickAlignment.START)
    stockAxis.addDateTick("13:30")
    stockAxis.addDateTick("14:00")
    stockAxis.addDateTick("14:30", TickAlignment.END)
    stockAxis.addDateTick("15:00", TickAlignment.END)
    val plot = test(stockAxis, timeSeriesArea, timeSeriesDataSet)
    val chart = JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, createLegend)
    chart.backgroundPaint = Color(60, 63, 65)

    return chart
}

fun createChart(): ChartPanel {
    val timeline = SegmentedTimeline(SegmentedTimeline.MINUTE_SEGMENT_SIZE, 1351, 89)
    timeline.startTime = SegmentedTimeline.firstMondayAfter1900() + 780 * SegmentedTimeline.MINUTE_SEGMENT_SIZE
    var data: MutableList<TimeseriesItem> = ArrayList();
    var time = System.currentTimeMillis();
    var random = Random();
    for (i in 0..10000) {
        data.add(TimeseriesItem(Date(time + i * 60000), random.nextDouble() + i / 100, random.nextInt(100) * 1.0))
    }


    val dataset = TimeseriesDataset(Minute::class.java, 1,
            timeline, true)
    dataset.addDataItems(data)

    var dataSet2 = StockDateSet()
    for (i in 0..2000) {
        dataSet2.add(Date(time + i * 60000), random.nextDouble() + i / 100, random.nextInt(100))
    }

    // Creates logic price axis.
    val logicPriceAxis = CentralValueAxis(
            5.0, Range(
            0.0, 10.0), 9,
            DecimalFormat(".00"))
    val priceArea = PriceArea(logicPriceAxis)

    // Creates logic volume axis.
    val logicVolumeAxis = LogicNumberAxis(Range(0.0, 100.0), 5, DecimalFormat("0"))
    val volumeArea = VolumeArea(logicVolumeAxis)

    val timeseriesArea = TimeseriesArea(priceArea,
            volumeArea, createlogicDateAxis(DateUtils.createDate(2008, 1, 1)))
    var mChart = createTimeSeriesChart("dasdas", dataSet2, timeseriesArea, false);
    var dd = ChartPanel(mChart)
    dd.isVisible = true;
    return dd;
}

private fun createlogicDateAxis(baseDate: Date): LogicDateAxis {
    val logicDateAxis = LogicDateAxis(baseDate,
            SimpleDateFormat("HH:mm"))

    return logicDateAxis
}