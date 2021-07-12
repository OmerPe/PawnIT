package com.colman.pawnit.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/*
this object is a pawn listing after being accepted, which means it already knows who gave the loan, whats the final interest rates and everything.
basically this object is finalized once settled.
 */

@Entity(tableName = "pawn_table")
public class Pawn {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private final int listingID;
    private final double loanAmount;
    private final double interestRate;
    private final String lenderId;
    private final String borrowerId;
    private final Date dateRecieved;
    private final Date dateEnds;
    private final String paymentDay;
    private final int numOfPayments;
    private Date lastPayment;

    public Pawn(int listingID, double loanAmount, double interestRate, String lenderId, String borrowerId, Date dateRecieved, Date dateEnds, String paymentDay, int numOfPayments) {
        this.listingID = listingID;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.lenderId = lenderId;
        this.dateRecieved = dateRecieved;
        this.dateEnds = dateEnds;
        this.paymentDay = paymentDay;
        this.numOfPayments = numOfPayments;
        this.borrowerId = borrowerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastPayment(Date lastPayment) {
        this.lastPayment = lastPayment;
    }

    public int getId() {
        return id;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public int getListingID() {
        return listingID;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getLenderId() {
        return lenderId;
    }

    public Date getDateRecieved() {
        return dateRecieved;
    }

    public Date getDateEnds() {
        return dateEnds;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public int getNumOfPayments() {
        return numOfPayments;
    }

    public Date getLastPayment() {
        return lastPayment;
    }
}
