package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FireActivity extends AppCompatActivity {

    EditText txtUID;
    TextView txtName, txtRole;
    Button btnSearch, btnContinue, btnBack;
    EmployeeDBHelper db = new EmployeeDBHelper(this);
    String employeeID = "", employeeRole = "", employeeName = "", username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }
        final String finalusername = username;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire);
        txtUID = findViewById(R.id.edtTxtUID);
        txtName = findViewById(R.id.txtEName);
        txtRole = findViewById(R.id.txtERole);
        btnSearch = findViewById(R.id.btnSearch);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);

        //search for employee
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeID = txtUID.getText().toString();
                if (db.checkID(employeeID)){ //if employee exists in table
                    employeeRole = db.getRole(employeeID);
                    txtRole.setText(employeeRole);
                    employeeName = db.getName(employeeID);
                    txtName.setText(employeeName);
                } else
                    Toast.makeText(FireActivity.this, "Employee does not Exist", Toast.LENGTH_SHORT).show();
            }
        });

        //remove employee from table
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(employeeID.equals("")||employeeName.equals("")||employeeRole.equals(""))){ //if all fields are not null
                    db.removeData(employeeID);
                    Toast.makeText(FireActivity.this, "Successfully Removed!", Toast.LENGTH_SHORT).show();
                    Intent managerIntent = new Intent(getApplicationContext(), ManagerActivity.class);
                    managerIntent.putExtra("username", finalusername);
                    startActivity(managerIntent);
                } else
                    Toast.makeText(FireActivity.this, "Please Accomplish All Fields", Toast.LENGTH_SHORT).show();
            }
        });

        //back to manager activity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FireActivity.this, "Remove Cancelled", Toast.LENGTH_SHORT).show();
                Intent managerIntent = new Intent(getApplicationContext(), ManagerActivity.class);
                managerIntent.putExtra("username", finalusername);
                startActivity(managerIntent);
            }
        });

    }
}