package com.tregouet.messesapp.modules.model;

import java.util.ArrayList;

/**
 * Created by mariececile.tregouet on 18/06/2017.
 */
public class Schedule {

    private String day;
    private ArrayList<Integer> hours;

    public Schedule(String day, ArrayList<Integer> hours) {
        this.day = day;
        this.hours = hours;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<Integer> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Integer> hours) {
        this.hours = hours;
    }
}
