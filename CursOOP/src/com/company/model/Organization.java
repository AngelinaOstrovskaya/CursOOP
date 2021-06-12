package com.company.model;

public abstract class Organization {
    private int id;
    private String name;
    private String telephone;
    private double balance;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public double getBalance() {
        return balance;
    }


    public Organization( String name, String telephone, double balance) {
        this.name = name;
        this.telephone = telephone;
        this.balance = balance;
    }

    public Organization(String name) {
        this.name=name;

    }

    public String[] sb = new String[4];
    //Абстрактный метод вывода
    public abstract String[] getArray();
}
