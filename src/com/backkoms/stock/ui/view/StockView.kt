package com.backkoms.stock.ui.view

import com.backkoms.stock.context.Global
import com.backkoms.stock.context.MyConfig
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.listener.AddListener
import com.backkoms.stock.ui.chart.StockSeriesChartFactory
import com.backkoms.stock.ui.chart.config.StockChartConfig
import com.backkoms.stock.ui.chart.dataset.StockDataSet
import com.backkoms.stock.ui.component.StockListRenderer
import com.backkoms.stock.ui.form.StockWindowForm
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.util.MinimizeButton
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import org.jfree.chart.ChartPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Insets
import java.util.concurrent.ConcurrentHashMap
import javax.swing.DefaultListModel
import javax.swing.DefaultListSelectionModel
import javax.swing.JPanel
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

/**
 * Created by test on 2016/7/15.
 */
class StockView : StockWindowForm, AddListener, ListSelectionListener {

    val model: DefaultListModel<Any>
    var searchView: SearchView
    val stockDataSetMap: MutableMap<String, StockDataSet> = ConcurrentHashMap()
    val stockListMap: MutableMap<String, ListItemView> = ConcurrentHashMap()
    val stockNameCodeMap: MutableMap<String, String> = ConcurrentHashMap()

    constructor() : super() {
        searchView = SearchView()
        searchView.addAddListener(this)
        model = DefaultListModel<Any>()
        var s: ListSelectionModel = DefaultListSelectionModel()
        stockList.model = model
        stockList.selectionModel = s
        stockList.cellRenderer = StockListRenderer()
        stockList.addListSelectionListener(this)
        addButton.addActionListener {
            var popup = JBPopupFactory.getInstance().createComponentPopupBuilder(searchView.container, null)
                    .setCancelOnClickOutside(false)
                    .setBelongsToGlobalPopupStack(true)
                    .setFocusable(true)
                    .setRequestFocus(true)
                    .setMovable(true)
                    .setResizable(true)
                    .setCancelOnOtherWindowOpen(false)
                    .setCancelButton(MinimizeButton("Hide"))
                    .setTitle("Search stock").setDimensionServiceKey(null, "Search stock", true).createPopup()
            popup.showInFocusCenter()
        }
        deleteButton.addActionListener {
            var i = stockList.selectedIndex
            if (i != -1) {
                var item = model.get(stockList.selectedIndex) as JPanel
                model.remove(i)
                stockTabs.removeTabAt(stockTabs.indexOfTab(item.name))
                stockDataSetMap.remove(item.name)
                stockListMap.remove(item.name)
                MyConfig.removeStockCode(stockNameCodeMap[item.name]!!)
            }
        }
        RealTimeStockData.addRealTimeDataListener {
            x ->
            if (stockDataSetMap[x.name]?.centralValue != 0.0) {
                stockDataSetMap[x.name]?.add(x.time, x.price, x.volume)
            }
            stockListMap[x.name]?.setRate((x.price / x.centralValue - 1) * 100.0)
        }
    }


    override fun add(stockCode: String, stockName: String) {
        synchronized(this) {
            if (!MyConfig.hasStock(stockCode)) {
                initStock(stockCode, stockName)
                MyConfig.addStockCode(stockCode, stockName)
            }
        }
    }

    fun initStock(stockCode: String, stockName: String) {
        var stockDateSet = StockDataSet()
        var stockPanel = JPanel()
        stockPanel.layout = GridLayoutManager(1, 2, Insets(10, 10, 10, 10), 5, 5)
        var panel = ChartPanel(StockSeriesChartFactory.createTimeSeriesChart(stockCode, stockDateSet, StockChartConfig()))
        panel.preferredSize = Dimension(800, 260)
        panel.focusTraversalKeysEnabled = false
        panel.mouseListeners.forEach { x -> panel.removeMouseListener(x) }
        stockPanel.add(panel, GridConstraints())
        var listView = ListItemView(stockName, Color.white)
        model.addElement(listView.container)
        stockTabs.addTab(stockName, stockPanel)
        stockDataSetMap.put(stockName, stockDateSet)
        stockListMap.put(stockName, listView)
        stockNameCodeMap.put(stockName, stockCode)
    }

    override fun valueChanged(e: ListSelectionEvent?) {
        var index = stockList.selectedIndex
        for (i in 0..(model.size() - 1)) {
            var item = model.get(i) as JPanel
            if (i != index) {
                item.background = Global.backgroundColor
                item.components.forEach {
                    x ->
                    x.background = Global.backgroundColor
                }
            } else {
                item.background = Global.selectedColor
                item.components.forEach {
                    x ->
                    x.background = Global.selectedColor
                }
                stockTabs.selectedIndex = stockTabs.indexOfTab(item.name)
            }
        }
    }


}