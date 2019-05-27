package android.projekat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;

import static android.projekat.ListAll.adDbHelper;
import static android.projekat.MainActivity.userDbHelper;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    static int GALLERY_REQUEST = 1;
    public Bitmap bitmap;
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
    public Drawable drawable;
    protected int isImgAdded;
    String cityname;
    TextView location;
    EditText inputLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById(R.id.bAddAd).setOnClickListener(this);
        findViewById(R.id.bAddImg).setOnClickListener(this);
        findViewById(R.id.bAddCurrentLoc).setOnClickListener(this);
        findViewById(R.id.bAddLocationManual).setOnClickListener(this);
        inputLocation = findViewById(R.id.inputLocation);
        isImgAdded = 0;
        location = findViewById(R.id.add_location);
        location.setText("");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAddImg:

                photo = findViewById(R.id.add_img);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                break;

            case R.id.bAddLocationManual:
                if(inputLocation.getText().toString() != "") {
                    location.setText(inputLocation.getText());
                }
                else{

                    toast = Toast.makeText(this, "Unesite lokaciju.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            case R.id.bAddCurrentLoc:
                location.setText(getCurrentCity());
                break;

            case R.id.bAddAd:

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
                location = findViewById(R.id.add_location);

                if (species.getText().toString().isEmpty()) {
                    toast = Toast.makeText(this, "Species is required.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (breed.getText().toString().isEmpty()) {
                    toast = Toast.makeText(this, "Breed is required.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (price.getText().toString().isEmpty()) {
                    toast = Toast.makeText(this, "Price is required.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(this, AdListActivity.class);

                    SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                    User user = userDbHelper.readUserById(((SharedPreferences) preferences).getString("userId", null));

                    Bitmap image = (Bitmap) BitmapFactory.decodeResource(getResources(), R.drawable.icon_paw);
                    if (isImgAdded == 1) {
                        image = bitmap;
                    } else {
                    }

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();

                    //TODO:send all info to server

                    Ad newAd = new Ad(species.getText().toString().toLowerCase(),
                            breed.getText().toString().toLowerCase(), name.getText().toString(),
                            date.getText().toString(), sex.getText().toString(),
                            location.getText().toString(), user.getFullName(), info.getText().toString(),
                            (price.getText().toString() + spinner.getSelectedItem().toString()),
                            1, 0, imageInByte);
                    adDbHelper.insert(newAd);
                    toast = Toast.makeText(this, "Uspešno ste dodali oglas!", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                }

                break;

        }

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                photo.setImageBitmap(bitmap);
                isImgAdded = 1;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    protected String getCurrentCity() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationManager.getBestProvider(criteria, true);
        List<Address> addresses;
        cityname = new String();
        cityname = "Nepoznata lokacija";
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if(addresses.size()>0){
                    cityname = addresses.get(0).getLocality();
                return cityname;
                }
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                toast = Toast.makeText(this, "Neuspešno lociranje.", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        Log.i("Location", cityname);
        return cityname;
    }
}
