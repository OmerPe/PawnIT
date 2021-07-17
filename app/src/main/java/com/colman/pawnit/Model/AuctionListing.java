package com.colman.pawnit.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;


@Entity(tableName = "auction_listings_table")
public class AuctionListing extends Listing {

    private Date endDate;
    private double startingPrice;
    private double currentPrice;

    public AuctionListing(){
        super();
    }

    public AuctionListing(String owner, String title, String description, String location, Date dateOpened, List<String> images, Date endDate, double startingPrice, double currentPrice) {
        super(owner, title, description, location, dateOpened, images);
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
