package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
//TODO: figure out the picture in the main menu of nav view
public class CashierActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SalesFragment()).commit();
        }
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_sales:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SalesFragment()).commit();
                        break;
                    case R.id.nav_receipts:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReceiptsFragment()).commit();
                        break;
                    case R.id.nav_shift:
                        Toast.makeText(CashierActivity.this, "Shift is Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_items:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItemFragment()).commit();
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(CashierActivity.this, "Settings is Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_backoffice:
                        Toast.makeText(CashierActivity.this, "Back Office is Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_apps:
                        Toast.makeText(CashierActivity.this, "Apps is Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(logoutIntent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}