package com.colman.pawnit.Model;

import androidx.room.Entity;

import java.util.Date;
import java.util.List;

@Entity(tableName = "Pawn_Listings_table")
public class PawnListing extends Listing {
    private double loanAmountRequested;
    private double interestRate;
    private Date whenToGet;
    private int numOfPayments;
    private String dayOfPayment;

    public PawnListing(String owner, String title, String description, String location, Date dateOpened, List<String> images, double loanAmountRequested, double interestRate, Date whenToGet, int numOfPayments, String dayOfPayment) {
        super(owner, title, description, location, dateOpened, images);
        this.loanAmountRequested = loanAmountRequested;
        this.interestRate = interestRate;
        this.whenToGet = whenToGet;
        this.numOfPayments = numOfPayments;
        this.dayOfPayment = dayOfPayment;
    }

    public PawnListing() {
        super();
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
}
