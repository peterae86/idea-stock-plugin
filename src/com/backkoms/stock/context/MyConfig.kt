package com.backkoms.stock.context

import com.fasterxml.jackson.core.type.TypeReference
import com.intellij.ide.util.PropertiesComponent
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
object MyConfig {
    private val stockCodeNameMap: MutableMap<String, String> = ConcurrentHashMap()
    private val pc: PropertiesComponent = PropertiesComponent.getInstance();

    fun save() {
        pc.setValue("stockCodeSet", Global.objectMapper.writeValueAsString(stockCodeNameMap))
    }

    fun load() {
        stockCodeNameMap.clear()
        try {
            stockCodeNameMap.putAll(Global.objectMapper.readValue(pc.getValue("stockCodeSet") ?: "{}", object : TypeReference<Map<String, String>>() {}))
        } catch(e: Exception) {
            pc.setValue("stockCodeSet", null)
        }
    }

    fun getAllStockCodes(): List<String> {
        return stockCodeNameMap.keys.distinct()
    }

    fun addStockCode(stockCode: String, stockName: String) {
        stockCodeNameMap.put(stockCode, stockName)
        save()
    }

    fun removeStockCode(stockCode: String) {
        stockCodeNameMap.remove(stockCode)
        save()
    }

    fun hasStock(stockCode: String): Boolean {
        return stockCodeNameMap.containsKey(stockCode)
    }

    fun getStockName(stockCode: String): String {
        return stockCodeNameMap[stockCode]!!
    }
}