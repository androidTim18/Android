package android.projekat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

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
    Ad ad = new Ad();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findViewById(R.id.bFavorite).setOnClickListener(this);
        findViewById(R.id.bBuy).setOnClickListener(this);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bFavorite:
                adDbHelper.makeAdFavorite(ad);
                break;
            case R.id.bBuy:
                adDbHelper.makeAdUnavailable(ad);
                break;
        }
    }
}