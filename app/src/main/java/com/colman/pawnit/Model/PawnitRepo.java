package com.colman.pawnit.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PawnitRepo {
    private ListingDao listingDao;
    private AuctionListingDao  auctionListingDao;
    private ResellListingDao resellListingDao;
    private PawnListingDao pawnListingDao;
    private PawnDao pawnDao;
    private OfferDao offerDao;

    private LiveData<List<AuctionListing>> allAuctions;
    private LiveData<List<ResellListing>> allResells;
    private LiveData<List<PawnListing>> allPawns;

    public PawnitRepo(Application application){
        PawnITDataBase dataBase = PawnITDataBase.getInstance(application);
        listingDao = dataBase.listingDao();
        auctionListingDao = dataBase.auctionListingDao();
        resellListingDao = dataBase.resellListingDao();
        pawnListingDao = dataBase.pawnListingDao();
        pawnDao = dataBase.pawnDao();
        offerDao = dataBase.offerDao();

        allAuctions = auctionListingDao.getAllAuctions();
        allResells = resellListingDao.getAllResells();
        allPawns = pawnListingDao.getAllPawns();

    }

    public void insert(Listing listing){

        if(listing instanceof AuctionListing){
            new InsertAuctionListingAsyncTask(auctionListingDao).execute((AuctionListing)listing);
        }
        if(listing instanceof ResellListing){
            new InsertResellListingAsyncTask(resellListingDao).execute((ResellListing) listing);
        }
        if(listing instanceof PawnListing){
            new InsertPawnListingAsyncTask(pawnListingDao).execute((PawnListing)listing);
        }

    }
    public void update(Listing listing){
        if(listing instanceof AuctionListing){
            new updateAuctionListingAsyncTask(auctionListingDao).execute((AuctionListing)listing);
        }
        if(listing instanceof ResellListing){
            new updateResellListingAsyncTask(resellListingDao).execute((ResellListing) listing);
        }
        if(listing instanceof PawnListing){
            new updatePawnListingAsyncTask(pawnListingDao).execute((PawnListing)listing);
        }
    }
    public void delete(Listing listing){
        if(listing instanceof AuctionListing){
            new deleteAuctionListingAsyncTask(auctionListingDao).execute((AuctionListing)listing);
        }
        if(listing instanceof ResellListing){
            new deleteResellListingAsyncTask(resellListingDao).execute((ResellListing) listing);
        }
        if(listing instanceof PawnListing){
            new deletePawnListingAsyncTask(pawnListingDao).execute((PawnListing)listing);
        }
    }

    private static class InsertAuctionListingAsyncTask extends AsyncTask<AuctionListing,Void,Void>{
        private AuctionListingDao listingDao;

        private InsertAuctionListingAsyncTask(AuctionListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(AuctionListing... listings) {
            listingDao.insert(listings[0]);
            return null;
        }
    }

    private static class updateAuctionListingAsyncTask extends AsyncTask<AuctionListing,Void,Void>{
        private AuctionListingDao listingDao;

        private updateAuctionListingAsyncTask(AuctionListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(AuctionListing... listings) {
            listingDao.update(listings[0]);
            return null;
        }
    }

    private static class deleteAuctionListingAsyncTask extends AsyncTask<AuctionListing,Void,Void>{
        private AuctionListingDao listingDao;

        private deleteAuctionListingAsyncTask(AuctionListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(AuctionListing... listings) {
            listingDao.delete(listings[0]);
            return null;
        }
    }

    private static class InsertResellListingAsyncTask extends AsyncTask<ResellListing,Void,Void>{
        private ResellListingDao listingDao;

        private InsertResellListingAsyncTask(ResellListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(ResellListing... listings) {
            listingDao.insert(listings[0]);
            return null;
        }
    }

    private static class updateResellListingAsyncTask extends AsyncTask<ResellListing,Void,Void>{
        private ResellListingDao listingDao;

        private updateResellListingAsyncTask(ResellListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(ResellListing... listings) {
            listingDao.update(listings[0]);
            return null;
        }
    }

    private static class deleteResellListingAsyncTask extends AsyncTask<ResellListing,Void,Void>{
        private ResellListingDao listingDao;

        private deleteResellListingAsyncTask(ResellListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(ResellListing... listings) {
            listingDao.delete(listings[0]);
            return null;
        }
    }

    private static class InsertPawnListingAsyncTask extends AsyncTask<PawnListing,Void,Void>{
        private PawnListingDao listingDao;

        private InsertPawnListingAsyncTask(PawnListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(PawnListing... listings) {
            listingDao.insert(listings[0]);
            return null;
        }
    }

    private static class updatePawnListingAsyncTask extends AsyncTask<PawnListing,Void,Void>{
        private PawnListingDao listingDao;

        private updatePawnListingAsyncTask(PawnListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(PawnListing... listings) {
            listingDao.update(listings[0]);
            return null;
        }
    }

    private static class deletePawnListingAsyncTask extends AsyncTask<PawnListing,Void,Void>{
        private PawnListingDao listingDao;

        private deletePawnListingAsyncTask(PawnListingDao listing){
            listingDao = listing;
        }


        @Override
        protected Void doInBackground(PawnListing... listings) {
            listingDao.delete(listings[0]);
            return null;
        }
    }

}
