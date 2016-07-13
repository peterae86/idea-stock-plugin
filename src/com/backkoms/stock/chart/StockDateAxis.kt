package com.backkoms.stock.chart

import org.jfree.chart.axis.DateAxis
import org.jfree.chart.axis.DateTick
import org.jfree.chart.axis.Tick
import org.jfree.ui.RectangleEdge
import org.jfree.ui.TextAnchor
import org.jstockchart.axis.TickAlignment
import org.jstockchart.axis.logic.AbstractLogicAxis
import org.jstockchart.axis.logic.LogicDateTick
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.text.DateFormat
import java.text.ParseException
import java.util.*

/**
 * Created by test on 2016/7/13.
 */
class StockDateAxis(baseDate: Date, dateFormat: DateFormat) : DateAxis() {
    private val ticks = ArrayList<LogicDateTick>()
    var formatter: DateFormat = dateFormat
    private val calendar = Calendar.getInstance()
    private var baseDate: Date = baseDate
    private var dateFormat: DateFormat = dateFormat


    fun addDateTick(var1: String, var2: TickAlignment) {
        this.ticks.add(LogicDateTick(this.parseTime(var1), var1, var2))
    }

    fun addDateTick(var1: String) {
        this.addDateTick(var1, TickAlignment.MID)
    }

    private fun parseTime(var1: String): Date {
        var var2: Date? = null

        try {
            var2 = this.formatter.parse(var1)
        } catch (var6: ParseException) {
            throw IllegalArgumentException("Parse time failed: \'" + var1 + "\'")
        }

        this.calendar.clear()
        this.calendar.time = var2
        val var3 = this.calendar.get(11)
        val var4 = this.calendar.get(12)
        val var5 = this.calendar.get(13)
        this.calendar.clear()
        this.calendar.time = this.baseDate
        this.calendar.set(11, var3)
        this.calendar.set(12, var4)
        this.calendar.set(13, var5)
        return this.calendar.time
    }

    fun getLogicTicks(): List<LogicDateTick> {
        return this.ticks
    }

    public override fun refreshTicksHorizontal(var1: Graphics2D, var2: Rectangle2D, var3: RectangleEdge): List<Tick> {
        var var4: TextAnchor? = null
        var var5: TextAnchor? = null
        var var6 = 0.0
        if (this.isVerticalTickLabels) {
            var4 = TextAnchor.CENTER_RIGHT
            var5 = TextAnchor.CENTER_RIGHT
            if (var3 === RectangleEdge.TOP) {
                var6 = 1.5707963267948966
            } else {
                var6 = -1.5707963267948966
            }
        } else if (var3 === RectangleEdge.TOP) {
            var4 = TextAnchor.BOTTOM_CENTER
            var5 = TextAnchor.BOTTOM_CENTER
        } else {
            var4 = TextAnchor.TOP_CENTER
            var5 = TextAnchor.TOP_CENTER
        }

        return createTicksHorizontal(this.ticks, var4, var5, var6)
    }

    private fun createTicksHorizontal(var0: List<LogicDateTick>, var1: TextAnchor, var2: TextAnchor, var3: Double): List<Tick> {
        val var5 = ArrayList<Tick>()

        for (var6 in var0.indices) {
            val var8 = var0[var6].tickAlignment
            var5.add(DateTick(var0[var6].tickDate, var0[var6].tickLabel, adjustAnchorHorizontal(var1, var8), adjustAnchorHorizontal(var2, var8), var3))
        }

        return var5
    }

    private fun adjustAnchorHorizontal(var0: TextAnchor, var1: TickAlignment): TextAnchor {
        var var2: TextAnchor = var0
        if (var1 == TickAlignment.MID) {
            var2 = var0
        } else if (var1 == TickAlignment.START) {
            if (var0 == TextAnchor.CENTER_RIGHT) {
                var2 = TextAnchor.CENTER_RIGHT
            } else if (var0 == TextAnchor.BOTTOM_CENTER) {
                var2 = TextAnchor.BOTTOM_LEFT
            } else if (var0 == TextAnchor.TOP_CENTER) {
                var2 = TextAnchor.TOP_LEFT
            } else {
                var2 = var0
            }
        } else if (var1 == TickAlignment.END) {
            if (var0 == TextAnchor.CENTER_RIGHT) {
                var2 = TextAnchor.CENTER_RIGHT
            } else if (var0 == TextAnchor.BOTTOM_CENTER) {
                var2 = TextAnchor.BOTTOM_RIGHT
            } else if (var0 == TextAnchor.TOP_CENTER) {
                var2 = TextAnchor.TOP_RIGHT
            } else {
                var2 = var0
            }
        }
        return var2
    }

}