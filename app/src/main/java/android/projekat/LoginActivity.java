package android.projekat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.prefs.Preferences;

import static android.projekat.MainActivity.userDbHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText email;
    public EditText password;
    public Toast toast;
    public HttpHelper httpHelper;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.bLoginA).setOnClickListener(this);
        httpHelper = new HttpHelper();
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
        else if (userDbHelper.readUserByEmail(email.getText().toString())== null)
        {
            toast = Toast.makeText(this, "User not registered.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (password.getText().toString().isEmpty())
        {
            toast = Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT);
            toast.show();
        }
        else

        {
            login();
            User user = userDbHelper.readUserByEmail(email.getText().toString());
            Log.i("ulogovani", user.fullName);
            SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
            ((SharedPreferences.Editor) editor).putString("userFullName", user.getFullName());
            ((SharedPreferences.Editor) editor).putString("userId", user.getUserId());
            ((SharedPreferences.Editor) editor).apply();

            Toast.makeText(LoginActivity.this, "Uspesno logovanje uz pomoc lokalne baze podataka.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AdListActivity.class);

            email.setText("");
            password.setText("");
            startActivity(intent);
        }

        }

        private void login(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", email.getText().toString());
                        jsonObject.put("password", password.getText().toString());

                        final boolean res = httpHelper.postJSONObjectFromURL(AdListActivity.BASE_URL + "/login", jsonObject);

                        mHandler.post(new Runnable(){
                            public void run() {
                                if (res) {
                                    Toast.makeText(LoginActivity.this, "Uspesno logovanje!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), AdListActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
