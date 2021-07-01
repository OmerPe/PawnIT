package com.colman.pawnit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class pawnActivity extends AppCompatActivity {

    private String itemName, desc;
    private int img;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pawn);


        MyAdapter adapter = new MyAdapter();
        ListView listView = findViewById(R.id.pawn_listv);
        listView.setClipToOutline(true);
        listView.setAdapter(adapter);



    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.item_list_row,null);

            TextView nameTv=v.findViewById(R.id.listrow_title_tv);
            nameTv.setText("Item" + i);

            return v;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

    }


}