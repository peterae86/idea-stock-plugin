package com.backkoms.stock.data.source.impl;

import com.backkoms.stock.data.vo.StockData
import com.backkoms.stock.data.vo.StockInfo
import com.backkoms.stock.data.source.DataSource

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
class EmptyDataSourceImpl : DataSource {
    override fun queryStock(keyword: String): List<StockInfo> {
        return emptyList()
    }

    override fun queryRealTimeData(stockCodes: Collection<String>): List<StockData> {
        return emptyList()
    }

    override fun queryHistory(stockCode: String): List<StockData> {
        return emptyList()
    }
}
