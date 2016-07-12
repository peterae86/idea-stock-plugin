package com.backkoms.stock.ui;

import javax.swing.*;

/**
 * Created by xiaorui.guo on 2016/7/12.
 */
public class StockWindow {
    private JPanel container;
    private JList stockList;
    private JTabbedPane stockTabs;

    public JPanel getContainer() {
        return container;
    }

    public JList getStockList() {
        return stockList;
    }

    public JTabbedPane getStockTabs() {
        return stockTabs;
    }
}
