package com.example.myinventoryapp;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {


    PopupWindow Modal;
    Activity activity;
    Button Login, Registration, ForgotPassword;
    EditText Email, Password;
    String endUser, phoneNumber, EndUserEmail, UserPassword;
    Boolean EmptyText;

    android.database.sqlite.SQLiteDatabase db;
    SQLiteDatabaseEndUser handler;
    String password = "Invalid Password" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        Login = findViewById(R.id.signinButton);
        Registration = findViewById(R.id.registerButton);
        ForgotPassword = findViewById(R.id.forgotPasswordButton);
        Email = findViewById(R.id.emailAddress);
        Password = findViewById(R.id.password);
        handler = new SQLiteDatabaseEndUser(this);

        // on click listener
        Login.setOnClickListener(view -> {
            Login();
        });

        // on click listener for registration button
        Registration.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
        });

        // on click listener when user forgets password button
        ForgotPassword.setOnClickListener(view -> {
            EndUserEmail = Email.getText().toString().trim();
        // If the end user email is left blank a toast message will pop up
            if (!EndUserEmail.isEmpty()) {
                forgotPassword();
            } else {
                Toast.makeText(Login.this, "Email address cant be empty", Toast.LENGTH_LONG).show();
            }
        });
    }

    // End User login functionality
    public void Login() {
        String text = emptyFields();

        if(!EmptyText) {
            db = handler.getWritableDatabase();

            // cursor for email search
            Cursor cursor = db.query(SQLiteDatabaseEndUser.TABLE, null, " " + SQLiteDatabaseEndUser.COL_EMAIL + "=?", new String[]{EndUserEmail}, null, null, null);

            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();

                    // The end user, password, phone number will be stored.
                    endUser = cursor.getString(cursor.getColumnIndex(SQLiteDatabaseEndUser.COL_NAME));
                    phoneNumber = cursor.getString(cursor.getColumnIndex(SQLiteDatabaseEndUser.COL_PHONE));
                    password = cursor.getString(cursor.getColumnIndex(SQLiteDatabaseEndUser.COL_PASSWORD));

                    // Close out the cursor
                    cursor.close();
                }
            }
            handler.close();

            // Check the result
            Result();
        } else {
            //Empty login fields will result in toast
            Toast.makeText(Login.this, text, Toast.LENGTH_LONG).show();
        }
    }

    // Method to check for empty fields.
    public String emptyFields() {
        String text = "";
        EndUserEmail = Email.getText().toString().trim();
        UserPassword = Password.getText().toString().trim();

        if (EndUserEmail.isEmpty()){
            Email.requestFocus();
            EmptyText = true;
            text = "Email cant be empty.";
        } else if (UserPassword.isEmpty()){
            Password.requestFocus();
            EmptyText = true;
            text = "Password cant be empty.";
        } else {
            EmptyText = false;
        }
        return text;
    }

    // Verify end user credentials match the database
    public void Result(){
        if(password.equalsIgnoreCase(UserPassword)) {
            Toast.makeText(Login.this,"Successfull login",Toast.LENGTH_SHORT).show();

            // bundle credentials
            Bundle credentials = new Bundle();
            credentials.putString("endUser", endUser);
            credentials.putString("email", EndUserEmail);
            credentials.putString("phoneNumber", phoneNumber);

            // Successful login will result to the item screen
            Intent Login = new Intent(Login.this, List.class);
            Login.putExtras(credentials);
            startActivity(Login);
            EmptyText();
        } else {
            // Toast message for incorrect credentials
            Toast.makeText(Login.this,"Incorrect Login Details",Toast.LENGTH_LONG).show();
        }
        password = "Invalid Password" ;
    }

    // method to clear text
    public void EmptyText() {
        Email.getText().clear();
        Password.getText().clear();
    }

    public void forgotPassword() {
        LayoutInflater layout1 = activity.getLayoutInflater();
        View view = layout1.inflate(R.layout.activity_forgot_password, activity.findViewById(R.id.popup_element));

        Modal = new PopupWindow(view, 900, 900, true);
        Modal.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Will write to the database
        db = handler.getWritableDatabase();

        // Query the email
        Cursor cursor = db.query(SQLiteDatabaseEndUser.TABLE, null, " " + SQLiteDatabaseEndUser.COL_EMAIL + "=?", new String[]{EndUserEmail}, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();

                // Stores the end user phone number and password.
                phoneNumber = cursor.getString(cursor.getColumnIndex(SQLiteDatabaseEndUser.COL_PHONE));
                password = cursor.getString(cursor.getColumnIndex(SQLiteDatabaseEndUser.COL_PASSWORD));

                // Closing cursor.
                cursor.close();
            }
        }
        handler.close();


    }
}
