package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class HireActivity extends AppCompatActivity {

    EditText txtName, txtPass, txtConfirm, txtRole;
    TextView txtUID;
    Button btnGenID, btnConfirm;
    String name, password, id, role, confirmPassword, username = "";
    EmployeeDBHelper db = new EmployeeDBHelper(this);

    //TODO: add error handling for password input
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }
        final String finalusername = username;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);
        txtName = findViewById(R.id.edtTextHName);
        txtPass = findViewById(R.id.edtTextPW);
        txtConfirm = findViewById(R.id.edtTextCPW);
        txtRole = findViewById(R.id.edtTextRole);
        txtUID = findViewById(R.id.txtUID);
        btnGenID = findViewById(R.id.btnGenID);
        btnConfirm = findViewById(R.id.btnConfirm);

        //rng for uid
        btnGenID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = "000001";
                do {
                    int id = new Random().nextInt(900000) + 100000;
                    ID = String.valueOf(id);
                } while (db.checkID(ID));
                txtUID.setText(ID);
            }
        });

        //add employee to table
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtName.getText().toString();
                password = txtPass.getText().toString();
                id = txtUID.getText().toString();
                role = txtRole.getText().toString();
                confirmPassword = txtConfirm.getText().toString();
                if (password.equals(confirmPassword)) { //if passwords match
                    if (!(name.equals("") || password.equals("") || id.equals("") || role.equals(""))) { //if all fields are not null
                        if (role.equals("Manager") || role.equals("Cashier")) { //TODO: change role to drop-down menu to remove this logic
                            Boolean insert = db.insertData(id, role, name, password);
                            if (insert) {
                                Toast.makeText(HireActivity.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
                                Intent managerIntent = new Intent(getApplicationContext(), ManagerActivity.class);
                                managerIntent.putExtra("username", finalusername);
                                startActivity(managerIntent);
                            } else
                                Toast.makeText(HireActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                        } else //if role is not manager/cashier
                            Toast.makeText(HireActivity.this, "Invalid Role", Toast.LENGTH_SHORT).show();
                    } else //if any field is null
                        Toast.makeText(HireActivity.this, "Please Accomplish All Fields", Toast.LENGTH_SHORT).show();
                } else // if passwords don't match
                    Toast.makeText(HireActivity.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
            }
        });
    }
}