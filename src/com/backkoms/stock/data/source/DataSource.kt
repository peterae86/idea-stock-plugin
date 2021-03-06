package com.backkoms.stock.data.source

import com.backkoms.stock.data.vo.StockData
import com.backkoms.stock.data.vo.StockInfo

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
interface DataSource {
    fun queryRealTimeData(stockCodes: Collection<String>): List<StockData>
    fun queryStock(keyword: String): List<StockInfo>
    fun queryHistory(stockCode: String): List<StockData>
}