package com.example.geolocation;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.location.Geocoder;
import android.location.Address;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class GeocodeHelper {


    // object for geocoder
    private Geocoder geocoder;

    // constructor
    // pass context to create object
    public GeocodeHelper (Context context) {
        geocoder = new Geocoder(context);
    }

    // function for calculating address using geocoding
    public Location getAddress(float latitude, float longitude){


        try {
            // get list of addresses
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            // if not emptty or null
            if (addresses != null && !addresses.isEmpty()) {

                //get first address
                Address address = addresses.get(0);

                // create location object
                Location location = new Location(latitude, longitude, address.getAddressLine(0));

                //return location to main
                return location;
            }

            if (addresses.isEmpty()){
                Log.e(TAG, "Geocoding failed for latitude " + latitude + " and longitude " + longitude + ", nothing found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    // Function to read input file and use Geocoding to find 50 addresses input latitude and longitude pairs
    public String getAddressFromCoordinates(double latitude, double longitude) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0); // Get the first address line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
