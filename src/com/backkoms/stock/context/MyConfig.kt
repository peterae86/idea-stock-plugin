package com.backkoms.stock.context

import com.google.common.collect.Sets
import com.intellij.ide.util.PropertiesComponent

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
object MyConfig {
    private val stockCodeSet: MutableSet<String> = Sets.newConcurrentHashSet()
    private val pc: PropertiesComponent = PropertiesComponent.getInstance();

    fun save() {
        pc.setValues("stockCodeSet", stockCodeSet.toTypedArray())
    }

    fun load() {
        stockCodeSet.clear()
        stockCodeSet.addAll(pc.getValues("stockCodeSet") ?: emptyArray())
    }

    fun getAllStockCodes(): List<String> {
        return stockCodeSet.distinct()
    }

    fun addStockCode(stockCode: String) {
        stockCodeSet.add(stockCode)
        save()
    }

    fun removeStockCode(stockCode: String) {
        stockCodeSet.remove(stockCode)
        save()
    }
}