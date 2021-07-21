package com.colman.pawnit.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private final Date dateReceived;
    private final Date dateEnds;
    private final String paymentDay;
    private final int numOfPayments;
    private Date lastPayment;

    final static String ID = "id";
    final static String LISTING_ID = "listingID";
    final static String LOAN_AMOUNT = "loanAmount";
    final static String INTEREST_RATE = "interestRate";
    final static String LENDER_ID = "lenderId";
    final static String BORROWER_ID = "borrowerId";
    final static String DATE_RECEIVED = "dateReceived";
    final static String END_DATE = "dateEnds";
    final static String PAYMENT_DAY = "paymentDay";
    final static String NUM_OF_PAYMENTS = "numOfPayments";
    final static String LAST_PAYMENT = "lastPayment";


    public Pawn(int listingID, double loanAmount, double interestRate, String lenderId, String borrowerId, Date dateReceived, Date dateEnds, String paymentDay, int numOfPayments) {
        this.listingID = listingID;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.lenderId = lenderId;
        this.dateReceived = dateReceived;
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

    public Date getDateReceived() {
        return dateReceived;
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

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, id);
        json.put(LISTING_ID, listingID);
        json.put(LOAN_AMOUNT, loanAmount);
        json.put(INTEREST_RATE, interestRate);
        json.put(LENDER_ID, lenderId);
        json.put(BORROWER_ID, borrowerId);
        json.put(DATE_RECEIVED, dateReceived.getTime());
        json.put(END_DATE, dateEnds.getTime());
        json.put(PAYMENT_DAY, paymentDay);
        json.put(NUM_OF_PAYMENTS, numOfPayments);
        json.put(LAST_PAYMENT, lastPayment.getTime());
        return json;
    }

    public static Pawn create(Map<String, Object> json) {
        Pawn pawn = new Pawn(
                Integer.parseInt((String) json.get(LISTING_ID)),
                Double.parseDouble((String) json.get(LOAN_AMOUNT)),
                Double.parseDouble((String) json.get(INTEREST_RATE)),
                (String) json.get(LENDER_ID),
                (String) json.get(BORROWER_ID),
                ((Timestamp)json.get(DATE_RECEIVED)).toDate(),
                ((Timestamp)json.get(END_DATE)).toDate(),
                (String) json.get(PAYMENT_DAY),
                Integer.parseInt((String) json.get(NUM_OF_PAYMENTS))
        );
        pawn.setId(Integer.parseInt((String) json.get(ID)));
        pawn.setLastPayment(((Timestamp)json.get(LAST_PAYMENT)).toDate());
        return pawn;
    }

}
