package com.colman.pawnit.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String userName, email;
    private Date dateOfBirth;
    private String profilePic;
    private List<Listing> listings = new LinkedList<>();
    private List<Pawn> pawns = new LinkedList<>();
    private List<Auction> auctions = new LinkedList<>();

    public User(){

    }

    public User(String name, Date birthDate, String email){
        this.userName = name;
        this.email = email;
        this.dateOfBirth = birthDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

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

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public void addListing(Listing listing){
        listings.add(listing);
    }

    public void addAuction(Auction auction){
        auctions.add(auction);
    }

    public void addPawn(Pawn pawn){
        pawns.add(pawn);
    }


}
