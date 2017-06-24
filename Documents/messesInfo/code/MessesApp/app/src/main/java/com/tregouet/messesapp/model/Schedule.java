package com.tregouet.messesapp.model;

import java.util.ArrayList;

/**
 * Created by mariececile.tregouet on 18/06/2017.
 */
public class Schedule {

    private String day;
    private ArrayList<Integer> hours;
    private String description;

    public Schedule(String day, ArrayList<Integer> hours) {
        this.day = day;
        this.hours = hours;
    }

    public Schedule(String day, ArrayList<Integer> hours, String description) {
        this.day = day;
        this.hours = hours;
        this.description = description;
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

    public String getStringHours() {
        String result = "";
        for (int i=0; i<hours.size(); i++){
            if (i!=0){
                result += ", ";
            }
            result += hours.get(i) + "h";
        }
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
