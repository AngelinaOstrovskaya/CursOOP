package com.company.model;

import java.util.Date;

public class Cars extends Organization{
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Cars(String number,String nameOrg) {
        super(nameOrg);
        this.number = number;

    }

    @Override
    public String[] getArray() {
        sb[0] = getName();
        sb[1] = getNumber();
       // sb[3] = Integer.toString(getPayment());
       // sb[4] = String.valueOf(getDate());
        return sb;
    }
}
