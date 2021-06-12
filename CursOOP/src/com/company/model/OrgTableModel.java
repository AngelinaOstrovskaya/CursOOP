package com.company.model;

import com.company.db.DBWorker;
import com.company.db.DBWorkerOrg;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class OrgTableModel extends AbstractTableModel {
    public static  OrgList data=new OrgList();

    public OrgTableModel(OrgList org) {
        try{
            DBWorker.connectionBD();
            DBWorker.newTable();
            //connectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.data=org;
    }
    public static void connectDB(){
        data.clear();
        try {
            DBWorkerOrg.readDB();
            data = DBWorkerOrg.getArr();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return data.getCount();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Название организации";
            case 1: return "Номер телефона";
            case 2: return "Расчетный счет";
        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Organization org= data.Organizations.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return org.getName();
            case 1: {
                return org.getTelephone();
            }
            case 2: {
                return org.getBalance();
            }
        }
        return "default";
    }
}
