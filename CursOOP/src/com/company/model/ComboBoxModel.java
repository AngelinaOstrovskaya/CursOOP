package com.company.model;

import com.company.db.DBWorkerOrg;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComboBoxModel extends AbstractListModel implements javax.swing.ComboBoxModel {
    private ArrayList<String> data = new ArrayList<String>();
    String selection = null;
    public ComboBoxModel() {
        setData();
    }

    public void setData() {
        try {
            // Очистка коллекции
            data.clear();

            data= DBWorkerOrg.cbselect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public void setSelectedItem(Object anItem) {
        selection = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Object getElementAt(int index) {
        return data.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
