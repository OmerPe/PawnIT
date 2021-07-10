package com.colman.pawnit.Model;

import java.util.Date;
import java.util.List;

public class Auction extends Listing {
    private Date endDate;

    public Auction(){
        super();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
