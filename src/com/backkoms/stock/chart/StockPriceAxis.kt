package com.backkoms.stock.chart;

import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.axis.NumberTick
import org.jfree.chart.axis.Tick
import org.jfree.chart.plot.ValueAxisPlot
import org.jfree.data.Range
import org.jfree.ui.RectangleEdge
import org.jfree.ui.TextAnchor
import org.jstockchart.axis.TimeseriesNumberAxis
import org.jstockchart.axis.logic.LogicNumberTick
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.util.*

/**
 * Created by test on 2016/7/14.
 */
class StockPriceAxis(centralValue: Double, maxValue: Double, minValue: Double
) : NumberAxis() {
    var centralValue = centralValue
    var maxValue = maxValue
    var minValue = minValue
    override fun autoAdjustRange() {
        val plot = plot ?: return // no plot, no data

        if (plot is ValueAxisPlot) {
            var range: Range? = plot.getDataRange(this)
            range = range ?: defaultAutoRange!!
            var maxChange = Math.max(Math.abs(centralValue - range.lowerBound), Math.abs(centralValue - range.upperBound))
            var newRange = Range(Math.max(minValue, centralValue * 0.99 - maxChange), Math.min(maxValue, centralValue * 1.01 + maxChange))
            setRange(newRange, false, false)
        }
    }

    override fun refreshTicksVertical(var1: Graphics2D?, var2: Rectangle2D?, var3: RectangleEdge?): MutableList<Tick>? {
        var var4: TextAnchor? = null
        var var5: TextAnchor? = null
        var var6 = 0.0
        if (this.isVerticalTickLabels) {
            var4 = TextAnchor.BOTTOM_CENTER
            var5 = TextAnchor.BOTTOM_CENTER
            if (var3 === RectangleEdge.LEFT) {
                var6 = -1.5707963267948966
            } else {
                var6 = 1.5707963267948966
            }
        } else if (var3 === RectangleEdge.LEFT) {
            var4 = TextAnchor.CENTER_RIGHT
            var5 = TextAnchor.CENTER_RIGHT
        } else {
            var4 = TextAnchor.CENTER_LEFT
            var5 = TextAnchor.CENTER_LEFT
        }
        return createTicks2(var4, var5, var6)

    }

    private fun createTicks2(var1: TextAnchor?, var2: TextAnchor?, var3: Double): MutableList<Tick>? {
        var list: MutableList<Tick> = ArrayList();
        list.add(NumberTick(range.lowerBound, String.format("%.2f", range.lowerBound), var1, var2, var3))
        list.add(NumberTick(range.upperBound, String.format("%.2f", range.upperBound), var1, var2, var3))
        return list
    }


}
