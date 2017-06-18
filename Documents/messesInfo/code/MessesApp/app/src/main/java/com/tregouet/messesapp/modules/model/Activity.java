package com.tregouet.messesapp.modules.model;

import java.util.ArrayList;

/**
 * Created by mariececile.tregouet on 18/06/2017.
 */
public class Activity {

    private ArrayList<Schedule> masses;
    private ArrayList<Schedule> openings;
    private ArrayList<Schedule> confessions;
    private ArrayList<Schedule> receptions;
    private ArrayList<Schedule> adorations;
    private ArrayList<Schedule> praises;
    private ArrayList<Schedule> rosaries;

    public ArrayList<Schedule> getMasses() {
        return masses;
    }

    public void setMasses(ArrayList<Schedule> masses) {
        this.masses = masses;
    }

    public ArrayList<Schedule> getOpenings() {
        return openings;
    }

    public void setOpenings(ArrayList<Schedule> openings) {
        this.openings = openings;
    }

    public ArrayList<Schedule> getConfessions() {
        return confessions;
    }

    public void setConfessions(ArrayList<Schedule> confessions) {
        this.confessions = confessions;
    }

    public ArrayList<Schedule> getReceptions() {
        return receptions;
    }

    public void setReceptions(ArrayList<Schedule> receptions) {
        this.receptions = receptions;
    }

    public ArrayList<Schedule> getAdorations() {
        return adorations;
    }

    public void setAdorations(ArrayList<Schedule> adorations) {
        this.adorations = adorations;
    }

    public ArrayList<Schedule> getPraises() {
        return praises;
    }

    public void setPraises(ArrayList<Schedule> praises) {
        this.praises = praises;
    }

    public ArrayList<Schedule> getRosaries() {
        return rosaries;
    }

    public void setRosaries(ArrayList<Schedule> rosaries) {
        this.rosaries = rosaries;
    }
}
