package com.backkoms.stock

import com.backkoms.stock.context.Common
import com.backkoms.stock.context.GlobalConfig
import com.backkoms.stock.ui.view.StockView
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory

/**
 * https://wizardforcel.gitbooks.io/jfreechart-dev-guide/content/7.html
 * Created by test on 2016/7/10.
 */
class MyWindow : ToolWindowFactory {

    private val stockView = StockView()

    init {
        GlobalConfig.getAllStockCodes().forEach {
            x ->
            stockView.initStock(x, GlobalConfig.getStockName(x))
        }
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        var contentFactory: ContentFactory = ContentFactory.SERVICE.getInstance();
        var content: Content = contentFactory.createContent(stockView.container, "", false);
        toolWindow.contentManager.addContent(content);
    }
}