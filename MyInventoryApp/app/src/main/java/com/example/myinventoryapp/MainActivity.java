//Matthew Clockel
//28 September 2021
//CS360 - 21EW1
//Professor Godbold

package com.example.myinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText nameText;
    private TextView textGreeting;
    private Button buttonSayHello;

    // When say hello button is clicked, Hello and person name is set as the text greeting.
    // Since the button is disabled the text you must enter a name does not show up.
    // If the button was not disabled, this function works.
    public void buttonSayHello(View dynamicButton){
        if (nameText.getText().toString().equals("")){
            textGreeting.setText("You must enter a name"); // Text greeting
        }else {
            textGreeting.setText("Hello "+ nameText.getText().toString()); // output of text greeting if name is input.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Three instances for the MainActivity class
        nameText = (EditText) findViewById(R.id.nameText);
        textGreeting = (TextView) findViewById(R.id.textGreeting);
        buttonSayHello = (Button) findViewById(R.id.buttonSayHello);
        // This disables the say hello button when app starts.
        buttonSayHello.setEnabled(false);

        // Adds text changed listener to the nameText variable
        //Call back created with TextWatcher to call before, during, and after text changes.
        // Reference 2.6.3: Listening for changes in an EditText ZyBooks
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                // If statements that enables or disables the say hello button
                if (s.toString().equals("")){ // this disables the button
                    buttonSayHello.setEnabled(false);
                } else {
                    buttonSayHello.setEnabled(true); // this enables the button
                }
            }
        });
    }
}