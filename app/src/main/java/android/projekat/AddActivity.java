package android.projekat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    public EditText name;
    public EditText breed;
    public EditText species;
    public EditText price;
    public EditText sex;
    public EditText date;
    public EditText info;
    public Spinner spinner;
    public ImageView photo;
    public Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById(R.id.bAddAd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        name = findViewById(R.id.add_name);
        breed = findViewById(R.id.add_bread);
        species = findViewById(R.id.add_species);
        name = findViewById(R.id.add_name);
        sex = findViewById(R.id.add_sex);
        date = findViewById(R.id.add_date);
        price = findViewById(R.id.add_price);
        spinner = findViewById(R.id.spinner);
        photo = findViewById(R.id.add_img);
        info = findViewById(R.id.add_info);

        if (species.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Species is required.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (breed.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Breed is required.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else  if (price.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Price is required.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Intent intent = new Intent(this, AdListActivity.class);

            intent.putExtra("species", species.getText().toString());
            intent.putExtra("breed", breed.getText().toString());
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("birthday", date.getText().toString());
            intent.putExtra("sex", sex.getText().toString());
            intent.putExtra("price", price.getText().toString() + spinner.getSelectedItem().toString());
            intent.putExtra("availabile", true);
            intent.putExtra("info", info.getText().toString());
            intent.putExtra("dateAdded", new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault()).format(new Date()));
            intent.putExtra("favorite", false);
            intent.putExtra("owner", "Vlasnik");
            intent.putExtra("location", "Lokacija");

            startActivity(intent);
        }

    }
}
