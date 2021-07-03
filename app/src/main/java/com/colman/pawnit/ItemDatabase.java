package com.colman.pawnit;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Item.class},version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized  ItemDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
            ItemDatabase.class,"item_databasev2")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull  SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private ItemDao itemDao;

        private PopulateDbAsyncTask(ItemDatabase db){
            itemDao = db.itemDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Title 1","1","description 1",0.0,"15.2.2021",null));
            itemDao.insert(new Item("Title 2","2","description 2",0.0,"16.2.2021",null));
            itemDao.insert(new Item("Title 3","3","description 3",0.0,"17.2.2021",null));
            return null;
        }
    }
}
