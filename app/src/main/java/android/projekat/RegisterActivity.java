package android.projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText firstName;
    public EditText lastName;
    public EditText email;
    public EditText username;
    public EditText password;
    public Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.bRegisterA).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        firstName = findViewById(R.id.regNameInput);
        lastName = findViewById(R.id.regLastNameInput);
        email = findViewById(R.id.regEmailInput);
        username = findViewById(R.id.regUsernameInput);
        password = findViewById(R.id.regPasswordInput);

        if (firstName.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "First name is required.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (lastName.getText().toString().isEmpty())
        {
                toast = Toast.makeText(this, "Last name is required.", Toast.LENGTH_SHORT);
                toast.show();
        }
        else  if (email.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Email is required.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else  if (!email.getText().toString().contains("@"))
        {
            toast = Toast.makeText(this, "Invalid email.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (username.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Username is required.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (password.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Password is required.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (password.getText().toString().length() < 6)
        {
            toast = Toast.makeText(this, "Password should be at least 6 characters.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Intent intent = new Intent(this, AdListActivity.class);
            startActivity(intent);
        }

    }
}
