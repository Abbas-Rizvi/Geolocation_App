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

public class updateLocationPage extends AppCompatActivity {

    // gui object variables
    private EditText addrInput;
    private EditText latInput;
    private EditText longInput;
    private Button submitBtn;

    // access database
    private SQLiteManager db  = new SQLiteManager(this);

    // location id for element being edited
    private int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_edit);

        // bind input fields to gui
        addrInput = findViewById(R.id.addrTextEntry);
        latInput = findViewById(R.id.latTextEntry);
        longInput = findViewById(R.id.longTextEntry);


        // set default value using stored value
        // passed by intent
        Intent intent = getIntent();

        // initialize field values for edit
        addrInput.setText(intent.getStringExtra("addrVal"));
        latInput.setText(String.valueOf(intent.getFloatExtra("latVal", Float.parseFloat("0"))));
        longInput.setText(String.valueOf(intent.getFloatExtra("longVal", Float.parseFloat("0"))));

        // store location id for database use
        id = intent.getIntExtra("locID",-1);


    }

    // update location data
    public void updateLocationClick(View v){

        // verify all fields are filled
        if (addrInput.getText().toString().isEmpty() ||
                latInput.getText().toString().isEmpty() ||
                longInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please ensure all fields are filled", Toast.LENGTH_SHORT).show();
        } else {

            // create note object
            Location newLoc = new Location( id,
                    Float.parseFloat(latInput.getText().toString()),
                    Float.parseFloat(longInput.getText().toString()),
                    addrInput.getText().toString());

            // send to database
            db.updateDatabaseLocation(newLoc);

            // show success message
            Toast.makeText(this,"Location updated!",Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }

    public void deleteClick(View v){

        // pass id to db delete function
        db.deleteLocation(id);

        // notify user
        Toast.makeText(this, "Location was deleted!", Toast.LENGTH_SHORT).show();

        // return to main page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


}
