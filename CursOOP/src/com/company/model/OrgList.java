package com.company.model;

import java.util.ArrayList;

public class OrgList {
    public ArrayList<Organization> Organizations = new ArrayList<Organization>();
    public int getCount(){

        return this.Organizations.size();
    }
    public void clear() {
        this.Organizations.clear();
    }
}


