package com.backkoms.stock.ui.view

import com.backkoms.stock.data.InitialDataHandler
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.data.StockData
import com.backkoms.stock.listener.AddListener
import com.backkoms.stock.ui.chart.StockSeriesChartFactory
import com.backkoms.stock.ui.chart.config.StockChartConfig
import com.backkoms.stock.ui.chart.dataset.StockDataSet
import com.backkoms.stock.ui.component.StockListRenderer
import com.backkoms.stock.ui.form.StockWindowForm
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import javafx.scene.control.SelectionModel
import org.jfree.chart.ChartPanel
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Insets
import java.util.*
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

/**
 * Created by test on 2016/7/15.
 */
class StockView : StockWindowForm, AddListener, ListSelectionListener {
    val model: DefaultListModel<Any>;
    val stockSet: MutableSet<String> = HashSet();

    constructor() : super() {
        model = DefaultListModel<Any>()
        var s: ListSelectionModel = DefaultListSelectionModel()
        stockList.model = model
        stockList.selectionModel = s
        stockList.cellRenderer = StockListRenderer()
        stockList.addListSelectionListener(this)
    }

    override fun add(stockName: String, stockCode: String) {
        synchronized(this) {
            if (!stockSet.contains(stockName)) {
                RealTimeStockData.registerStockCode(stockCode, object : InitialDataHandler {
                    override fun handleInitialData(data: StockData, sampleHistory: List<StockData>) {
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
                        model.addElement(ListItemView(stockName, Color.BLACK, Color.white).container)
                        stockTabs.addTab(stockName, panel2)
                        stockSet.add(stockName)
                    }
                })
            }
        }
    }

    override fun valueChanged(e: ListSelectionEvent?) {
        var item = model.get(stockList.selectedIndex) as JPanel
        var stockName = item.name
        stockTabs.selectedIndex = stockTabs.indexOfTab(stockName)
    }


}