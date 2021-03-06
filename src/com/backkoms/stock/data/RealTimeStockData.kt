package com.backkoms.stock.data

import com.backkoms.stock.context.Common
import com.backkoms.stock.context.GlobalConfig
import com.backkoms.stock.data.source.DataSource
import com.backkoms.stock.data.source.impl.EmptyDataSourceImpl
import com.backkoms.stock.data.vo.StockData
import com.backkoms.stock.data.vo.StockInfo
import com.google.common.collect.Sets
import java.util.concurrent.TimeUnit

/**
 * Created by test on 2016/7/16.
 */
class RealTimeStockData{
    init {
        Common.executeService.scheduleAtFixedRate({
            try {
                var res = queryRealTimeData(GlobalConfig.getAllStockCodes())
                res.forEach {
                    x ->
                    realTimeDataListener.invoke(x)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }, 10, 10, TimeUnit.SECONDS)
    }

    private constructor()

    companion object : DataSource  {
        val listeners: MutableSet<(StockData: StockData) -> Unit> = Sets.newConcurrentHashSet()

        @Volatile var realTimeDataListener: (stockData: StockData) -> Unit = {}

        var dataSourceDelegate: DataSource = EmptyDataSourceImpl()

        var data:RealTimeStockData = RealTimeStockData()

        fun addRealTimeDataListener(realTimeDataListener: (stockData: StockData) -> Unit) {
            this.realTimeDataListener = realTimeDataListener
        }


        fun registerStockCode(stockCode: String, initialDataHandler: (data: StockData, sampleHistory: List<StockData>) -> Unit) {
            initialDataHandler.invoke(queryRealTimeData(arrayListOf(stockCode))[0], queryHistory(stockCode))
        }


        fun addListener(listener: (StockData: StockData) -> Unit) {
            listeners.add(listener)
        }

        fun removeListener(listener: (StockData: StockData) -> Unit) {
            listeners.remove(listener)
        }

        fun setDataSource(dataSource: DataSource) {
            this.dataSourceDelegate = dataSource
        }

        override fun queryStock(keyword: String): List<StockInfo> {
            return dataSourceDelegate.queryStock(keyword)
        }

        override fun queryHistory(stockCode: String): List<StockData> {
            return dataSourceDelegate.queryHistory(stockCode)
        }

        override fun queryRealTimeData(stockCodes: Collection<String>): List<StockData> {
            return dataSourceDelegate.queryRealTimeData(stockCodes)
        }
    }
}





