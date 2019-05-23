package android.projekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.graphics.BitmapFactory;
import java.io.ByteArrayInputStream;

import static android.projekat.ListAll.adDbHelper;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById(R.id.bAddAd).setOnClickListener(this);
        findViewById(R.id.bAddImg).setOnClickListener(this);
        isImgAdded = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bAddImg:

                photo = findViewById(R.id.add_img);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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

                    SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                    String owner = new String();
                    owner = ((SharedPreferences) preferences).getString("user", null);

                    Bitmap image = (Bitmap) BitmapFactory.decodeResource(getResources(), R.drawable.icon_paw);
                    if (isImgAdded == 1) {
                        image = bitmap;
                    }
                    else { }
                    
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();

                    //TODO: get location, send all info to server
                    Ad newAd = new Ad(species.getText().toString(), breed.getText().toString(), name.getText().toString(),
                            date.getText().toString(), sex.getText().toString(), "Novi Sad", owner,
                            info.getText().toString(), (price.getText().toString() + spinner.getSelectedItem().toString()),
                            true, false, imageInByte);
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

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
