package com.example.geolocation;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class addLocationPage extends AppCompatActivity {

    private EditText addrInput;
    private EditText latInput;
    private EditText longInput;

    private Button submitBtn;
    private SQLiteManager db  = new SQLiteManager(this);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_add);

        addrInput = findViewById(R.id.addrTextEntry);
        latInput = findViewById(R.id.latTextEntry);
        longInput = findViewById(R.id.longTextEntry);


    }

    public void submitLocationClick(View v){

        // verify all fields are filled
        if (addrInput.getText().toString().isEmpty() ||
            latInput.getText().toString().isEmpty() ||
            longInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please ensure all fields are filled", Toast.LENGTH_SHORT).show();
        } else {

            // create note
            Location newLoc = new Location( Float.parseFloat(latInput.getText().toString()),
                                            Float.parseFloat(longInput.getText().toString()),
                                            addrInput.getText().toString());

            // send to database
            db.addDatbabaseLocation(newLoc);

            // show success message
            Toast.makeText(this,"Location added!",Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }



}
