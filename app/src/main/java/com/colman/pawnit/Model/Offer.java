package com.colman.pawnit.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

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
}
