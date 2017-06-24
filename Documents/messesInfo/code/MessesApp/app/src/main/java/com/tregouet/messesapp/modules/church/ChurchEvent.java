package com.tregouet.messesapp.modules.church;

import com.tregouet.messesapp.model.Church;
import com.tregouet.messesapp.model.SearchResult;

import java.util.ArrayList;

/**
 * Created by mariececile.tregouet on 24/06/2017.
 */
public class ChurchEvent {

    private Church church = new Church();

    public ChurchEvent(Church church) {
        this.church = church;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }
}
