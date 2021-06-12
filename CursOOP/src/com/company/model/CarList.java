package com.company.model;

import java.util.ArrayList;

public class CarList {
    public ArrayList<Cars> Cars = new ArrayList<Cars>();
    public int getCount(){

        return this.Cars.size();
    }
    public void clear() {
        this.Cars.clear();
    }
}
