package com.company.model;

import com.company.db.DBWorker;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.sql.Date;

public class RecordTableModel extends AbstractTableModel {
    public static  RecordList data=new RecordList();

    public RecordTableModel(RecordList rec) {
        try{
            DBWorker.connectionBD();
            DBWorker.newTable();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.data=rec;
    }

    public static void connectDB(){
        data.clear();
        try {
            DBWorker.readDB();
            data = DBWorker.getArr();
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
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Id";
            case 1: return "Название организации";
            case 2: return "Номер машины";
            case 3: return "Сумма оплаты";
            case 4: return "Дата оплаты";
        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Record rec= data.Rec.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rec.getIdRec();
            case 1:
                return rec.car.getName();
            case 2: {

                return rec.car.getNumber();
            }
            case 3: {

                return rec.getPayment();
            }
            case 4: {
                return rec.getDate();
            }
        }
        return "default";
    }

    public static void filterDB( Date date1,Date date2 ){
        data.Rec.clear();
        try {

            DBWorker.filter( date1,date2);
            data = DBWorker.getArr();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
