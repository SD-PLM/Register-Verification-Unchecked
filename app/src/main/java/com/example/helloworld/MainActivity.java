package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    EditText txtID, txtPassword;
    Button btnLogIn;
    String ID, password;
    EmployeeDBHelper db = new EmployeeDBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db.adminData();
        txtID = findViewById(R.id.edtTxtID);
        txtPassword = findViewById(R.id.edtTxtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);

        //log in code
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = txtID.getText().toString();
                password = txtPassword.getText().toString();
                if (!(ID.equals("") || password.equals(""))) { //if both fields are not null
                    if (db.checkID(ID)){ //if ID exists in database
                        if (db.checkIDPassword(ID, password)) { //if username and password match
                            Toast.makeText(MainActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                            String name = db.getName(ID);
                            String role = db.getRole(ID);
                            if (role.equals("admin")|| role.equals("Manager")){
                                Intent managerIntent = new Intent(getApplicationContext(), ManagerActivity.class);
                                managerIntent.putExtra("username", name);
                                startActivity(managerIntent);
                            } else if (role.equals("Cashier")){
                                Intent cashierIntent = new Intent(getApplicationContext(), CashierActivity.class);
                                cashierIntent.putExtra("username", name);
                                startActivity(cashierIntent);
                            } else if (role.equals("error"))
                                Toast.makeText(MainActivity.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
                        } else //if ID and password does not match
                            Toast.makeText(MainActivity.this, "ID and Password does not Match", Toast.LENGTH_SHORT).show();
                    } else //if username is not in database
                        Toast.makeText(MainActivity.this, "Employee does not Exist", Toast.LENGTH_SHORT).show();
                } else if (ID.equals("")) { //if name field is null
                    Toast.makeText(MainActivity.this, "Employee ID cannot be Blank", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")){ //if password field is null
                    Toast.makeText(MainActivity.this, "Password cannot be Blank", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
