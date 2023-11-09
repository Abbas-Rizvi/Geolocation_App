package com.example.geolocation;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    // list of all notes and filtered notes (for search)
    private ArrayList<Location> allLocations;
    private ArrayList<Location> filteredLocations;

    // listener for onClick functionality
    private SelectListener listener;


    // constructor
    // ------------
    public LocationAdapter(ArrayList<Location> locations, SelectListener listener) {
        allLocations = new ArrayList<>(locations); // Make a copy of the original list
        filteredLocations = new ArrayList<>(locations); // Initialize filteredNotes with allNotes

        this.listener = listener;
    }


    // creation of view holder, mapping to card fields
    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
        return new LocationViewHolder(view);
    }

    // binds the data fields from the filtered locations to the output view
    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Location location = filteredLocations.get(position);

        // bind holder to individual fields from location
        holder.addrLabel.setText(location.getAddress());
        holder.latLabel.setText("Lat: " + String.valueOf(location.getLatitude()));
        holder.longLabel.setText("Long: " + String.valueOf(location.getLongitude()));

        // gradient
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        holder.noteItemLayout.setBackground(drawable);

        // set the on click listener for each individual value
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(filteredLocations.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredLocations.size();
    }

    public void filter(String search) {
        filteredLocations.clear();
        if (search.isEmpty()) {
            filteredLocations.addAll(allLocations); // If the query is empty, show all notes
        } else {
            search = search.toUpperCase();
            for (Location location : allLocations) {
                if (location.getAddress().toUpperCase().contains(search)) {
                    filteredLocations.add(location);
                }
            }
        }
        notifyDataSetChanged(); // Notify the RecyclerView that the data has changed
    }
}
