package com.colman.pawnitv2.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "Offer_table")
public class Offer {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Uid;
    private int pawnListingID;
    private double amount;
    private double interestRate;
    private Date dateToStart;
    private boolean accepted;
    private int numOfPayments;
    private String dayOfPayment;

    final static String ID = "id";
    final static String UID = "Uid";
    final static String PAWN_LISTING_ID = "pawnListingID";
    final static String AMOUNT = "amount";
    final static String INTEREST_RATE = "interestRate";
    final static String STARTING_DATE = "dateToStart";
    final static String ACCEPTED = "accepted";
    final static String NUM_OF_PAYMENTS = "numOfPayments";
    final static String PAYMENT_DAY = "dayOfPayment";

    public Offer(int pawnListingID, String uid, double amount, double interestRate, Date dateToStart, boolean accepted, int numOfPayments, String dayOfPayment) {
        this.pawnListingID = pawnListingID;
        this.amount = amount;
        this.interestRate = interestRate;
        this.dateToStart = dateToStart;
        this.accepted = accepted;
        this.numOfPayments = numOfPayments;
        this.dayOfPayment = dayOfPayment;
        this.Uid = uid;
    }

    public Offer() {
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDayOfPayment() {
        return dayOfPayment;
    }

    public void setDayOfPayment(String dayOfPayment) {
        this.dayOfPayment = dayOfPayment;
    }

    public int getNumOfPayments() {
        return numOfPayments;
    }

    public void setNumOfPayments(int numOfPayments) {
        this.numOfPayments = numOfPayments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPawnListingID() {
        return pawnListingID;
    }

    public void setPawnListingID(int pawnListingID) {
        this.pawnListingID = pawnListingID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getDateToStart() {
        return dateToStart;
    }

    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Map<String, Object> getjson(Offer offer) {
        Map<String, Object> json = new HashMap<>();

        json.put(ID, id);
        json.put(UID, Uid);
        json.put(PAWN_LISTING_ID, pawnListingID);
        json.put(AMOUNT, amount);
        json.put(INTEREST_RATE, interestRate);
        json.put(STARTING_DATE, dateToStart.getTime());
        json.put(ACCEPTED, accepted);
        json.put(NUM_OF_PAYMENTS, numOfPayments);
        json.put(PAYMENT_DAY, dayOfPayment);

        return json;
    }

    public static Offer create(Map<String, Object> json) {
        Offer offer = new Offer((int) json.get(PAWN_LISTING_ID),
                (String) json.get(UID),
                (double) json.get(AMOUNT),
                (double) json.get(INTEREST_RATE),
                new Date((long) json.get(STARTING_DATE)),
                (boolean) json.get(ACCEPTED),
                (int) json.get(NUM_OF_PAYMENTS),
                (String) json.get(PAYMENT_DAY));
        offer.setId((int) json.get(ID));
        return offer;
    }
}
