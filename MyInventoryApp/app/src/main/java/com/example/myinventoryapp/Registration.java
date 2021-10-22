package com.example.myinventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Registration extends AppCompatActivity {

    Button NewRegistration, CancelButton;
    EditText EndUser, EndUserPhoneNumber, EndUserEmail, EndUserPassword;
    Boolean noInput;
    android.database.sqlite.SQLiteDatabase db;
    SQLiteDatabaseEndUser sqliteDatabaseEndUser;
    String Output = "Empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EndUser = findViewById(R.id.userName);
        EndUserPhoneNumber = findViewById(R.id.phoneNumber);
        EndUserEmail = findViewById(R.id.emailAddress1);
        EndUserPassword = findViewById(R.id.password);
        NewRegistration = findViewById(R.id.register);
        CancelButton = findViewById(R.id.cancelButton);
        sqliteDatabaseEndUser = new SQLiteDatabaseEndUser(this);

        // On click listener if end user forgets password
        NewRegistration.setOnClickListener(view -> {
            String text = EmptyFields();

            if (!noInput) {
                // If end user email is in the database with no password
                EndUserIsRegistered();
                // Clears field once in database.
                Clear();
            } else {
                // Toast for empty fields
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            }
        });

        // On click listener for cancel button.
        CancelButton.setOnClickListener(view -> {
            // If cancel button is hit during registration the login activity will be displayed.
            startActivity(new Intent(Registration.this, Login.class));
            this.finish();
        });

    }

    // New end user registration
    public void registerEndUser(){
        String endUserName = EndUser.getText().toString().trim();
        String endUserPhoneNumber = EndUserPhoneNumber.getText().toString().trim();
        String endUserEmail = EndUserEmail.getText().toString().trim();
        String endUserPassword = EndUserPassword.getText().toString().trim();

        EndUser endUser = new EndUser(endUserName, endUserPhoneNumber, endUserEmail, endUserPassword);
        sqliteDatabaseEndUser.createUser(endUser);

        // Toast message for a successful end user registration
        Toast.makeText(Registration.this,"Registration successful", Toast.LENGTH_LONG).show();

        // Once the end user successfully registers the login screen will be displayed next.
        startActivity(new Intent(Registration.this, Login.class));
        this.finish();
    }

    // Checks for empty fields.
    public String EmptyFields() {
        String text = "";
        String endUserName = EndUser.getText().toString().trim();
        String endUserPhoneNumber = EndUserPhoneNumber.getText().toString().trim();
        String endUserEmail = EndUserEmail.getText().toString().trim();
        String endUserPassword = EndUserPassword.getText().toString().trim();

        if (endUserName.isEmpty()) {
            EndUser.requestFocus();
            noInput = true;
            text = "User Name cant be empty.";
        } else if (endUserPhoneNumber.isEmpty()){
            EndUserPhoneNumber.requestFocus();
            noInput = true;
            text = "Phone number cant be empty.";
        } else if (endUserEmail.isEmpty()){
            EndUserEmail.requestFocus();
            noInput = true;
            text = "Email address cant be empty.";
        } else if (endUserPassword.isEmpty()){
            EndUserPassword.requestFocus();
            noInput = true;
            text = "Password cant be empty.";
        } else {
            noInput = false;
        }
        return text;
    }

    // Check if end user email is in the database.
    public void EndUserIsRegistered(){
        String endUserEmail = EndUserEmail.getText().toString().trim();
        db = sqliteDatabaseEndUser.getWritableDatabase();

        // email query in the database
        Cursor cursor = db.query(SQLiteDatabaseEndUser.TABLE, null, " " + SQLiteDatabaseEndUser.COL_EMAIL + "=?", new String[]{endUserEmail}, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // output for email if exists in the database
                Output = "Email exists in the database.";
                cursor.close();
            }
        }
        sqliteDatabaseEndUser.close();
        // insert the data into database.
        Credentials();
    }

    // Credential verification
    public void Credentials(){
        // Email check
        if(Output.equalsIgnoreCase("Email address has be found!"))
        {
            // Toast message for email already in database
            Toast.makeText(Registration.this,"Email is assigned to a user.",Toast.LENGTH_LONG).show();
        }
        else {
            // Register user if no email in the database.
            registerEndUser();
        }
        Output = "Empty" ;
    }

    // Clear text once registered.
    public void Clear(){
        EndUser.getText().clear();
        EndUserPhoneNumber.getText().clear();
        EndUserEmail.getText().clear();
        EndUserPassword.getText().clear();
    }

}