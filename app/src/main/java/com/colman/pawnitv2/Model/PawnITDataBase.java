package com.colman.pawnitv2.Model;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.colman.pawnitv2.MyApplication;

@Database(entities = {Listing.class, AuctionListing.class, ResellListing.class, PawnListing.class, Offer.class, Pawn.class, History.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class PawnITDataBase extends RoomDatabase {

    private static PawnITDataBase instance;

    public abstract ListingDao listingDao();

    public abstract AuctionListingDao auctionListingDao();

    public abstract PawnListingDao pawnListingDao();

    public abstract ResellListingDao resellListingDao();

    public abstract PawnDao pawnDao();

    public abstract OfferDao offerDao();

    public abstract HistoryDao historyDao();

    public static synchronized PawnITDataBase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(MyApplication.context,
                    PawnITDataBase.class, "pawnit_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
