package com.colman.pawnit.Model;

import androidx.room.Entity;

import java.util.Date;
import java.util.List;


@Entity(tableName = "auction_hand_listing")
public class Auction extends Listing {
    private Date endDate;
    private double startingPrice;

    public Auction(){
        super();
    }

    public Auction(String owner, String title, String description, String location, Date dateOpened, List<String> images, Date endDate, double startingPrice) {
        super(owner, title, description, location, dateOpened, images);
        this.endDate = endDate;
        this.startingPrice = startingPrice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }
}
