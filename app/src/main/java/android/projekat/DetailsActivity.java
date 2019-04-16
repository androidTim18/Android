package android.projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        name.setText(i.getStringExtra("name"));
        breed.setText(i.getStringExtra("breed"));
        species.setText(i.getStringExtra("species"));
        birthday.setText(i.getStringExtra("birthday"));
        sex.setText(i.getStringExtra("sex"));
        location.setText(i.getStringExtra("location"));
        owner.setText(i.getStringExtra("owner"));
        info.setText(i.getStringExtra("info"));
        price.setText(i.getStringExtra("price"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bFavorite:
            //TODO Dodati na listu omiljenih
                break;
            case R.id.bBuy:
            //TODO Izbaciti sa liste
                break;
        }
    }
}