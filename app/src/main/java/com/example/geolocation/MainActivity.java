package com.example.geolocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SelectListener {

    //create variables for all elements on screen
    private FloatingActionButton addLocationBtn;
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
        addLocationBtn = findViewById(R.id.addLocationButton);
        searchBar = findViewById(R.id.searchAddress);

        // initialize recycler view
        locationRecycler = (RecyclerView) findViewById(R.id.locationRecycler);
        locationRecycler.setLayoutManager(new LinearLayoutManager(this));

        // open database connection
        db.open();

        // create list of locations
        // pass to adapter
        ArrayList<Location> locItems = new ArrayList<>();
        locItems = viewLocations();

        locationAdapter = new LocationAdapter(locItems, this);
        locationRecycler.setAdapter(locationAdapter);


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
}