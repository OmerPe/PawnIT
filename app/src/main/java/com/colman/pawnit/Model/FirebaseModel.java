package com.colman.pawnit.Model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.colman.pawnit.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {
    final static String RESELL_LISTING_COLLECTION = "Resell_listings";
    final static String AUCTION_LISTING_COLLECTION = "Auction_listings";
    final static String PAWN_LISTING_COLLECTION = "Pawn_listings";
    final static String USER_COLLECTION = "Users";

    private FirebaseModel() {
    }

    /*---------------------------------------------------------------------------Store--------------------------------------------------------------------------------------*/
    public interface getAllResellsListener {
        public void onComplete(List<ResellListing> resells);
    }

    public static void getAllResells(getAllResellsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(RESELL_LISTING_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<ResellListing> listings = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ResellListing resellListing = ResellListing.create(document.getData());
                                resellListing.setListingID(document.getId());
                                listings.add(resellListing);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(listings);
                    }
                });
    }

    public interface getAllAuctionsListener {
        public void onComplete(List<AuctionListing> resells);
    }

    public static void getAllAuctions(getAllAuctionsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AUCTION_LISTING_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<AuctionListing> listings = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AuctionListing listing = AuctionListing.create(document.getData());
                                listing.setListingID(document.getId());
                                listings.add(listing);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(listings);
                    }
                });
    }

    public interface getAllPawnsListener {
        void onComplete(List<PawnListing> resells);
    }

    public static void getAllPawnListings(getAllPawnsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PAWN_LISTING_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<PawnListing> listings = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PawnListing listing = PawnListing.create(document.getData());
                                listings.add(listing);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(listings);
                    }
                });
    }

    public static void saveListing(Listing listing, Model.myOnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<DocumentReference> ref = null;

        if (listing instanceof ResellListing) {
            Model.instance.resellLoadingState.setValue(Model.LoadingState.loading);
            ResellListing resellListing = (ResellListing) listing;
            ref = db.collection(RESELL_LISTING_COLLECTION).add(resellListing.getJson());
        } else if (listing instanceof AuctionListing) {
            Model.instance.auctionLoadingState.setValue(Model.LoadingState.loading);
            AuctionListing auctionListing = (AuctionListing) listing;
            ref = db.collection(AUCTION_LISTING_COLLECTION).add(auctionListing.getJson());
        } else if (listing instanceof PawnListing) {
            Model.instance.pawnListingLoadingState.setValue(Model.LoadingState.loading);
            PawnListing pawnListing = (PawnListing) listing;
            ref = db.collection(PAWN_LISTING_COLLECTION).add(pawnListing.getJson());
        }

        if (ref != null) {
            ref.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    documentReference.update("ListingID",documentReference.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            MyApplication.mainThreadHandler.post(() -> {
                                onCompleteListener.onComplete(documentReference.getId());
                            });
                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            onCompleteListener.onComplete(null);
                            Log.d("TAG", "documentation failed");
                        }
                    });
        }

    }

    public interface deleteOnCompleteListener{
        void onComplete();
    }

    public static void deletePawnListing(String listingID,deleteOnCompleteListener listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PAWN_LISTING_COLLECTION).document(listingID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void deleteAuctionListing(String listingID,deleteOnCompleteListener listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AUCTION_LISTING_COLLECTION).document(listingID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void deleteResellListing(String listingID,deleteOnCompleteListener listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(RESELL_LISTING_COLLECTION).document(listingID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public interface updateListingListener{
        void onComplete();
    }

    public static void updateListing(String listingID, Listing listing, updateListingListener listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<Void> ref = null;

        if (listing instanceof ResellListing) {
            Model.instance.resellLoadingState.setValue(Model.LoadingState.loading);
            ResellListing resellListing = (ResellListing) listing;
            ref = db.collection(RESELL_LISTING_COLLECTION).document(listingID).update(resellListing.getJson());
        } else if (listing instanceof AuctionListing) {
            Model.instance.auctionLoadingState.setValue(Model.LoadingState.loading);
            AuctionListing auctionListing = (AuctionListing) listing;
            ref = db.collection(AUCTION_LISTING_COLLECTION).document(listingID).update(auctionListing.getJson());
        } else if (listing instanceof PawnListing) {
            Model.instance.pawnListingLoadingState.setValue(Model.LoadingState.loading);
            PawnListing pawnListing = (PawnListing) listing;
            ref = db.collection(PAWN_LISTING_COLLECTION).document(listingID).update(pawnListing.getJson());
        }

        if (ref != null) {
            ref.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    listener.onComplete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG","error updating listing "+listingID);
                }
            });
        }
    }

    public static void getAllPawnsForUser(String Uid, getAllPawnsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PAWN_LISTING_COLLECTION).whereEqualTo(Listing.OWNER_ID,Uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<PawnListing> listings = new LinkedList<>();
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                listings.add(PawnListing.create(document.getData()));
                            }
                        }
                        listener.onComplete(listings);
                    }
                });
    }


    /*---------------------------------------------------------------------------Auth---------------------------------------------------------------------------------------*/
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getUser() {
        return getAuth().getCurrentUser();
    }

    public static boolean isLoggedIn() {
        return getUser() != null;
    }

    public static void signUpUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public static void logIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public static void logOut() {
        getAuth().signOut();
    }

    public static void forgotPassword(String email, OnCompleteListener<Void> listener) {
        getAuth().sendPasswordResetEmail(email).addOnCompleteListener(listener);
    }

    public interface userOnCompleteListener {
        void onComplete();
    }

    public static void saveUser(User user, userOnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_COLLECTION).document(user.getUid())
                .set(user.toJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                onCompleteListener.onComplete();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "documentation failed");
                    }
                });
    }

    public interface getUserDataListener {
        public void onComplete(User user);
    }

    public static void getUserData(getUserDataListener listener) {
        if (isLoggedIn()) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(USER_COLLECTION).document(getUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User user = User.create(document.getData());
                            listener.onComplete(user);
                        }
                    }else {
                        listener.onComplete(null);
                    }
                }
            });
        }
    }

    public static void updateUser(User user, Model.updateUserDataOnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_COLLECTION).document(getUser().getUid())
                .update(user.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        listener.onComplete();
    }

    public static void uploadImage(Bitmap imageBmp, String name, String dir, final Model.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child(dir).child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

}
