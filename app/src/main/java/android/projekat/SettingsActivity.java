package android.projekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;

import static android.projekat.MainActivity.userDbHelper;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    RadioButton rbGreen;
    RadioButton rbYellow;
    RadioButton rbDark;
    RatingBar ratingBar;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button = findViewById(R.id.bSave);
        rbGreen = findViewById(R.id.rbGreen);
        rbYellow = findViewById(R.id.rbYellow);
        rbDark = findViewById(R.id.rbDark);
        ratingBar = findViewById(R.id.ratingBar);
        button.setOnClickListener(this);


        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        User user = userDbHelper.readUserById(((SharedPreferences) preferences).getString("userId", null));


        if(user.rated != null){
            ratingBar.setRating(user.getRating());
        }

        if (((preferences).getString("theme", null) == "1")) {
            rbGreen.setChecked(true);
        }
        else if(((preferences).getString("theme", null) == "2")) {
            rbYellow.setChecked(true);
        }
        else if(((preferences).getString("theme", null) == "3")) {
            rbDark.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSave:

                String id = ((SharedPreferences) preferences).getString("userId", null);
                userDbHelper.rate(userDbHelper.readUserById(id), ratingBar.getRating());
                SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();;
                if (rbDark.isSelected()) {
                    setTheme(R.style.AppThemeNavy);
                    ((SharedPreferences.Editor) editor).putString("theme", "3");
                }
                else if(rbYellow.isSelected()){
                    setTheme(R.style.AppThemeYellow);
                    ((SharedPreferences.Editor) editor).putString("theme","2");
                }
                else if(rbGreen.isSelected()){
                    setTheme(R.style.AppTheme);
                    ((SharedPreferences.Editor) editor).putString("theme","1");
                }
                Intent i = new Intent(this, AdListActivity.class);
                startActivity(i);
                break;
        }
    }
}
