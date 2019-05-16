package android.projekat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bLogin).setOnClickListener(this);
        findViewById(R.id.bJoin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
            case R.id.bJoin:
                i = new Intent(this, RegisterActivity.class);
                startActivity(i);
        }
    }
}

