package com.example.geolocation;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements SelectListener {

    //create variables for all elements on screen
    private RecyclerView locationRecycler;
    private LocationAdapter locationAdapter;  // adapter for recycler
    private SearchView searchBar;

    // create database variable
    private SQLiteManager db = new SQLiteManager(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind elements to variables
        searchBar = findViewById(R.id.searchAddress);

        // initialize recycler view
        locationRecycler = (RecyclerView) findViewById(R.id.locationRecycler);
        locationRecycler.setLayoutManager(new LinearLayoutManager(this));

        // open database connection
        db.open();

//        db.clearDB();
        // read in default values from text file
        // initial setup
        try {
            readInitValues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // create list of locations
        // pass to adapter
        ArrayList<Location> locItems = new ArrayList<>();
        locItems = viewLocations();

        locationAdapter = new LocationAdapter(locItems, this);
        locationRecycler.setAdapter(locationAdapter);

        // listener for search bar
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                locationAdapter.filter(newText);
                return true;
            }
        });



    }

    // function used on click of recycler item
    // navigate to edit page
    @Override
    public void onItemClick(Location location) {

        // create intent to store values
        Intent intent = new Intent(this, updateLocationPage.class);

        // put values into intent
        intent.putExtra("locID", location.getId());
        intent.putExtra("addrVal", location.getAddress());
        intent.putExtra("longVal", location.getLongitude());
        intent.putExtra("latVal", location.getLatitude());

        // launch edit page
        startActivity(intent);
    }

    private ArrayList<Location> viewLocations() {

        ArrayList<Location> allNotes = new ArrayList<Location>();
        Cursor cursor = db.getAllLocations();

        if (cursor != null) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(SQLiteManager.LocationEntry._ID));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteManager.LocationEntry.COLUMN_NAME_ADDRESS));
                String latitude = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteManager.LocationEntry.COLUMN_NAME_LATITUDE));
                String longitude = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteManager.LocationEntry.COLUMN_NAME_LONGITUDE));

                allNotes.add(new Location(id,Float.parseFloat(latitude), Float.parseFloat(longitude), address));
            }
            cursor.close();
        }
        return allNotes; // print the notes from db
    }

    public void addLocation(View v){
        Intent i = new Intent(this, addLocationPage.class);
        startActivity(i);

    }

    // function used to load initial values
    // opens file containing 50 latitude and longitude pairs
    public void readInitValues() throws IOException {
        try {
            Context context = this;

            // create streams to raed from default file
            InputStream inputStream = context.getResources().openRawResource(R.raw.init);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            Scanner scan = new Scanner (inputStream);
            String line;

            // create geocode helper object
            GeocodeHelper geocodeHelper = new GeocodeHelper(context);

            // while program is reading data
            while (scan.hasNextLine()) {

                line = scan.nextLine();
                // split values in row by comma
                String[] coordinates = line.split(",");
//                Log.e(TAG, String.valueOf(coordinates.length));


                if (coordinates.length == 2) {
                    // read latitude longitude into variables
                    float latitude = Float.parseFloat(coordinates[0].trim());
                    float longitude = Float.parseFloat(coordinates[1].trim());

                    // Get the location object for coordinates
                    Location loc = geocodeHelper.getAddress(latitude, longitude);

                    if (loc != null) {


//                         check if already exists in database
                        if (!db.exists(loc)) {
                            // Insert the data into the database
                            Log.e(TAG, "Added "+ Arrays.toString(coordinates));
                            db.addDatbabaseLocation(loc);
                        } else {
                            Log.e(TAG, "Geocoding failed for latitude " + latitude + " and longitude " + longitude + ", already exists");
                        }

                    }
                }
//                reader.close();
            }
        } catch (Resources.NotFoundException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }
}