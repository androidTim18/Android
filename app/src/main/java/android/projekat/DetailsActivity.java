package android.projekat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.projekat.ListAll.adDbHelper;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public Intent i;
    TextView name;
    TextView breed;
    TextView species;
    TextView birthday;
    TextView sex;
    TextView location;
    TextView owner;
    TextView info;
    TextView price;
    ImageView photo;
    TextView dateAdded;
    Ad ad = new Ad();
    Button buy;
    Button favorite;
    Button bConfirm;
    Button bCancel;
    PopupWindow popupWindow;
    TextView popupPrice;
    ListView commentList;
    EditText commentInput;
    Button addComment;
    ArrayList<Comment> commentArray;
    CommentAdapter commentAdapter;
    ArrayList<Comment> newComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        favorite = findViewById(R.id.bFavorite);
        favorite.setOnClickListener(this);
        buy = findViewById(R.id.bBuy);
        buy.setOnClickListener(this);
        commentList = findViewById(R.id.commentList);
        commentInput = findViewById(R.id.commentInput);
        addComment = findViewById(R.id.add_comment);
        addComment.setOnClickListener(this);

        i = getIntent();

        name = findViewById(R.id.d_name);
        breed = findViewById(R.id.d_breed);
        species = findViewById(R.id.d_species);
        birthday = findViewById(R.id.d_date);
        sex = findViewById(R.id.d_sex);
        location = findViewById(R.id.d_location);
        owner = findViewById(R.id.d_owner);
        info = findViewById(R.id.d_info);
        price = findViewById(R.id.d_price);
        photo = findViewById(R.id.d_img);
        dateAdded = findViewById(R.id.d_dateAdded);

        ad = adDbHelper.readAd(i.getStringExtra("adId"));

        name.setText(ad.name);
        breed.setText(ad.breed);
        species.setText(ad.species);
        birthday.setText(ad.birthday);
        sex.setText(ad.sex);
        location.setText(ad.location);
        owner.setText(ad.owner);
        info.setText(ad.info);
        price.setText(ad.price);
        photo.setImageDrawable(ad.photo);
        dateAdded.setText(ad.dateAdded);

        commentArray = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(this, commentArray);
        commentList.setAdapter(commentAdapter);

        newComments = ad.comments;
        commentAdapter.addAll(newComments);

        if (ad.favorite){
            favorite.setTag(1);
        }
        else {
            favorite.setTag(0);
        }

        if (ad.available){
            buy.setTag(1);
        }
        else {
            buy.setTag(0);
        }
        checkTags();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bFavorite:
                if(!ad.favorite){
                    adDbHelper.makeAdFavorite(ad);
                    favorite.setTag(1);
                }
                else {
                    adDbHelper.removeAdFavorite(ad);
                    favorite.setTag(0);
                }
                checkTags();
                break;
            case R.id.bBuy:
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_buy, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                popupWindow = new PopupWindow(popupView, width, height, true);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                bCancel = popupView.findViewById(R.id.bPopupCancel);
                bConfirm = popupView.findViewById(R.id.bPopupConfirm);
                popupPrice = popupView.findViewById(R.id.popupPrice);
                popupPrice.setText(ad.price);
                bCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                bConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        adDbHelper.makeAdUnavailable(ad);
                        buy.setTag(0);
                        checkTags();
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.add_comment:
                Comment comment = new Comment(owner.getText() + ": " + commentInput.getText(),
                        new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault()).format(new Date()));
                ad.addComment(comment);
                newComments= ad.comments;
                commentAdapter.addAll(newComments);
        }
    }
    private void checkTags(){
        if(favorite.getTag().equals(1)){
            favorite.setText("obriši iz omiljenih");
        }
        else{
            favorite.setText("Dodaj u omiljeno");
        }
        if (buy.getTag().equals(1)){
            buy.setText("kupi");
            buy.setEnabled(true);
        }
        else{
            buy.setText("prodato");
            buy.setEnabled(false);
        }
    }
}