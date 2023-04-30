package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManagerActivity extends AppCompatActivity {
    TextView txtWelcome;
    Button btnCashier, btnFire, btnStocks, btnHire;
    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        txtWelcome = findViewById(R.id.txtWelcome);
        btnCashier = findViewById(R.id.btnCashier);
        btnFire = findViewById(R.id.btnFire);
        btnStocks = findViewById(R.id.btnStocks);
        btnHire = findViewById(R.id.btnHire);
        txtWelcome.setText("Hello Manager "+username+"!");

        //link to hire activity
        btnHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hireIntent = new Intent(getApplicationContext(), HireActivity.class);
                hireIntent.putExtra("username", username);
                startActivity(hireIntent);
            }
        });

        //link to fire activity
        btnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fireIntent = new Intent(getApplicationContext(), FireActivity.class);
                fireIntent.putExtra("username", username);
                startActivity(fireIntent);
            }
        });
    }
}