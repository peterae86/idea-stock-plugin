package com.backkoms.stock.data

import com.backkoms.stock.context.MyConfig
import com.backkoms.stock.data.vo.StockData
import com.backkoms.stock.data.vo.StockInfo
import com.backkoms.stock.data.source.DataSource
import com.backkoms.stock.data.source.impl.EmptyDataSourceImpl
import com.backkoms.stock.util.DateUtil
import com.backkoms.stock.util.HttpUtil
import com.google.common.base.Joiner
import com.google.common.collect.Sets
import com.intellij.ide.util.PropertiesComponent
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.apache.commons.lang.StringEscapeUtils
import org.apache.commons.lang.time.FastDateFormat
import java.io.IOException
import java.io.StreamTokenizer
import java.io.StringReader
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.swing.JPanel

/**
 * Created by test on 2016/7/16.
 */
object RealTimeStockData : DataSource {
    val listeners: MutableSet<(StockData: StockData) -> Unit> = Sets.newConcurrentHashSet()
    val executeService = Executors.newScheduledThreadPool(5)
    @Volatile var realTimeDataListener: (stockData: StockData) -> Unit = {}

    var dataSourceDelegate: DataSource = EmptyDataSourceImpl()


    init {
        executeService.scheduleAtFixedRate({
            try {
                var res = queryRealTimeData(MyConfig.getAllStockCodes())
                res.forEach {
                    x ->
                    realTimeDataListener.invoke(x)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }, 10, 20, TimeUnit.SECONDS)
    }

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





