package com.company.db;

import com.company.db.DBWorker;
import com.company.model.OrgList;
import com.company.model.Organization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.company.db.DBWorker.pub;

public class DBWorkerOrg {
    public static ResultSet result;
    public static OrgList Orglist=new OrgList();

    public static OrgList getArr() {

        return Orglist;
    }

    public static void writeDB(String name, String telephone, double balance) throws SQLException {

        PreparedStatement prep = pub.prepareStatement("INSERT INTO 'Organization' ('name','telephone', 'balance') VALUES (?, ?, ?);");
        prep.setString(1, name);
        prep.setString(2, telephone);
        prep.setDouble(3, balance);

        prep.execute();

       // result = state.executeQuery("SELECT * FROM Record ORDER BY id DESC LIMIT 1");
       // return result.getInt("id");



    }

    // вывод данных из таблицы
    public static void readDB() throws ClassNotFoundException, SQLException {

        Organization org;
        result = DBWorker.state.executeQuery("SELECT * FROM Organization"); //выборки данных с помощью команды SELECT
        while (result.next()) {
            //client=new Cars(result.getInt("idOrg"),result.getString("name"),result.getString(""));
            org= new Organization(result.getString("name"), result.getString("telephone"), result.getInt("balance")) {
                @Override
                public String[] getArray() {
                    return new String[0];
                }
            };

            //pubList.Publications.add(publication);

            Orglist.Organizations.add(org);
        }

        System.out.println("Таблица выгружена");
    }
    public static void delete(String name) throws SQLException {
        PreparedStatement del = pub.prepareStatement("DELETE FROM Organization WHERE name = ?");
        del.setObject(1, name);
        del.execute();
        System.out.println("Запись удалена"+name);
    }

    public static void updateDB(String name, String telephone, double balance) throws SQLException {


        PreparedStatement prep = pub.prepareStatement("UPDATE Organization SET name=?, telephone=?, balance=? WHERE name = ?");
        prep.setString(1, name);
        prep.setString(2, telephone);
        prep.setDouble(3, balance);
        prep.setString(4, name);
        prep.execute();

        System.out.println("Запись изменена");
    }


    public static ArrayList<String> cbselect() throws ClassNotFoundException, SQLException {

        ArrayList<String> list = new ArrayList<>();
        result = DBWorker.state.executeQuery("SELECT name FROM Organization"); //выборки данных с помощью команды SELECT
        while (result.next()) {

            list.add(result.getString("name"));

            //pubList.Publications.add(publication);

        }

        System.out.println("Таблица выгружена");
        return list;
    }
}
