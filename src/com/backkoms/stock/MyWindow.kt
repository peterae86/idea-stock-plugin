package com.backkoms.stock

import com.backkoms.stock.ui.StockView
import com.backkoms.stock.ui.chart.config.StockChartConfig
import com.backkoms.stock.ui.chart.createTimeSeriesChart
import com.backkoms.stock.ui.chart.dataset.StockDataSet
import com.backkoms.stock.ui.window.StockWindowForm
import com.backkoms.stock.util.localTimeToDate
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import org.jfree.chart.ChartPanel
import java.awt.Color
import java.time.LocalDate
import java.util.*

/**
 * https://wizardforcel.gitbooks.io/jfreechart-dev-guide/content/7.html
 * Created by test on 2016/7/10.
 */
class MyWindow : ToolWindowFactory {
    var stockView: StockView;

    constructor() {
        stockView = StockView()
    }


    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        var contentFactory: ContentFactory = ContentFactory.SERVICE.getInstance();
        var stockDateSet = StockDataSet()
        Thread() {
            run {
                var time = localTimeToDate(LocalDate.now().atTime(9, 30)).time;
                var random = Random();
                var i = 0
                while (true) {
                    stockDateSet.add(Date(time + i * 6000), 100 + (random.nextDouble() + i / 300.0), random.nextInt(100))
                    i++
                    println(i)
                    Thread.sleep(100)
                }
            }
        }.start()

        stockView.stockTabs.add("test", ChartPanel(createTimeSeriesChart(stockDateSet, StockChartConfig(100.0, 110.0, 90.0))));
        var content: Content = contentFactory.createContent(stockView.container, "", false);
        toolWindow.contentManager.addContent(content);
    }
}