package com.colman.pawnit.Model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
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
    private HistoryDao historyDao;

    public static final String PROFILE_DIR = "profilePics";
    public static final String LISTINGS_DIR = "listingsImages";

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

    public static final Model instance = new Model();
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    private Model() {
        PawnITDataBase dataBase = PawnITDataBase.getInstance();
        listingDao = dataBase.listingDao();
        auctionListingDao = dataBase.auctionListingDao();
        resellListingDao = dataBase.resellListingDao();
        pawnListingDao = dataBase.pawnListingDao();
        historyDao = dataBase.historyDao();
    }

    public LiveData<List<Listing>> getMarketList(String Uid) {
        LiveData<List<AuctionListing>> auctionList;
        LiveData<List<ResellListing>> resellList;
        if (Uid != null) {
            auctionList = getAllUserAuctions();
            resellList = getAllUserResells();
        } else {
            auctionList = getAuctionData();
            resellList = getResellData();
        }

        return ZipLiveData.create(
                auctionList,
                resellList,
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
                getMarketList(null),
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

    LiveData<List<ResellListing>> allResellListings = PawnITDataBase.getInstance().resellListingDao().getAllResells();
    public LiveData<List<ResellListing>> getResellData() {
        resellLoadingState.setValue(LoadingState.loading);

        //read local last update time
        Long localLastUpdate = Listing.getLocalLastUpdateTime();

        //get all updates from server
        FirebaseModel.getAllResells(localLastUpdate, resells -> {
            executorService.execute(() -> {
                Log.d("TAG","fb returned "+resells.size()+" resells");
                Long lastupdate = 0L;
                //update local db
                Collections.sort(resells, (listing1, listing2) -> { //sorting everything by date created
                    if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                        return 0;
                    }
                    return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
                });
                for (ResellListing listing : resells) {
                    Log.d("TAG","- resell "+listing.getListingID() +"\n LuD : " + listing.getLastUpdated());

                    resellListingDao.insertAll(listing);
                    //update local last update time
                    if (lastupdate < listing.getLastUpdated()) {
                        lastupdate = listing.getLastUpdated();
                    }
                }

                Listing.setLocalLastUpdateTime(lastupdate);
                resellLoadingState.postValue(LoadingState.loaded);
                //read and return all data from local db
            });
        });

        return allResellListings;
    }

    LiveData<List<AuctionListing>> allAuctionListings = PawnITDataBase.getInstance().auctionListingDao().getAllAuctions();
    public LiveData<List<AuctionListing>> getAuctionData() {
        auctionLoadingState.setValue(LoadingState.loading);

        //read local last update time
        Long localLastUpdate = Listing.getLocalLastUpdateTime();

        //get all updates from server
        FirebaseModel.getAllAuctions(localLastUpdate, auctions -> {
            executorService.execute(() -> {
                Log.d("TAG","fb returned "+auctions.size()+" auctions");
                Long lastupdate = 0L;
                //update local db
                Collections.sort(auctions, (listing1, listing2) -> { //sorting everything by date created
                    if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                        return 0;
                    }
                    return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
                });
                for (AuctionListing listing : auctions) {
                    Log.d("TAG","- auction "+listing.getListingID() +"\n LuD : " + listing.getLastUpdated());

                    auctionListingDao.insertAll(listing);
                    //update local last update time
                    if (lastupdate < listing.getLastUpdated()) {
                        lastupdate = listing.getLastUpdated();
                    }
                }

                Listing.setLocalLastUpdateTime(lastupdate);
                auctionLoadingState.postValue(LoadingState.loaded);
                //read and return all data from local db
            });
        });

        return allAuctionListings;
    }

    LiveData<List<PawnListing>> allPawnListings = PawnITDataBase.getInstance().pawnListingDao().getAllPawns();
    public LiveData<List<PawnListing>> getAllPawnListings() {
        pawnListingLoadingState.setValue(LoadingState.loading);

        //read local last update time
        Long localLastUpdate = Listing.getLocalLastUpdateTime();

        //get all updates from server
        FirebaseModel.getAllPawnListings(localLastUpdate, pawns -> {
            executorService.execute(() -> {
                Log.d("TAG","fb returned "+pawns.size()+" pawns");
                Long lastupdate = 0L;
                //update local db
                Collections.sort(pawns, (listing1, listing2) -> { //sorting everything by date created
                    if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                        return 0;
                    }
                    return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
                });
                for (PawnListing listing : pawns) {
                    Log.d("TAG","- pawn "+listing.getListingID() +"\n LuD : " + listing.getLastUpdated());

                    pawnListingDao.insertAll(listing);
                    //update local last update time
                    if (lastupdate < listing.getLastUpdated()) {
                        lastupdate = listing.getLastUpdated();
                    }
                }

                Listing.setLocalLastUpdateTime(lastupdate);
                pawnListingLoadingState.postValue(LoadingState.loaded);
                //read and return all data from local db
            });
        });

        return allPawnListings;
    }

    public LiveData<List<History>> getAllHistoryForUser(String uid) {
        return historyDao.getAllHistoryForUser(uid);
    }

    public interface myOnCompleteListener {
        void onComplete(String listingID);
    }

    public void saveListing(Listing listing, myOnCompleteListener onCompleteListener) {
        if (listing instanceof ResellListing) {
            Model.instance.resellLoadingState.setValue(Model.LoadingState.loading);
        } else if (listing instanceof AuctionListing) {
            Model.instance.auctionLoadingState.setValue(Model.LoadingState.loading);
        } else if (listing instanceof PawnListing) {
            Model.instance.pawnListingLoadingState.setValue(Model.LoadingState.loading);
        }

        FirebaseModel.saveListing(listing, onCompleteListener);

        if (listing instanceof ResellListing) {
            getResellData();
        } else if (listing instanceof AuctionListing) {
            getAuctionData();
        } else if (listing instanceof PawnListing) {
            getAllPawnListings();
        }
    }

    public interface createUserDataOnCompleteListener {
        void onComplete();
    }

    public void createUser(User user, createUserDataOnCompleteListener listener) {
        userLoadingState.setValue(LoadingState.loading);
        currentUser = user;
        FirebaseModel.saveUser(currentUser, () -> {
            listener.onComplete();
        });
    }

    public User getUserData() {
        return currentUser;
    }

    public void getUserFromDB(FirebaseModel.getUserDataListener listener) {
        FirebaseModel.getUserData(user -> {
            if (user != null) {
                currentUser = new User(FirebaseModel.getUser().getUid(), user.getUserName(), user.getDateOfBirth(), user.getEmail(), user.getProfilePic());
                currentUser.setResellListings(user.getResellListings());
                currentUser.setAuctionListings(user.getAuctionListings());
                currentUser.setPawnListings(user.getPawnListings());
                listener.onComplete(currentUser);
            } else {
                listener.onComplete(null);
            }
        });
    }

    public interface updateUserDataOnCompleteListener {
        void onComplete();
    }

    public void updateUserData(User user, updateUserDataOnCompleteListener listener) {
        userLoadingState.setValue(LoadingState.loading);
        FirebaseModel.updateUser(user, listener);
    }

    public void signUpUser(String email, String password, com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener) {
        FirebaseModel.signUpUser(email, password, listener);
    }

    public void logIn(String email, String password, com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener) {
        FirebaseModel.logIn(email, password, task -> {
            if (task.isSuccessful()) {
                listener.onComplete(task);
            }
        });
    }

    public void logOut() {
        currentUser = new User();
        FirebaseModel.logOut();
    }

    public void forgotPassword(String email, com.google.android.gms.tasks.OnCompleteListener<Void> listener) {
        FirebaseModel.forgotPassword(email, listener);
    }

    public FirebaseUser getLoggedUser() {
        return FirebaseModel.getUser();
    }

    public boolean isLoggedIn() {
        return FirebaseModel.isLoggedIn();
    }

    MutableLiveData<List<PawnListing>> allUserPawns = new MutableLiveData<List<PawnListing>>(new LinkedList<PawnListing>());

    public LiveData<List<PawnListing>> getAllUserPawns() {
        pawnListingLoadingState.setValue(LoadingState.loading);
        FirebaseModel.getAllPawnsForUser(FirebaseModel.getUser().getUid(), (pawns) -> {
            Collections.sort(pawns, (listing1, listing2) -> {
                if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                    return 0;
                }
                return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
            });
            allUserPawns.setValue(pawns);
            pawnListingLoadingState.setValue(LoadingState.loaded);
        });

        return allUserPawns;
    }

    MutableLiveData<List<AuctionListing>> allUserAuctions = new MutableLiveData<List<AuctionListing>>(new LinkedList<AuctionListing>());

    public LiveData<List<AuctionListing>> getAllUserAuctions() {
        auctionLoadingState.setValue(LoadingState.loading);
        FirebaseModel.getAllAuctionsForUser(FirebaseModel.getUser().getUid(), (auctions) -> {
            Collections.sort(auctions, (listing1, listing2) -> {
                if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                    return 0;
                }
                return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
            });
            allUserAuctions.setValue(auctions);
            pawnListingLoadingState.setValue(LoadingState.loaded);
        });

        return allUserAuctions;
    }

    MutableLiveData<List<ResellListing>> allUserResells = new MutableLiveData<List<ResellListing>>(new LinkedList<ResellListing>());

    public LiveData<List<ResellListing>> getAllUserResells() {
        resellLoadingState.setValue(LoadingState.loading);
        FirebaseModel.getAllResellsForUser(FirebaseModel.getUser().getUid(), (resells) -> {
            Collections.sort(resells, (listing1, listing2) -> {
                if (listing1.getDateOpened() == null || listing2.getDateOpened() == null) {
                    return 0;
                }
                return (-1) * listing1.getDateOpened().compareTo(listing2.getDateOpened());
            });
            allUserResells.setValue(resells);
            pawnListingLoadingState.setValue(LoadingState.loaded);
        });

        return allUserResells;
    }

    public void deletePawnListing(String listingID, FirebaseModel.deleteOnCompleteListener listener) {
        FirebaseModel.deletePawnListing(listingID, listener);
    }

    public void deleteAuctionListing(String listingID, FirebaseModel.deleteOnCompleteListener listener) {
        FirebaseModel.deleteAuctionListing(listingID, listener);
    }

    public void deleteResellListing(String listingID, FirebaseModel.deleteOnCompleteListener listener) {
        FirebaseModel.deleteResellListing(listingID, listener);
    }

    public void updateListing(String listingID, Listing listing, FirebaseModel.updateListingListener listener) {
        FirebaseModel.updateListing(listingID, listing, listener);
    }

    public interface UploadImageListener {
        void onComplete(String url);
    }

    public void uploadImage(Bitmap imageBmp, String name, String dir, final UploadImageListener listener) {
        FirebaseModel.uploadImage(imageBmp, name, dir, listener);
    }

    public void getPawnListing(String Uid, FirebaseModel.getListingOnCompleteListener listener) {
        FirebaseModel.getPawnListing(Uid, listener);
    }

    public void getAuctionListing(String Uid, FirebaseModel.getListingOnCompleteListener listener) {
        FirebaseModel.getAuctionListing(Uid, listener);
    }

    public void getResellListing(String Uid, FirebaseModel.getListingOnCompleteListener listener) {
        FirebaseModel.getResellListing(Uid, listener);
    }

    public interface UploadImagesListener {
        void onComplete(List<String> url);
    }
}
