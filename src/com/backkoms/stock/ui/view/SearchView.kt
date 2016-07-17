package com.backkoms.stock.ui.view

import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.ui.form.SearchForm
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

/**
 * Created by test on 2016/7/17.
 */
class SearchView : SearchForm, DocumentListener {
    constructor() : super() {
        keyword.document.addDocumentListener(this)
    }

    override fun changedUpdate(e: DocumentEvent) {
        var doc = e.document;
        var s = doc.getText(0, doc.length);
        RealTimeStockData.queryStock(s, {
            keyword, res ->
            if (keyword == s) {

            }
        })
    }

    override fun removeUpdate(e: DocumentEvent) {
        println("b")
    }

    override fun insertUpdate(e: DocumentEvent) {
        println("c")
    }

}