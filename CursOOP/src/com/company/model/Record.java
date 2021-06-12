package com.company.model;

import java.sql.Driver;
import java.util.Date;

public class Record {
    //Organization org=null;
   public Cars car;
    private int idRec;
    private double payment;
    private Date date;

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int id) {
        this.idRec = id;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Record(int idRec,String nameOrg, String numberC, double payment, Date date) {
        car=new Cars(nameOrg,numberC);
        this.idRec=idRec;
        this.payment = payment;
        this.date = date;
    }

}
