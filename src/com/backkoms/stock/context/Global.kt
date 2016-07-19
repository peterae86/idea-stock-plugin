package com.backkoms.stock.context

import com.backkoms.stock.ui.view.StockView
import com.fasterxml.jackson.databind.ObjectMapper
import java.awt.Color
import java.util.concurrent.Executors

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
object Global {
    val stockView: StockView = StockView();
    val scheduledThreadPool = Executors.newScheduledThreadPool(5)
    val fixedThreadPool = Executors.newFixedThreadPool(10)
    val objectMapper = ObjectMapper()

    val backgroundColor: Color = Color(60, 63, 65)
    val selectedColor: Color = Color(13, 41, 62)
}