package com.backkoms.stock

import com.backkoms.stock.chart.createChart
import com.backkoms.stock.ui.StockWindow
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.StandardChartTheme
import org.jfree.chart.plot.DefaultDrawingSupplier
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYDataItem
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Paint
import java.awt.Stroke
import java.util.*

/**
 * https://wizardforcel.gitbooks.io/jfreechart-dev-guide/content/7.html
 * Created by test on 2016/7/10.
 */
class MyWindow : StockWindow, ToolWindowFactory {
    private val backgroundColor: Color = Color(60, 63, 65)

    private lateinit var myToolWindow: ToolWindow;

    constructor() {
    }


    fun createChart1(): ChartPanel {
        var theme: StandardChartTheme = StandardChartTheme.createDarknessTheme() as StandardChartTheme;
        theme.titlePaint = Color.lightGray
        theme.subtitlePaint = Color.lightGray
        theme.legendBackgroundPaint = backgroundColor
        theme.legendItemPaint = Color.lightGray
        theme.chartBackgroundPaint = backgroundColor
        theme.plotBackgroundPaint = backgroundColor
        theme.plotOutlinePaint = Color.lightGray
        theme.baselinePaint = Color.white
        theme.crosshairPaint = Color.red
        theme.labelLinkPaint = Color.lightGray
        theme.tickLabelPaint = Color.lightGray
        theme.axisLabelPaint = Color.lightGray
        theme.shadowPaint = Color.darkGray
        theme.itemLabelPaint = Color.lightGray
        theme.drawingSupplier = DefaultDrawingSupplier(
                arrayOf<Paint>(Color.lightGray),
                arrayOf<Paint>(Color.decode("0x0036CC"), Color.decode("0x0036CC")),
                arrayOf<Stroke>(BasicStroke(2.0f)),
                arrayOf<Stroke>(BasicStroke(0.5f)),
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE)
        theme.wallPaint = Color.darkGray
        theme.errorIndicatorPaint = Color.lightGray
        theme.gridBandPaint = Color(255, 255, 255, 20)
        theme.gridBandAlternatePaint = Color(255, 255, 255, 40)
        ChartFactory.setChartTheme(theme);
        var mCollection = GetCollection();
        var mChart = ChartFactory.createXYLineChart(
                "折线图",
                "X",
                "Y",
                mCollection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        var dd = ChartPanel(mChart)
        dd.isVisible = true;
        return dd;
    }

    private fun GetCollection(): XYDataset {

        var mCollection = XYSeriesCollection();
        var mSeriesFirst = XYSeries("First");
        Thread() {
            run {
                var random = Random()
                var i = 0;
                while (true) {
                    mSeriesFirst.add(XYDataItem(i++, random.nextInt(111)))
                    Thread.sleep(1000)
                }
            }
        }.start()
        mCollection.addSeries(mSeriesFirst);
        return mCollection;
    }


    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        myToolWindow = toolWindow;
        var contentFactory: ContentFactory = ContentFactory.SERVICE.getInstance();
        stockTabs.add("test", createChart());
        var content: Content = contentFactory.createContent(container, "", false);
        toolWindow.contentManager.addContent(content);
    }
}