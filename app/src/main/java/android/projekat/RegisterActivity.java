package android.projekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.projekat.MainActivity.userDbHelper;

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
        else  if (userDbHelper.readUserByEmail(email.getText().toString())!= null)
        {
            toast = Toast.makeText(this, "Email must be unique.", Toast.LENGTH_SHORT);
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
        else  if (userDbHelper.isUserNameUnique(email.getText().toString())!= null)
        {
            toast = Toast.makeText(this, "Username must be unique.", Toast.LENGTH_SHORT);
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
            User u = new User(firstName.getText().toString(), lastName.getText().toString(),
                    username.getText().toString(), email.getText().toString());
            SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
            ((SharedPreferences.Editor) editor).putString("userFullName", u.getFullName());
            ((SharedPreferences.Editor) editor).putString("userId", u.getUserId());
            ((SharedPreferences.Editor) editor).apply();
            userDbHelper.insert(u);
            Intent intent = new Intent(this, AdListActivity.class);
            startActivity(intent);
        }

    }
}
