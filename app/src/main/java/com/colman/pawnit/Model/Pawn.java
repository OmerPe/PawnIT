package com.colman.pawnit.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "pawn_table")
public class Pawn {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double loanAmount;
    private double intrestRate;
    private Date dateRquested;
    private Date deadLine;
    private int PaymentDay;
    private Date lastPayment;
    private List<String> images = images = new LinkedList<>();
    private String loaner;
    private String owner;

    public Pawn(){

    }

    public Pawn(double loanAmount, double intrestRate, Date dateRquested, Date deadLine, int paymentDay, Date lastPayment, List<String> images, String loaner, String owner) {
        this.loanAmount = loanAmount;
        this.intrestRate = intrestRate;
        this.dateRquested = dateRquested;
        this.deadLine = deadLine;
        PaymentDay = paymentDay;
        this.lastPayment = lastPayment;
        this.images = images;
        this.loaner = loaner;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateRquested() {
        return dateRquested;
    }

    public void setDateRquested(Date dateRquested) {
        this.dateRquested = dateRquested;
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

    public String getLoaner() {
        return loaner;
    }

    public void setLoaner(String loaner) {
        this.loaner = loaner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
