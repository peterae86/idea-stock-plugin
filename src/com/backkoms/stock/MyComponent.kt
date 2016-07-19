package com.backkoms.stock;

import com.backkoms.stock.context.Global
import com.backkoms.stock.context.MyConfig
import com.backkoms.stock.data.RealTimeStockData
import com.backkoms.stock.data.source.impl.TxDataSourceImpl
import com.intellij.openapi.components.ApplicationComponent

/**
 * Created by xiaorui.guo on 2016/7/19.
 */
class MyComponent : ApplicationComponent {
    override fun getComponentName(): String {
        return "aaa"
    }

    override fun disposeComponent() {
        MyConfig.save()
    }

    override fun initComponent() {
        MyConfig.load()
        RealTimeStockData.dataSourceDelegate = TxDataSourceImpl()
        MyConfig.getAllStockCodes().forEach {
            x ->
            Global.stockView.initStock(x, MyConfig.getStockName(x))
        }
    }
}
