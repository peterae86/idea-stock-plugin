package com.backkoms.stock;

import com.backkoms.stock.context.Common
import com.backkoms.stock.context.GlobalConfig
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
        GlobalConfig.save()
    }

    override fun initComponent() {
        GlobalConfig.load()
        RealTimeStockData.dataSourceDelegate = TxDataSourceImpl()
    }
}
