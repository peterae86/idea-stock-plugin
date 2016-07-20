package com.backkoms.stock.data.source.impl

import com.backkoms.stock.data.source.DataSource
import com.backkoms.stock.data.vo.StockData
import com.backkoms.stock.data.vo.StockInfo
import com.backkoms.stock.util.DateUtil
import com.backkoms.stock.util.HttpUtil
import com.google.common.base.Joiner
import okhttp3.Response
import org.apache.commons.lang.StringEscapeUtils
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
class TxDataSourceImpl : DataSource {
    val random = Random(System.currentTimeMillis())

    override fun queryHistory(stockCode: String): List<StockData> {
        var resp: Response? = null
        try {
            resp = HttpUtil.getSync("http://data.gtimg.cn/flashdata/hushen/minute/$stockCode.js?${random.nextDouble()}")
            var lines = resp.body().charStream().readLines();
            resp.close()
            return parseHistoryLines(lines)
        } finally {
            resp?.close()
        }
    }

    override fun queryStock(keyword: String): List<StockInfo> {
        var resp: Response? = null
        try {
            resp = HttpUtil.getSync("http://smartbox.gtimg.cn/s3/?v=2&q=${URLEncoder.encode(keyword, "utf-8")}&t=gp")
            var lines = resp.body().charStream().readLines();
            resp.close()
            return parseStockNames(lines)
        } finally {
            resp?.close()
        }
    }

    override fun queryRealTimeData(stockCodes: Collection<String>): List<StockData> {
        if (stockCodes.size == 0) {
            return emptyList()
        }
        var resp: Response? = null
        try {
            resp = HttpUtil.getSync("http://qt.gtimg.cn/q=" + Joiner.on(',').join(stockCodes));
            var lines = resp.body().charStream().readLines();
            return parseStockInfoLines(lines)
        } finally {
            resp?.close()
        }
    }

    private fun parseStockInfoLines(lines: List<String>): List<StockData> {
        val res: MutableList<StockData> = ArrayList()
        val format = SimpleDateFormat("yyyyMMddHHmm")
        lines.forEach {
            line ->
            val datas = line.split('~')
            val stockData = StockData()
            stockData.centralValue = datas[4].toDouble()
            stockData.maxValue = datas[47].toDouble()
            stockData.minValue = datas[48].toDouble()
            stockData.price = datas[3].toDouble()
            stockData.volume = datas[6].toLong()
            stockData.name = datas[1]
            stockData.time = format.parse(datas[30].substring(0, datas[30].length - 2))
            res.add(stockData)
        }
        return res
    }

    private fun parseHistoryLines(lines: List<String>): List<StockData> {
        if (lines.size < 2) {
            return emptyList()
        }
        var res: MutableList<StockData> = ArrayList()
        var today = LocalDateTime.now()
        lines.subList(2, lines.size - 1).forEach({ line ->
            var stockData = StockData()
            var empty0 = line.indexOf(' ', 0)
            var empty1 = line.indexOf(' ', empty0 + 1)
            var time = line.substring(0, empty0)
            stockData.time = DateUtil.localTimeToDate(today.withTime(time.substring(0, 2).toInt(), time.substring(2).toInt(), 0, 0))
            stockData.price = line.substring(empty0 + 1, empty1).toDouble()
            stockData.volume = line.substring(empty1 + 1, line.indexOf('\\', 0)).toLong()
            res.add(stockData)
        })
        return res
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