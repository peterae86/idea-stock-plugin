package com.backkoms.stock.ui.form;

import javax.swing.*;

/**
 * Created by xiaorui.guo on 2016/7/12.
 */
public class StockWindowForm {
    private JPanel container;
    private JList stockList;
    private JTabbedPane stockTabs;
    private JButton addButton;
    private JButton deleteButton;

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
