package com.backkoms.stock.ui.view

import com.backkoms.stock.context.Global
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.data.vo.StockInfo
import com.backkoms.stock.listener.AddListener
import com.backkoms.stock.ui.component.SearchResultListRenderer
import com.backkoms.stock.ui.form.SearchForm
import java.awt.Color
import java.util.*
import javax.swing.DefaultListModel
import javax.swing.DefaultListSelectionModel
import javax.swing.JTextField
import javax.swing.ListSelectionModel
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
        if (searchResultList.selectedIndex != -1) {
            synchronized(this) {
                var field = model.get(searchResultList.selectedIndex) as JTextField
                field.background = Global.selectedColor
                for (i in fields.indices) {
                    if (i != searchResultList.selectedIndex) {
                        fields[i].background = Global.backgroundColor
                    }
                }
                var stockInfo = stockInfoList[searchResultList.selectedIndex]
                println(stockInfo.name + " " + stockInfo.code)
                addListeners.forEach { x ->
                    x.add(stockInfo.code, stockInfo.name)
                }
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
                    stockInfoList.clear()
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