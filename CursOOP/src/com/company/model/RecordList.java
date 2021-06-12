package com.company.model;

import java.util.ArrayList;

public class RecordList {
    public ArrayList<Record> Rec = new ArrayList<Record>();

    public int getCount(){

        return this.Rec.size();
    }

    public void clear() {
        this.Rec.clear();
    }

    public Record getPecord(int index){
        return Rec.get(index);
    }

}
