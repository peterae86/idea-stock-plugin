package com.backkoms.stock.ui.view

import com.backkoms.stock.context.MyConfig
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.data.vo.StockData
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
import javafx.scene.control.SelectionModel
import org.jfree.chart.ChartPanel
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

/**
 * Created by test on 2016/7/15.
 */
class StockView : StockWindowForm, AddListener, ListSelectionListener {

    val model: DefaultListModel<Any>;
    val stockSet: MutableSet<String> = HashSet();
    var searchView: SearchView;
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
        var popup = JBPopupFactory.getInstance().createComponentPopupBuilder(searchView.container, null)
                .setCancelOnClickOutside(false)
                .setBelongsToGlobalPopupStack(true)
                .setFocusable(true)
                .setRequestFocus(true)
                .setMovable(true)
                .setResizable(true)
                .setCancelOnOtherWindowOpen(false)
                .setCancelButton(MinimizeButton("Hide"))
                .setTitle("Search stock").setDimensionServiceKey(null, "Search stock", true).createPopup();
        addButton.addActionListener {
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


    override fun add(stockCode: String) {
        synchronized(this) {
            if (!stockSet.contains(stockCode)) {
                stockSet.add(stockCode)
                RealTimeStockData.registerStockCode(stockCode, {
                    data: StockData, sampleHistory: List<StockData> ->
                    stockNameCodeMap.put(data.name, stockCode)
                    var stockDateSet = StockDataSet()
                    sampleHistory.forEach {
                        x ->
                        stockDateSet.add(x.time, x.price, x.volume)
                    }
                    var panel2 = JPanel()
                    var layout = GridLayoutManager(1, 2, Insets(10, 10, 10, 10), 5, 5)
                    panel2.layout = layout
                    var panel = ChartPanel(StockSeriesChartFactory.createTimeSeriesChart(stockDateSet,
                            StockChartConfig(data.centralValue, data.maxValue, data.minValue)))
                    panel.preferredSize = Dimension(800, 260)
                    panel.focusTraversalKeysEnabled = false
                    panel.mouseListeners.forEach { x -> panel.removeMouseListener(x) }
                    panel2.add(panel, GridConstraints())
                    stockDateSet.centralValue = data.centralValue
                    var listView = ListItemView(data.name, Color.BLACK, Color.white)
                    model.addElement(listView.container)
                    stockTabs.addTab(data.name, panel2)
                    stockDataSetMap.put(data.name, stockDateSet)
                    stockListMap.put(data.name, listView)
                    MyConfig.addStockCode(stockCode)
                })
            }
        }
    }

    override fun valueChanged(e: ListSelectionEvent?) {
        var index = stockList.selectedIndex
        for (i in 0..(model.size() - 1)) {
            var item = model.get(i) as JPanel
            if (i != index) {
                item.background = Color.BLACK
            } else {
                item.background = Color.WHITE
                var stockName = item.name
                stockTabs.selectedIndex = stockTabs.indexOfTab(stockName)
            }
        }
    }


}