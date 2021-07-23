package com.colman.pawnit.Model;

import androidx.room.Entity;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity(tableName = "Pawn_Listings_table")
public class PawnListing extends Listing {

    private double loanAmountRequested;
    private double interestRate;
    private Date whenToGet;
    private int numOfPayments;
    private String dayOfPayment;

    final static String LOAN_AMOUNT_REQUESTED = "loanAmountRequested";
    final static String INTEREST_RATE = "interestRate";
    final static String WHEN_TO_GET = "whenToGet";
    final static String NUM_OF_PAYMENTS = "numOfPayments";
    final static String PAYMENT_DAY = "dayOfPayment";
    final static String TYPE = "Pawn";

    public PawnListing(String owner, String title, String description, String location, Date dateOpened, List<String> images, double loanAmountRequested, double interestRate, Date whenToGet, int numOfPayments, String dayOfPayment) {
        super(owner, title, description, location, dateOpened, images, TYPE);
        this.loanAmountRequested = loanAmountRequested;
        this.interestRate = interestRate;
        this.whenToGet = whenToGet;
        this.numOfPayments = numOfPayments;
        this.dayOfPayment = dayOfPayment;
    }

    public PawnListing() {
        super();
        super.setType(TYPE);
    }

    public String getDayOfPayment() {
        return dayOfPayment;
    }

    public void setDayOfPayment(String dayOfPayment) {
        this.dayOfPayment = dayOfPayment;
    }

    public double getLoanAmountRequested() {
        return loanAmountRequested;
    }

    public void setLoanAmountRequested(double loanAmountRequested) {
        this.loanAmountRequested = loanAmountRequested;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getWhenToGet() {
        return whenToGet;
    }

    public void setWhenToGet(Date whenToGet) {
        this.whenToGet = whenToGet;
    }

    public int getNumOfPayments() {
        return numOfPayments;
    }

    public void setNumOfPayments(int numOfPayments) {
        this.numOfPayments = numOfPayments;
    }

    @Override
    public Map<String, Object> getJson() {
        Map<String, Object> json = super.getJson();
        json.put(LOAN_AMOUNT_REQUESTED, loanAmountRequested);
        json.put(INTEREST_RATE, interestRate);
        json.put(WHEN_TO_GET, whenToGet);
        json.put(NUM_OF_PAYMENTS, numOfPayments);
        json.put(PAYMENT_DAY, dayOfPayment);

        return json;
    }

    public static PawnListing create(Map<String, Object> json) {
        Listing listing = Listing.createListing(json);

        return new PawnListing(listing.getOwnerId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getLocation(),
                listing.getDateOpened(),
                listing.getImages(),
                (double)json.get(LOAN_AMOUNT_REQUESTED),
                (double)json.get(INTEREST_RATE),
                ((Timestamp)json.get(WHEN_TO_GET)).toDate(),
                (int)((long)json.get(NUM_OF_PAYMENTS)),
                (String) json.get(PAYMENT_DAY));
    }
}
