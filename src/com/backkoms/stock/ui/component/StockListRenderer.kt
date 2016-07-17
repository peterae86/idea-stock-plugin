package com.backkoms.stock.ui.component

import java.awt.Component
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.ListCellRenderer

/**
 * Created by test on 2016/7/16.
 */
class StockListRenderer : ListCellRenderer<Any> {
    override fun getListCellRendererComponent(list: JList<out Any>?, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component? {
        return value as JPanel
    }
}