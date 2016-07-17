package com.backkoms.stock.ui.form;

import javax.swing.*;

/**
 * Created by test on 2016/7/17.
 */
public class SearchForm {
    private JTextField keyword;
    private JList searchResultList;
    private JPanel container;

    public JTextField getKeyword() {
        return keyword;
    }

    public JList getSearchResultList() {
        return searchResultList;
    }

    public JPanel getContainer() {
        return container;
    }
}
