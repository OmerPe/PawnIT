package com.colman.pawnit.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Pawn {
    private double loanAmount;
    private double intrestRate;
    private Date dateRecieved;
    private Date deadLine;
    private int PaymentDay;
    private Date lastPayment;
    private List<String> images = images = new LinkedList<>();
    private User loaner;

    public Pawn(){

    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getIntrestRate() {
        return intrestRate;
    }

    public void setIntrestRate(double intrestRate) {
        this.intrestRate = intrestRate;
    }

    public Date getDateRecieved() {
        return dateRecieved;
    }

    public void setDateRecieved(Date dateRecieved) {
        this.dateRecieved = dateRecieved;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public int getPaymentDay() {
        return PaymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        PaymentDay = paymentDay;
    }

    public Date getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(Date lastPayment) {
        this.lastPayment = lastPayment;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public User getLoaner() {
        return loaner;
    }

    public void setLoaner(User loaner) {
        this.loaner = loaner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    private User owner;
}
