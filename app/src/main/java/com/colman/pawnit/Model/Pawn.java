package com.colman.pawnit.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Pawn {
    private double loanAmount;
    private double intrestRate;
    private Date dateRecieved;
    private Date lastDayToPay;
    private List<String> images;
    private User loaner;


    public Pawn() {
        images = new LinkedList<>();
    }

    public Pawn(Date dateRecieved, double amount, Date lastDayToPay) {
        this.loanAmount = amount;
        this.dateRecieved = dateRecieved;
        this.lastDayToPay = lastDayToPay;

        images = new LinkedList<>();
    }

}
