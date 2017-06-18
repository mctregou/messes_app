package com.tregouet.messesapp.modules.model;

import java.util.ArrayList;

/**
 * Created by mariececile.tregouet on 18/06/2017.
 */
public class Group {

    private String name;
    private String description;
    private ArrayList<Schedule> schedules;

    public Group(String name, String description, ArrayList<Schedule> schedules) {
        this.name = name;
        this.description = description;
        this.schedules = schedules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }
}
