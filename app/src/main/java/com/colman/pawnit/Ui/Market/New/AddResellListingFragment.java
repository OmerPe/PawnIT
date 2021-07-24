package com.colman.pawnit.Ui.Market.New;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.MyApplication;
import com.colman.pawnit.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class AddResellListingFragment extends Fragment {

    private AddResellListingViewModel mViewModel;
    ImageView imageView;
    LayoutInflater inf;
    LinearLayout gallery;
    List<Bitmap> selectedImages;

    public static AddResellListingFragment newInstance() {
        return new AddResellListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_resell_listing_fragment, container, false);

        gallery = view.findViewById(R.id.resell_gallery);
        inf = inflater;

        EditText title = view.findViewById(R.id.add_resell_title);
        EditText price = view.findViewById(R.id.add_resell_price);
        EditText desc = view.findViewById(R.id.add_resell_description);
        Button addBtn = view.findViewById(R.id.add_resell_add_btn);
        ProgressBar progressBar = view.findViewById(R.id.add_resell_progressbar);
        progressBar.setVisibility(View.GONE);

        ImageButton addImages = view.findViewById(R.id.add_resell_imageB);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);
                ResellListing listing = new ResellListing();

                listing.setTitle(title.getText().toString().trim());
                listing.setPrice(Double.parseDouble(price.getText().toString().trim()));
                listing.setDescription(desc.getText().toString().trim());
                listing.setDateOpened(Calendar.getInstance().getTime());
                listing.setOwnerId(Model.instance.getLoggedUser().getUid());

                Model.instance.saveListing(listing,(listingId)->{
                    if(listingId != null){
                        Model.instance.getUserFromDB(user -> {
                            if(user != null){
                                user.addResellListing(listingId);
                                Model.instance.updateUserData(user,()->{
                                    Model.instance.userLoadingState.setValue(Model.LoadingState.loaded);
                                });
                            }
                        });
                    }
                });
                Navigation.findNavController(v).navigateUp();
            }
        });
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImages();
            }
        });

        return view;
    }

    static final int PICK_IMAGE = 1;
    void addImages(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK) {
            try {
                selectedImages = new LinkedList<>();
                ClipData clipData = data.getClipData();
                if(clipData != null){//multiplte images
                    for (int i =0; i<clipData.getItemCount();i++){
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap image = BitmapFactory.decodeStream(is);
                        selectedImages.add(image);
                    }
                }else {//single image
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    selectedImages.add(image);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
            new Thread(()->{
                for (final Bitmap image : selectedImages){
                    MyApplication.mainThreadHandler.post(()->{
                        View im = inf.inflate(R.layout.image_item,gallery,false);
                        ImageView imV = im.findViewById(R.id.imageItem_imageV);
                        imV.setImageBitmap(image);
                        if(imV.getParent() != null){
                            ((ViewGroup)imV.getParent()).removeView(imV);
                        }
                        gallery.addView(imV);
                    });
                }

            }).start();

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}