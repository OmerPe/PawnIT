package com.colman.pawnit.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    public interface OnCompleteListener{
        void onComplete();
    }

    public static final Model instance = new Model();

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    private Model(){

    }

    public void get2ndHandData(){

    }

    public void getAuctionData(){

    }

    public void getPawnListingData(){

    }

    public void getPawnData(){

    }

    public void getOfferData(){

    }

}
