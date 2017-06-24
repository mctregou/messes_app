package com.tregouet.messesapp.model;

/**
 * Created by mariececile.tregouet on 18/06/2017.
 */
public class SearchResult {

    private int id;
    private Church church;
    private Schedule schedule;

    public SearchResult(int id, Church church, Schedule schedule) {
        this.id = id;
        this.church = church;
        this.schedule = schedule;
    }

    public SearchResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
