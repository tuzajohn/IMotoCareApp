package com.uh4t.imotocareapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IndividualViewActivity extends AppCompatActivity {

    private Intent _intent;
    private ImageView _imgView;
    private TextView _name, _garageNameTextView, _GarageAddress, _contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _intent = getIntent();
        String _peronleId = _intent.getStringExtra("personeid");
        String _personelName = _intent.getStringExtra("_personelName");
        String _garageName = _intent.getStringExtra("_garageName");
        String _garageAddress = _intent.getStringExtra("_garageAddress");
        String _contactNumber = _intent.getStringExtra("_contact");
        String _imgUrl = _intent.getStringExtra("_imgUrl");


        _imgView = findViewById(R.id.imageView);
        _name = findViewById(R.id.person_name);
        _garageNameTextView =findViewById(R.id.garage_name);
        _GarageAddress = findViewById(R.id.contact);
        _contact = findViewById(R.id.contact);


        _name.setText(_personelName);
        _garageNameTextView.setText(_garageName);
        _GarageAddress.setText(_garageAddress);
        _contact.setText(_contactNumber);

        Picasso.with(IndividualViewActivity.this).load(_imgUrl).into(_imgView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
