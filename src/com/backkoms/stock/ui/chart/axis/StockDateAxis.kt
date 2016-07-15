package com.backkoms.stock.ui.chart.axis

import com.backkoms.stock.util.localTimeToDate
import org.jfree.chart.axis.DateAxis
import org.jfree.chart.axis.DateTick
import org.jfree.chart.axis.SegmentedTimeline
import org.jfree.chart.axis.Tick
import org.jfree.ui.RectangleEdge
import org.jfree.ui.TextAnchor
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.time.LocalDate
import java.util.*

/**
 * Created by test on 2016/7/13.
 */
class StockDateAxis : DateAxis {
    private var ticks: List<Tick> = ArrayList()

    constructor() {
        setRange(localTimeToDate(LocalDate.now().atTime(9, 30)), localTimeToDate(LocalDate.now().atTime(15, 0)))
        val timeline = SegmentedTimeline(SegmentedTimeline.MINUTE_SEGMENT_SIZE, 1350, 90)
        timeline.startTime = SegmentedTimeline.firstMondayAfter1900() + 780 * SegmentedTimeline.MINUTE_SEGMENT_SIZE
        this.timeline = timeline
        this.labelPaint = Color.white
        this.tickLabelPaint = Color.white
    }

    public override fun refreshTicksHorizontal(g2d: Graphics2D, r2d: Rectangle2D, re: RectangleEdge): List<Tick> {
        if (this.ticks.isEmpty()) {
            var var4: TextAnchor
            var var5: TextAnchor
            var var6 = 0.0
            if (this.isVerticalTickLabels) {
                var4 = TextAnchor.CENTER_RIGHT
                var5 = TextAnchor.CENTER_RIGHT
                if (re === org.jfree.ui.RectangleEdge.TOP) {
                    var6 = 1.5707963267948966
                } else {
                    var6 = -1.5707963267948966
                }
            } else if (re === org.jfree.ui.RectangleEdge.TOP) {
                var4 = TextAnchor.BOTTOM_CENTER
                var5 = TextAnchor.BOTTOM_CENTER
            } else {
                var4 = TextAnchor.TOP_CENTER
                var5 = TextAnchor.TOP_CENTER
            }

            this.ticks = createTicksHorizontal(var4, var5, var6)
        }
        return this.ticks;
    }

    private fun createTicksHorizontal(textAuthor: TextAnchor, rotationAnchor: TextAnchor, var3: Double): List<Tick> {
        val tickList = ArrayList<Tick>()

        var nowDate = LocalDate.now()
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(9, 30)), "09:30", adjustAnchorHorizontal(textAuthor, DateTickAlignment.START), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.START), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(10, 0)), "", adjustAnchorHorizontal(textAuthor, DateTickAlignment.MID), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.MID), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(10, 30)), "10:30", adjustAnchorHorizontal(textAuthor, DateTickAlignment.MID), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.MID), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(11, 0)), "", adjustAnchorHorizontal(textAuthor, DateTickAlignment.MID), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.MID), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(11, 30)), "11:30", adjustAnchorHorizontal(textAuthor, DateTickAlignment.END), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.END), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(13, 0)), "13:00", adjustAnchorHorizontal(textAuthor, DateTickAlignment.START), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.START), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(13, 30)), "", adjustAnchorHorizontal(textAuthor, DateTickAlignment.MID), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.MID), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(14, 0)), "14:00", adjustAnchorHorizontal(textAuthor, DateTickAlignment.MID), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.MID), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(14, 30)), "", adjustAnchorHorizontal(textAuthor, DateTickAlignment.MID), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.MID), var3))
        tickList.add(DateTick(localTimeToDate(nowDate.atTime(15, 0)), "15:00", adjustAnchorHorizontal(textAuthor, DateTickAlignment.END), adjustAnchorHorizontal(rotationAnchor, DateTickAlignment.END), var3))
        return tickList
    }

    private fun adjustAnchorHorizontal(textAnchor: TextAnchor, aligment: DateTickAlignment): TextAnchor {
        var res: TextAnchor = textAnchor
        if (aligment == DateTickAlignment.MID) {
            res = textAnchor
        } else if (aligment == DateTickAlignment.START) {
            if (textAnchor == TextAnchor.CENTER_RIGHT) {
                res = TextAnchor.CENTER_RIGHT
            } else if (textAnchor == TextAnchor.BOTTOM_CENTER) {
                res = TextAnchor.BOTTOM_LEFT
            } else if (textAnchor == TextAnchor.TOP_CENTER) {
                res = TextAnchor.TOP_LEFT
            } else {
                res = textAnchor
            }
        } else if (aligment == DateTickAlignment.END) {
            if (textAnchor == TextAnchor.CENTER_RIGHT) {
                res = TextAnchor.CENTER_RIGHT
            } else if (textAnchor == TextAnchor.BOTTOM_CENTER) {
                res = TextAnchor.BOTTOM_RIGHT
            } else if (textAnchor == TextAnchor.TOP_CENTER) {
                res = TextAnchor.TOP_RIGHT
            } else {
                res = textAnchor
            }
        }
        return res
    }

    enum class DateTickAlignment {
        START, MID, END
    }
}