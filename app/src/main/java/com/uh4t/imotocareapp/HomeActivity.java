package com.uh4t.imotocareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button _mechanicBtn;
    private Button _breakDownBtn;
    private Intent _intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        _mechanicBtn = (Button) findViewById(R.id.MechanicBtn);
        _breakDownBtn = (Button) findViewById(R.id.BreakDownBtn);


        _mechanicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _intent = new Intent(HomeActivity.this, MainActivity.class);
                _intent.putExtra("type", "mechanic");
                startActivity(_intent);
            }
        });
        _breakDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _intent = new Intent(HomeActivity.this, MainActivity.class);
                _intent.putExtra("type", "breakdown");
                startActivity(_intent);
            }
        });

    }
}
