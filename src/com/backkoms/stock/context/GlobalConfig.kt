package com.backkoms.stock.context

import com.fasterxml.jackson.core.type.TypeReference
import com.intellij.ide.util.PropertiesComponent
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
class GlobalConfig {
    var stockCodeNameMap: MutableMap<String, String> = ConcurrentHashMap()

    private constructor() {

    }

    companion object {
        private val pc: PropertiesComponent = PropertiesComponent.getInstance()
        var config = GlobalConfig()

        fun save() {
            println(Common.objectMapper.writeValueAsString(config))
            pc.setValue("config", Common.objectMapper.writeValueAsString(config))
        }

        fun load() {
            try {
                config = (Common.objectMapper.readValue(pc.getValue("config") ?: "{}", GlobalConfig.config.javaClass))
            } catch(e: Exception) {
                pc.setValue("config", null)
            }
        }

        fun getAllStockCodes(): List<String> {
            return config.stockCodeNameMap.keys.distinct()
        }

        @Synchronized fun addStockCode(stockCode: String, stockName: String) {
            if (config.stockCodeNameMap.size > 10) {
                return
            }
            config.stockCodeNameMap.put(stockCode, stockName)
            save()
        }

        fun removeStockCode(stockCode: String) {
            config.stockCodeNameMap.remove(stockCode)
            save()
        }

        fun hasStock(stockCode: String): Boolean {
            return config.stockCodeNameMap.containsKey(stockCode)
        }

        fun getStockName(stockCode: String): String {
            return config.stockCodeNameMap[stockCode]!!
        }
    }
}