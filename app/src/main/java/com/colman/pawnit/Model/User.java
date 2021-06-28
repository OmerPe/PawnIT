package com.colman.pawnit.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User {
    public String userName, email;
    public Date dateOfBirth;
    public String profilePic;
    private List<Listing> listings;
    private List<Pawn> pawns;
    private List<Auction> auctions;

    public User(){
        listings = new LinkedList<>();
        pawns = new LinkedList<>();
        auctions = new LinkedList<>();
    }

    public User(String name, Date birthDate, String email){
        this.userName = name;
        this.email = email;
        this.dateOfBirth = birthDate;

        listings = new LinkedList<>();
        pawns = new LinkedList<>();
        auctions = new LinkedList<>();
    }

}
