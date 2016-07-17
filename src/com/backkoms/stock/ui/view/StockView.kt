package com.backkoms.stock.ui.view

import com.backkoms.stock.data.InitialDataHandler
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.data.StockData
import com.backkoms.stock.ui.component.StockListRenderer
import com.backkoms.stock.ui.form.StockWindowForm
import javafx.scene.control.SelectionModel
import java.awt.Color
import javax.swing.*

/**
 * Created by test on 2016/7/15.
 */
class StockView : StockWindowForm() {
    fun addStock(stockName: String, stockCode: String) {
        var model = DefaultListModel<Any>()
        var s: ListSelectionModel = DefaultListSelectionModel()
        stockList.model = model
        stockList.selectionModel = s
        stockList.cellRenderer = StockListRenderer()
        model.addElement(ListItemView(stockName, Color.BLACK, Color.white).container)
    }


}