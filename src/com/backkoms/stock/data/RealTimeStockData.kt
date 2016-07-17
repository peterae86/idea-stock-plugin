package com.backkoms.stock.data

import com.backkoms.stock.util.DateUtil
import com.backkoms.stock.util.HttpUtil
import com.google.common.collect.Sets
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.apache.commons.lang.StringEscapeUtils
import java.io.IOException
import java.io.StreamTokenizer
import java.io.StringReader
import java.net.URLEncoder
import java.time.LocalDate
import java.util.*

/**
 * Created by test on 2016/7/16.
 */
object RealTimeStockData {
    val stockCodeSet: MutableSet<String> = Sets.newConcurrentHashSet()
    val listeners: MutableSet<PriceDataListener> = Sets.newConcurrentHashSet()
    val random = Random()

    fun registerStockCode(stockCode: String, initialDataHandler: InitialDataHandler) {
        var stockInfoResponse = HttpUtil.getSync("http://qt.gtimg.cn/q=" + stockCode)
        if (!stockInfoResponse.isSuccessful) {
            throw RuntimeException()
        }
        var todayHistoryResponse = HttpUtil.getSync(String.format("http://data.gtimg.cn/flashdata/hushen/minute/%s.js?%s", stockCode, random.nextDouble()))
        var stockInfoLines = stockInfoResponse.body().charStream().readLines()
        var todayHistoryLines = todayHistoryResponse.body().charStream().readLines()
        initialDataHandler.handleInitialData(parseStockInfoLines(stockInfoLines), parseHistoryLines(todayHistoryLines))
        stockCodeSet.add(stockCode)
    }


    fun parseStockInfoLines(lines: List<String>): StockData {
        var line = lines[0]
        var datas = line.split('~')
        var res = StockData()
        res.centralValue = datas[4].toDouble()
        res.maxValue = datas[47].toDouble()
        res.minValue = datas[48].toDouble()
        return res
    }

    fun parseHistoryLines(lines: List<String>): List<StockData> {
        var res: MutableList<StockData> = ArrayList()
        var today = LocalDate.now()
        var lastVolume = 0L
        lines.subList(2, lines.size - 1).forEach({ line ->
            var stockData = StockData()
            var empty0 = line.indexOf(' ', 0)
            var empty1 = line.indexOf(' ', empty0 + 1)
            var time = line.substring(0, empty0)
            stockData.time = DateUtil.localTimeToDate(today.atTime(time.substring(0, 2).toInt(), time.substring(2).toInt()))
            stockData.price = line.substring(empty0 + 1, empty1).toDouble()
            stockData.volume = line.substring(empty1 + 1, line.indexOf('\\', 0)).toLong() - lastVolume
            lastVolume = line.substring(empty1 + 1, line.indexOf('\\', 0)).toLong()
            res.add(stockData)
        })
        return res
    }

    fun removeStockCode(stockCode: String) {
        stockCodeSet.remove(stockCode)
    }

    fun addListener(listener: PriceDataListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: PriceDataListener) {
        listeners.remove(listener)
    }

    fun queryStock(keyword: String): List<StockInfo> {
        var resp = HttpUtil.getSync(String.format("http://smartbox.gtimg.cn/s3/?v=2&q=%s&t=gp", URLEncoder.encode(keyword, "utf-8")))
        var lines = resp.body().charStream().readLines();
        return parseStockNames(lines)
    }


    private fun parseStockNames(lines: List<String>): List<StockInfo> {
        var line = lines[0]
        if (line.equals("v_hint=\"N\";")) {
            return emptyList()
        }
        var datas = line.substring(8, line.length - 1).split('^')
        var res: MutableList<StockInfo> = ArrayList()
        for (dataStr in datas) {
            var params = dataStr.split('~')
            var info = StockInfo()
            info.code = params[0] + params[1]
            info.name = StringEscapeUtils.unescapeJava(params[2])
            info.pinyin = params[3]
            res.add(info)
        }
        return res
    }
}

interface PriceDataListener {
    fun handleRealtimeData(data: StockData)
}

interface InitialDataHandler {
    fun handleInitialData(data: StockData, sampleHistory: List<StockData>)
}


class StockData {
    lateinit var time: Date
    var price: Double = 0.0
    var volume: Long = 0
    var centralValue: Double = 0.0
    var maxValue: Double = 0.0
    var minValue: Double = 0.0
}

class StockInfo {
    lateinit var name: String
    lateinit var code: String
    lateinit var pinyin: String
}

