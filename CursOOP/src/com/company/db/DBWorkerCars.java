package com.company.db;

import com.company.model.CarList;
import com.company.model.Cars;
import com.company.model.OrgList;
import com.company.model.Organization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.company.db.DBWorker.pub;

public class DBWorkerCars {
    public static ResultSet result;
    public static CarList CarList=new CarList();

    public static CarList getArr() {
        return CarList;
    }

    public static void writeDB(String number, String id) throws SQLException {

        PreparedStatement prep = pub.prepareStatement("INSERT INTO 'Cars' ('number','nameOrg') VALUES (?, ?);");
        prep.setString(1, number);
        prep.setString(2, id);

        prep.execute();

    }

    // вывод данных из таблицы
    public static void readDB() throws ClassNotFoundException, SQLException {

        Cars car;
        result = DBWorker.state.executeQuery("SELECT * FROM Cars"); //выборки данных с помощью команды SELECT
        while (result.next()) {
            //client=new Cars(result.getInt("idOrg"),result.getString("name"),result.getString(""));
            car= new Cars(result.getString("number"), result.getString("nameOrg")) {
                @Override
                public String[] getArray() {
                    return new String[0];
                }
            };

            CarList.Cars.add(car);
        }

        System.out.println("Таблица выгружена");
    }
    public static void delete(String number) throws SQLException {
        PreparedStatement del = pub.prepareStatement("DELETE FROM Cars WHERE number = ?");
        del.setString(1, number);
        del.execute();
        System.out.println("Запись удалена"+number);
    }

    public static void updateDB(String num,String number,String idOrg) throws SQLException {

        PreparedStatement prep = pub.prepareStatement("UPDATE Cars SET number=?, nameOrg=? WHERE number = ?");
        prep.setString(1, number);
        prep.setString(2, idOrg);
        prep.setString(3, num);
        prep.execute();

        System.out.println("Запись изменена");
    }
}
