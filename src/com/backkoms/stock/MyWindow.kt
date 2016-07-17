package com.backkoms.stock

import com.backkoms.stock.data.InitialDataHandler
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.data.StockData
import com.backkoms.stock.ui.chart.StockSeriesChartFactory
import com.backkoms.stock.ui.chart.config.StockChartConfig
import com.backkoms.stock.ui.chart.dataset.StockDataSet
import com.backkoms.stock.ui.form.SearchForm
import com.backkoms.stock.ui.view.SearchView
import com.backkoms.stock.ui.view.StockView
import com.backkoms.stock.util.DateUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.util.MinimizeButton
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import org.jfree.chart.ChartPanel
import java.awt.*
import java.time.LocalDate
import java.util.*
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * https://wizardforcel.gitbooks.io/jfreechart-dev-guide/content/7.html
 * Created by test on 2016/7/10.
 */
class MyWindow : ToolWindowFactory {
    var stockView: StockView;
    var searchView: SearchView;

    constructor() {
        stockView = StockView()
        searchView = SearchView()
        searchView.addAddListener(stockView)
    }


    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        var contentFactory: ContentFactory = ContentFactory.SERVICE.getInstance();

        var popup = JBPopupFactory.getInstance().createComponentPopupBuilder(searchView.container, null).setCancelOnClickOutside(false).setBelongsToGlobalPopupStack(true).setFocusable(true).setRequestFocus(true).setMovable(true).setResizable(true)
                .setCancelOnOtherWindowOpen(false).setCancelButton(MinimizeButton("Hide"))
                .setTitle("Regular expressions syntax").setDimensionServiceKey(null, "RegExHelpPopup", true).createPopup();
        popup.showInFocusCenter()
        var content: Content = contentFactory.createContent(stockView.container, "", false);
        toolWindow.contentManager.addContent(content);
    }
}