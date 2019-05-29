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

    RadioButton rbSettingsOrig;
    RadioButton rbSettingsDark;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button = findViewById(R.id.bSave);
        rbSettingsOrig = findViewById(R.id.rbSettingsOrig);
        rbSettingsDark = findViewById(R.id.rbSettingsDark);
        ratingBar = findViewById(R.id.ratingBar);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSave:

                SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                String id = ((SharedPreferences) preferences).getString("userId", null);
                userDbHelper.rate(userDbHelper.readUserById(id), ratingBar.getNumStars());
                Intent i = new Intent(this, AdListActivity.class);
                startActivity(i);
                break;
        }
    }
}
