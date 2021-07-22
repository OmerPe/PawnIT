package com.colman.pawnit.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User {
    private String Uid;
    private String userName, email;
    private Date dateOfBirth;
    private String profilePic;
    private List<String> resellListings = new LinkedList<>();
    private List<String> pawnListings = new LinkedList<>();
    private List<String> auctionListings = new LinkedList<>();

    final static String USER_NAME = "userName";
    final static String EMAIL = "email";
    final static String DATE_OF_BIRTH = "dateOfBirth";
    final static String PROFILE_PIC = "profilePic";
    final static String RESELL_LISTINGS = "resellListings";
    final static String PAWN_LISTINGS = "pawnListings";
    final static String AUCTION_LISTINGS = "auctionListings";
    final static String UID = "Uid";

    public User() {

    }

    public User(String Uid, String name, Date birthDate, String email) {
        this.userName = name;
        this.email = email;
        this.dateOfBirth = birthDate;
        this.Uid = Uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
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

    public List<String> getResellListings() {
        return resellListings;
    }

    public void setResellListings(List<String> resellListings) {
        this.resellListings = resellListings;
    }

    public List<String> getPawnListings() {
        return pawnListings;
    }

    public void setPawnListings(List<String> pawnListings) {
        this.pawnListings = pawnListings;
    }

    public List<String> getAuctionListings() {
        return auctionListings;
    }

    public void setAuctionListings(List<String> auctionListings) {
        this.auctionListings = auctionListings;
    }

    public void addResellListing(String listing) {
        resellListings.add(listing);
    }

    public void addAuctionListing(String auctionListing) {
        auctionListings.add(auctionListing);
    }

    public void addPawnListing(String pawn) {
        pawnListings.add(pawn);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USER_NAME, userName);
        json.put(EMAIL, email);
        json.put(DATE_OF_BIRTH, dateOfBirth);
        json.put(PROFILE_PIC, profilePic);
        json.put(RESELL_LISTINGS, resellListings);
        json.put(AUCTION_LISTINGS, auctionListings);
        json.put(PAWN_LISTINGS, pawnListings);
        json.put(UID, Uid);
        return json;
    }

    public static User create(Map<String, Object> json) {
        User user = new User();
        user.setUserName((String) json.get(USER_NAME));
        user.setEmail((String) json.get(EMAIL));
        user.setDateOfBirth(((Timestamp) json.get(DATE_OF_BIRTH)).toDate());
        user.setProfilePic((String) json.get(PROFILE_PIC));
        user.setUid((String) json.get(UID));

        user.setResellListings((ArrayList<String>) json.get(RESELL_LISTINGS));
        user.setAuctionListings((ArrayList<String>) json.get(AUCTION_LISTINGS));
        user.setPawnListings((ArrayList<String>) json.get(PAWN_LISTINGS));

        return user;
    }

}
