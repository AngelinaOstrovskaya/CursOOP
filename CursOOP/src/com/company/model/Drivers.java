package com.company.model;

public class Drivers extends Organization{
    private int pasport;
    private String fio;

    public int getPasport() {
        return pasport;
    }

    public String getFIO() {
        return fio;
    }

    public Drivers(int pasport, String name,String nameOrg) {
        super(nameOrg);
        this.fio = name;
        this.pasport = pasport;
    }
    public Drivers(String nameOrg, String name) {
        super(nameOrg);
        this.fio = name;
    }
    @Override
    public String[] getArray() {
        sb[0] = String.valueOf(getPasport());
        sb[1] = getFIO();
        sb[2] = getName();
        return sb;
    }
}
