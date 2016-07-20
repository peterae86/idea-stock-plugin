package com.backkoms.stock.ui.view

import com.backkoms.stock.context.Global
import com.backkoms.stock.ui.form.ListItemForm
import java.awt.Color
import javax.swing.JTextField
import javax.swing.border.EmptyBorder

/**
 * Created by test on 2016/7/16.
 */
class ListItemView : ListItemForm {
    val positiveRateColor = Color.red
    val negativeRateColor = Color.green
    val fontColor: Color

    constructor(name: String, fontColor: Color) {
        container.name = name
        stockName.border = EmptyBorder(0, 0, 0, 0)
        stockName.horizontalAlignment = JTextField.CENTER
        stockName.text = name
        stockName.background = Global.backgroundColor
        stockRate.border = EmptyBorder(0, 0, 0, 0)
        stockRate.text = "0.00%"
        stockRate.horizontalAlignment = JTextField.CENTER
        stockRate.background = Global.backgroundColor
        this.fontColor = fontColor
    }

    fun setRate(rate: Double): ListItemView {

        if (rate > 1e-5) {
            stockRate.text = String.format("%.2f%%", rate)
            stockRate.foreground = positiveRateColor
        } else if (rate < -1e-5) {
            stockRate.text = String.format("%.2f%%", rate)
            stockRate.foreground = negativeRateColor
        } else {
            stockRate.text = "0.00%"
            stockRate.foreground = fontColor
        }
        return this
    }
}