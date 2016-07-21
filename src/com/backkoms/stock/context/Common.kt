package com.backkoms.stock.context

import com.backkoms.stock.ui.view.StockView
import com.fasterxml.jackson.databind.ObjectMapper
import java.awt.Color
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
class Common {
    companion object {
        val fixedThreadPool: ExecutorService = Executors.newFixedThreadPool(10)
        val objectMapper = ObjectMapper()
        val backgroundColor: Color = Color(60, 63, 65)
        val selectedColor: Color = Color(13, 41, 62)
        val executeService: ScheduledExecutorService = Executors.newScheduledThreadPool(5)
    }
}