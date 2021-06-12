package com.company.db;

import com.company.model.*;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class DBWorker {
    public static Connection pub;
    public static Statement state; //используется для выполнения SQL-запросов
    public static ResultSet result;
    public static RecordList Reclist=new RecordList();

    private static Properties connectionProperties = new Properties();

    public static RecordList getArr() {
        return Reclist;
    }
    public static void connectionBD() throws ClassNotFoundException, SQLException {
        pub = null;
        Class.forName("org.sqlite.JDBC");  //("имя движка") вызывает динамическую загрузку класса, org.sqlite.JDBC принадлежит к jar sqlite-jdbc

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        connectionProperties = config.toProperties();
        pub = DriverManager.getConnection("jdbc:sqlite:dataNew21.s3db",connectionProperties); //("протокол:движок:имя_файла_БД")находит java.sql.Driver соответствующей базы данных и вызывает у него метод connect (метод connect всегда создает базу данных заранее)
        System.out.println("БД подключена!");
    }
    //cоздание таблицы БД
    public static void newTable() throws ClassNotFoundException, SQLException {
        //создание экземпляра класса Statement
        state = pub.createStatement();
        state.execute("CREATE TABLE if not exists 'Record' ('id' INTEGER PRIMARY KEY AUTOINCREMENT , 'idOrg' VARCHAR, 'number_car' VARCHAR, 'pay' DOUBLE,'date' DATE, FOREIGN KEY (idOrg) REFERENCES Organization(name) ON DELETE CASCADE); ");
       state.execute("CREATE TABLE if not exists 'Organization' ( 'name' VARCHAR PRIMARY KEY, 'telephone' VARCHAR, 'balance' DOUBLE); ");
        state.execute("CREATE TABLE if not exists 'Cars' ('number' VARCHAR PRIMARY KEY, 'nameOrg' VARCHAR ); ");

        System.out.println("Таблица существует");
    }

    public static boolean checkOrg(String id){
        try {
            PreparedStatement prep1 = pub.prepareStatement("SELECT name FROM Organization WHERE name=?");
            prep1.setString(1, id);
            result = prep1.executeQuery();
            System.out.println(result.getInt("name"));
            return true;
        }
        catch (SQLException ex ){
            return false;
        }
    }

    public static boolean checkCar(String number){
        try {
            PreparedStatement prep1 = pub.prepareStatement("SELECT number FROM Cars WHERE number=?");
            prep1.setString(1, number);
            result = prep1.executeQuery();
            System.out.println(result.getInt("number"));
            return true;
        }
        catch (SQLException ex ){
            return false;
        }
    }
     // заполнение таблицы БД
    public static int writeDB(String idOrg, String number, double pay,Date date) throws SQLException {


            //PreparedStatemen prep = pub.prepareStatement( "INSERT INTO 'Record' ('idOrg','driver_FIO', 'number_car','pay','date') VALUES (?, ?, ?, ?,?);");
            PreparedStatement prep = pub.prepareStatement("INSERT INTO 'Record' ('idOrg', 'number_car','pay','date') VALUES (?, ?, ?, ?) ;");

            prep.setString(1, idOrg);
            prep.setString(2, number);
            prep.setDouble(3, pay);
            prep.setDate(4, date);
            prep.execute();

        result = state.executeQuery("SELECT * FROM Record ORDER BY id DESC LIMIT 1");
        return result.getInt("id");



    }
    // заполнение таблицы БД
    public static void updateDB(int id,String idOrg, String number, double pay,Date date) throws SQLException {


        PreparedStatement prep = pub.prepareStatement("UPDATE Record SET idOrg=?, number_car=?, pay=?, date=? WHERE id = ?");
        prep.setString(1, idOrg);
        prep.setString(2, number);
        prep.setDouble(3, pay);
        prep.setDate(4,date);
        prep.setInt(5, id);
        prep.execute();

        System.out.println("Запись изменена");
    }
    // вывод данных из таблицы
    public static void readDB() throws ClassNotFoundException, SQLException {

        Record rec;
        result = state.executeQuery("SELECT * FROM Record"); //выборки данных с помощью команды SELECT
        while (result.next()) {
            rec= new Record(result.getInt("id"),result.getString("idOrg"),result.getString("number_car"),result.getInt("pay"),result.getDate("date"));
            Reclist.Rec.add(rec);
        }

        System.out.println("Таблица выгружена");
    }

    public static void delete(int id) throws SQLException {
        PreparedStatement del = pub.prepareStatement("DELETE FROM Record WHERE id = ?");
        del.setObject(1, id);
        del.execute();
        System.out.println("Запись удалена"+id);
    }

    public static void filter( Date date1, Date date2) throws SQLException {
        Record rec;
        PreparedStatement fltr = pub.prepareStatement("SELECT * FROM Record WHERE date BETWEEN ? AND ?");
        fltr.setDate(1, date1);
        fltr.setDate(2, date2);
        result = fltr.executeQuery();

        rec= new Record(result.getInt("id"),result.getString("idOrg"),result.getString("number_car"),result.getInt("pay"),result.getDate("date"));

        Reclist.Rec.add(rec);

        System.out.println("Таблица отфильтрована");
    }
    public static ArrayList<Record> excel(String nameOrg, Date date1, Date date2) throws SQLException {
        Record rec;
        ArrayList<Record> data=new ArrayList<Record>();
        PreparedStatement fltr = pub.prepareStatement("SELECT * FROM Record WHERE idOrg=? AND date BETWEEN ? AND ?");
        fltr.setString(1, nameOrg);
        fltr.setDate(2, date1);
        fltr.setDate(3, date2);
        result = fltr.executeQuery();
        while (result.next()) {
            rec = new Record(result.getInt("id"), result.getString("idOrg"), result.getString("number_car"), result.getInt("pay"), result.getDate("date"));
            data.add(rec);
            //Reclist.Rec.add(rec);
        }

        System.out.println("Запрос для экселя");
        return  data;
    }
    //закрытие БД
    public static void closeDB() throws ClassNotFoundException, SQLException {
        result.close();
        state.close();
        pub.close();

        System.out.println("Соединения закрыты");
    }
}
