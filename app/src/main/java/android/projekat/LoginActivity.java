package android.projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText email;
    public EditText password;
    public Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.bLoginA).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        email = findViewById(R.id.logEmailInput);
        password = findViewById(R.id.logPasswordInput);

        if (email.getText().toString().isEmpty() || !email.getText().toString().contains("@"))
        {
            toast = Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (password.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            //TODO: If Http response ok, change email tousername, block anyone from reg as admin
            SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
            ((SharedPreferences.Editor) editor).putString("user", email.getText().toString());
            ((SharedPreferences.Editor) editor).apply();
            Intent intent = new Intent(this, AdListActivity.class);
            startActivity(intent);
        }

        }
    }
