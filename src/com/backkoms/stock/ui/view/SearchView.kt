package com.backkoms.stock.ui.view

import com.backkoms.stock.data.InitialDataHandler
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.data.StockData
import com.backkoms.stock.data.StockInfo
import com.backkoms.stock.listener.AddListener
import com.backkoms.stock.ui.chart.StockSeriesChartFactory
import com.backkoms.stock.ui.chart.config.StockChartConfig
import com.backkoms.stock.ui.chart.dataset.StockDataSet
import com.backkoms.stock.ui.component.SearchResultListRenderer
import com.backkoms.stock.ui.component.StockListRenderer
import com.backkoms.stock.ui.form.SearchForm
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import org.jfree.chart.ChartPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Insets
import java.util.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

/**
 * Created by test on 2016/7/17.
 */
class SearchView : SearchForm, DocumentListener, ListSelectionListener {
    val model: DefaultListModel<Any>;
    var fields: MutableList<JTextField> = ArrayList();
    var addListeners: MutableList<AddListener> = ArrayList();
    var stockInfoList: MutableList<StockInfo> = ArrayList();

    constructor() : super() {
        keyword.document.addDocumentListener(this)
        model = DefaultListModel<Any>()
        var s: ListSelectionModel = DefaultListSelectionModel()
        searchResultList.model = model
        searchResultList.selectionModel = s
        searchResultList.cellRenderer = SearchResultListRenderer()
        searchResultList.addListSelectionListener(this)
        searchResultList.selectionBackground = Color.BLUE
    }

    override fun changedUpdate(e: DocumentEvent) {
        doSearch(e)
    }

    override fun removeUpdate(e: DocumentEvent) {
        doSearch(e)
    }

    override fun insertUpdate(e: DocumentEvent) {
        doSearch(e)
    }

    override fun valueChanged(e: ListSelectionEvent) {
        synchronized(this) {
            var field = model.get(searchResultList.selectedIndex) as JTextField
            field.background = Color.BLUE
            for (i in fields.indices) {
                if (i != searchResultList.selectedIndex) {
                    fields[i].background = Color.BLACK
                }
            }
            var stockInfo = stockInfoList[searchResultList.selectedIndex]
            addListeners.forEach { x ->
                x.add(stockInfo.name, stockInfo.code)
            }

        }
    }

    fun addAddListener(l: AddListener) {
        addListeners.add(l);
    }


    private fun doSearch(e: DocumentEvent) {
        var doc = e.document;
        var s = doc.getText(0, doc.length);
        if (s.isNotEmpty()) {
            var res = RealTimeStockData.queryStock(s)
            synchronized(this) {
                if (keyword.text == s) {
                    model.removeAllElements()
                    fields.clear()
                    res.forEach { x ->
                        var textField = JTextField(x.name + " " + x.code)
                        model.addElement(textField)
                        fields.add(textField)
                        stockInfoList.add(x)
                    }
                }
            }
        }
    }

}