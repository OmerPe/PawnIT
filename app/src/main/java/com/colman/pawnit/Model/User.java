package com.colman.pawnit.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User {
    private String userName, email;
    private Date dateOfBirth;
    private String profilePic;
    private List<Integer> resellListings = new LinkedList<>();
    private List<Integer> pawnListings = new LinkedList<>();
    private List<Integer> auctionListings = new LinkedList<>();

    final static String USER_NAME = "userName";
    final static String EMAIL = "email";
    final static String DATE_OF_BIRTH = "dateOfBirth";
    final static String PROFILE_PIC = "profilePic";
    final static String RESELL_LISTINGS = "resellListings";
    final static String PAWN_LISTINGS = "pawnListings";
    final static String AUCTION_LISTINGS = "auctionListings";

    public User() {

    }

    public User(String name, Date birthDate, String email) {
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

    public List<Integer> getResellListings() {
        return resellListings;
    }

    public void setResellListings(List<Integer> resellListings) {
        this.resellListings = resellListings;
    }

    public List<Integer> getPawnListings() {
        return pawnListings;
    }

    public void setPawnListings(List<Integer> pawnListings) {
        this.pawnListings = pawnListings;
    }

    public List<Integer> getAuctionListings() {
        return auctionListings;
    }

    public void setAuctionListings(List<Integer> auctionListings) {
        this.auctionListings = auctionListings;
    }

    public void addResellListing(ResellListing listing) {
        resellListings.add(listing.getListingID());
    }

    public void addAuctionListing(AuctionListing auctionListing) {
        auctionListings.add(auctionListing.getListingID());
    }

    public void addPawnListing(PawnListing pawn) {
        pawnListings.add(pawn.getListingID());
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USER_NAME, userName);
        json.put(EMAIL, email);
        json.put(DATE_OF_BIRTH, dateOfBirth.getTime());
        json.put(PROFILE_PIC, profilePic);
        json.put(RESELL_LISTINGS, resellListings.toString().substring(1, resellListings.size() - 1));
        json.put(AUCTION_LISTINGS, auctionListings.toString().substring(1, auctionListings.size() - 1));
        json.put(PAWN_LISTINGS, pawnListings.toString().substring(1, pawnListings.size() - 1));
        return json;
    }

    public static User create(Map<String, Object> json) {
        User user = new User();
        user.setUserName((String) json.get(USER_NAME));
        user.setEmail((String) json.get(EMAIL));
        user.setDateOfBirth(((Timestamp)json.get(DATE_OF_BIRTH)).toDate());
        user.setProfilePic((String)json.get(PROFILE_PIC));
        List<Integer> resellListings = new LinkedList<>();
        List<Integer> auctionListings = new LinkedList<>();
        List<Integer> pawnListings = new LinkedList<>();

        for (long id :
                (ArrayList<Long>)json.get(RESELL_LISTINGS)) {
            resellListings.add((int)(id));
        }
        for (long id :
                (ArrayList<Long>)json.get(AUCTION_LISTINGS)) {
            auctionListings.add((int)(id));
        }
        for (long id :
                (ArrayList<Long>)json.get(PAWN_LISTINGS)) {
            pawnListings.add((int)(id));
        }
        user.setResellListings(resellListings);
        user.setAuctionListings(auctionListings);
        user.setPawnListings(pawnListings);

        return user;
    }

}
