package com.colman.pawnit.Model;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.pawnit.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public interface OnCompleteListener {
        void onComplete();
    }

    private ListingDao listingDao;
    private AuctionListingDao auctionListingDao;
    private ResellListingDao resellListingDao;
    private PawnListingDao pawnListingDao;
    private PawnDao pawnDao;
    private OfferDao offerDao;
    private HistoryDao historyDao;

    User currentUser = new User();

    public enum LoadingState {
        loaded,
        loading,
        error
    }

    public MutableLiveData<LoadingState> resellLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> auctionLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> pawnListingLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> userLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> offerLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> pawnLoadingState = new MutableLiveData<>(LoadingState.loaded);

    public static final Model instance = new Model();
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    private Model() {
        PawnITDataBase dataBase = PawnITDataBase.getInstance();
        listingDao = dataBase.listingDao();
        auctionListingDao = dataBase.auctionListingDao();
        resellListingDao = dataBase.resellListingDao();
        pawnListingDao = dataBase.pawnListingDao();
        pawnDao = dataBase.pawnDao();
        offerDao = dataBase.offerDao();
        historyDao = dataBase.historyDao();
    }

    public LiveData<List<Listing>> getMarketList() {
        return ZipLiveData.create(
                getAuctionData(),
                getResellData(),
                (auction, resell) -> {
                    final List<Listing> listings = new LinkedList<>();
                    if (auction != null) {
                        listings.addAll(auction);
                    }
                    if (resell != null) {
                        listings.addAll(resell);
                    }
                    Collections.sort(listings, (listing1, listing2) -> {
                        if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                            return 0;
                        }
                        return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
                    });
                    return listings;
                });
    }

    public LiveData<List<Listing>> getAllListings() {
        return ZipLiveData.create(
                getMarketList(),
                getAllPawnListings(),
                (market, pawn) -> {
                    final List<Listing> listings = new LinkedList<>();
                    if (market != null) {
                        listings.addAll(market);
                    }
                    if (pawn != null) {
                        listings.addAll(pawn);
                    }
                    Collections.sort(listings, (listing1, listing2) -> {
                        if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                            return 0;
                        }
                        return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
                    });
                    return listings;
                });
    }

    MutableLiveData<List<ResellListing>> allResellListings = new MutableLiveData<List<ResellListing>>(new LinkedList<ResellListing>());

    public LiveData<List<ResellListing>> getResellData() {
        resellLoadingState.setValue(LoadingState.loading);
        FirebaseModel.getAllResells((resells) -> {
            Collections.sort(resells, (listing1, listing2) -> {
                if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                    return 0;
                }
                return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
            });
            allResellListings.setValue(resells);
            resellLoadingState.setValue(LoadingState.loaded);
        });
        return allResellListings;
    }

    MutableLiveData<List<AuctionListing>> allAuctionListings = new MutableLiveData<List<AuctionListing>>(new LinkedList<AuctionListing>());

    public LiveData<List<AuctionListing>> getAuctionData() {
        auctionLoadingState.setValue(LoadingState.loading);
        FirebaseModel.getAllAuctions((auctions) -> {
            Collections.sort(auctions, (listing1, listing2) -> {
                if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                    return 0;
                }
                return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
            });
            allAuctionListings.setValue(auctions);
            auctionLoadingState.setValue(LoadingState.loaded);
        });
        return allAuctionListings;
    }

    MutableLiveData<List<PawnListing>> allPawnListings = new MutableLiveData<List<PawnListing>>(new LinkedList<PawnListing>());

    public LiveData<List<PawnListing>> getAllPawnListings() {
        pawnLoadingState.setValue(LoadingState.loading);
        FirebaseModel.getAllPawns((pawns) -> {
            Collections.sort(pawns, (listing1, listing2) -> {
                if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                    return 0;
                }
                return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
            });
            allPawnListings.setValue(pawns);
            pawnLoadingState.setValue(LoadingState.loaded);
        });
        return allPawnListings;
    }

    public LiveData<List<Pawn>> getAllPawnsForUser(String uid) {
        return pawnDao.getAllPawnsForUser(uid);
    }

    public LiveData<List<History>> getAllHistoryForUser(String uid) {
        return historyDao.getAllHistoryForUser(uid);
    }

    public LiveData<List<Offer>> getAllOffersForListing(String listingID) {
        return offerDao.getAllOffersForListing(listingID);
    }

    public interface myOnCompleteListener {
        void onComplete();
    }
    public void saveListing(Listing listing, myOnCompleteListener onCompleteListener) {
        FirebaseModel.saveListing(listing, () -> {
            onCompleteListener.onComplete();
        });
    }

    public interface createUserDataOnCompleteListener{
        void onComplete();
    }
    public void createUser(User user,createUserDataOnCompleteListener listener) {
        userLoadingState.setValue(LoadingState.loading);
        currentUser = user;
        FirebaseModel.saveUser(currentUser, () -> {
            listener.onComplete();
        });
    }

    public User getUserData(){
        return currentUser;
    }

    public interface updateUserDataOnCompleteListener{
        void onComplete();
    }
    public void updateUserData(updateUserDataOnCompleteListener listener) {
        userLoadingState.setValue(LoadingState.loading);
        if(currentUser.getUid() == null && isLoggedIn()){
            FirebaseModel.getUserData((user)->{
                currentUser = user;
                listener.onComplete();
                userLoadingState.setValue(LoadingState.loaded);
            });
        }
    }

    public void signUpUser(String email, String password, com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener){
        FirebaseModel.signUpUser(email,password,listener);
    }

    public void logIn(String email, String password, com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener){
        FirebaseModel.logIn(email,password,task -> {
            if(task.isSuccessful()){
                listener.onComplete(task);
            }
        });
    }

    public void logOut() {
        currentUser = new User();
        FirebaseModel.logOut();
    }

    public void forgotPassword(String email, com.google.android.gms.tasks.OnCompleteListener<Void> listener){
        FirebaseModel.forgotPassword(email,listener);
    }

    public FirebaseUser getLoggedUser(){
        return FirebaseModel.getUser();
    }

    public boolean isLoggedIn(){
        return FirebaseModel.isLoggedIn();
    }
}
