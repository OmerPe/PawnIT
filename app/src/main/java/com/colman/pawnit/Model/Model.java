package com.colman.pawnit.Model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private List<Listing> listingData = new LinkedList<>();

    public List<Listing> getListingData() {
        return listingData;
    }

    public void setListingData(List<Listing> listingData) {
        this.listingData = listingData;
    }

    public void addListing(Listing listing){
        listingData.add(listing);
    }

}
