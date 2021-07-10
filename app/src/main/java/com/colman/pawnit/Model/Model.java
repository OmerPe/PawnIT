package com.colman.pawnit.Model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private List<Listing> listings = new LinkedList<>();
    private List<Pawn> pawns = new LinkedList<>();

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public void addListing(Listing listing){
        listings.add(listing);
    }

    public void addPawn(Pawn pawn){
        pawns.add(pawn);
    }


}
