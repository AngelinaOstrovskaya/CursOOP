package com.company.model;

import com.company.db.DBWorker;
import com.company.db.DBWorkerCars;
import com.company.db.DBWorkerOrg;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class CarTableModel extends AbstractTableModel {
    public static  CarList data=new CarList();

    public CarTableModel(CarList car) {
        try{
            DBWorker.connectionBD();
            DBWorker.newTable();
            //connectDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.data=car;
    }
    public static void connectDB(){
        data.clear();
        try {
            DBWorkerCars.readDB();
            data = DBWorkerCars.getArr();
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
        return 2;
    }
    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Номер";
            case 1: return "ID организации";
        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cars car= data.Cars.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return car.getNumber();
            case 1:
                return car.getName();
        }
        return "default";
    }
}
