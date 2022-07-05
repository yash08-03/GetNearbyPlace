package com.example.mynotes;

import android.Manifest;
// Every app project must have an AndroidManifest. xml file (with precisely that name) at the root of the project source set. The manifest file describes essential information about your app to the Android build tools, the Android operating system, and Google Play
import android.app.Application;
// The Application class in Android is the base class within an Android app that contains all other components such as activities and services.
import android.app.PendingIntent;
// By giving a PendingIntent to another application, you are granting it the right to perform the operation you have specified 
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
// Class for retrieving various kinds of information related to the application packages that are currently installed on the device.
import android.graphics.Color;
// The Color class provides methods for creating, converting and manipulating colors
import android.location.Location;
// A data class representing a geographic location
import android.os.AsyncTask;
// An asynchronous task is defined by a computation that runs on a background thread and whose result is published on the UI thread.
import android.os.Build;
// Information about the current build, extracted from system properties.
import android.util.Log;
import android.widget.ArrayAdapter;
// The Adapter acts as a bridge between the UI Component and the Data Source. It converts data from the data sources into view items that can be displayed into the UI Component. Data Source can be Arrays, HashMap, Database, etc. and UI Components can be ListView, GridView, Spinner, etc. 
import androidx.annotation.NonNull;
// Denotes that a parameter, field or method return value can never be null. This is a marker annotation and it has no specific attributes
import androidx.core.app.ActivityCompat;
// Helper for accessing features in android.app.Activity.
import androidx.core.content.ContextCompat;
// Helper for accessing features in Context.

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
// A JSONArray is an ordered sequence of values. It provides methods to access values by index and to put values
import org.json.JSONException;
// Utility classes of org.json throws JSONException in case of invalid JSON.
import org.json.JSONObject;
// JSONObject class is a unordered collection of key-value pairs. It provides methods to access values by key and to put values. 
import java.io.IOException;
// This exception is related to Input and Output operations in the Java code. It happens when there is a failure during reading, writing, and searching file or directory operations
import java.util.ArrayList;

public class GetNearbyPlacesData extends AsyncTask<Object,String,String>{ //ContextWrapper {

    private static final String TAG = "MapsActivity";
//    public GetNearbyPlacesData(Context base) {
//        MapsActivity.Con(base);
//    }
    String googlePlacesData;
    GoogleMap googleMap;
    String url;
    String name;
    LatLng latLng;
    private float GEOFENCE_RADIUS=100;
    private String GEOFENCE_ID="SOME_GEOFENCE_ID";
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE=10002;
    static ArrayList<String> lat= new ArrayList<String>();
    static ArrayList<String> lng= new ArrayList<String>();




//    GeofenceHelper geofenceHelper;
//    MapsActivity mapsActivity=new MapsActivity();
//    public GoogleMap mMap;
    //GoogleMap googleMap;

    @Override
    protected String doInBackground(Object... objects) { //standard notation
        googleMap=(GoogleMap)objects[0];
        url = (String) objects[1]; //1st index

        DownloadUrl downloadUrl=new DownloadUrl(); 
        try  {
            googlePlacesData = downloadUrl.readUrl(url);
            System.out.println("****************************************googlePLacesData = "+googlePlacesData);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return googlePlacesData; 
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultArray = parentObject.getJSONArray("results");
            System.out.println("****************************************INSIDE OnPOSTeXECUTE resultArray.size() = " + resultArray.length());
            for(int i=0;i<resultArray.length();i++)
            {
                JSONObject jsonObject = resultArray.getJSONObject(i);
                JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");

                String latitude = locationObj.getString("lat");
                String longitude = locationObj.getString("lng");

                JSONObject nameObject = resultArray.getJSONObject(i);

                name = nameObject.getString("name");

                latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                //MarkerOptions markerOptions = new MarkerOptions();
                //markerOptions.title(name);
                //markerOptions.position(latLng);
                    /////////////////////////////// ye latlng mil gayi hai hmlog ko yahan se bs grofence bnana hai;////////////////////
                //googleMap.addMarker(markerOptions);
                //googleMap.addCircle()
                MapsActivity mapsActivity=new MapsActivity();
                addMarker(latLng);
                addCircle(latLng,GEOFENCE_RADIUS);
                lat.add(latitude);
                lng.add(longitude);
                System.out.println("****************************************Should add marker and circle");

//                MapsActivity mapsActivity=new MapsActivity();
//                mapsActivity.getLOCATION(latLng,GEOFENCE_RADIUS);






/////////////////////////////// yahan pe humlog arraylist bnake data adapter se pass krenge mapsactivity mei wahan pe kaam hoga saara
//                              abhi k liye geofences add krne ka.. marker and circle wagera yahin pe ho jayega... bs geofences wahan pe bnane rhenge...






                //mapsActivity.addGeofence(latLng,GEOFENCE_RADIUS);
                //MapsActivity.getInstance().addGeofence(latLng,GEOFENCE_RADIUS);
                //addGeofence(latLng,GEOFENCE_RADIUS);
                //((MapsActivity)getActivity()).startChronometer();
                /*if(Build.VERSION.SDK_INT>=29)
                {
                    if(ContextCompat.checkSelfPermission(, Manifest.permission.ACCESS_BACKGROUND_LOCATION)==
                            PackageManager.PERMISSION_GRANTED) {
//                        mMap.clear();
                        addMarker(latLng);
                        addCircle(latLng,GEOFENCE_RADIUS);
                        addGeofence(latLng,GEOFENCE_RADIUS);
                    } else {
                        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                            ActivityCompat.requestPermissions(this,new String[]
                                    {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                        }
                        else
                        {
                            ActivityCompat.requestPermissions(this,new String[]
                                    {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                        }
                    }
                }
                else {
//                    mMap.clear();
                    addMarker(latLng);
                    addCircle(latLng,GEOFENCE_RADIUS);
                    addGeofence(latLng,GEOFENCE_RADIUS);
                }*/

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // setting the ArrayList Value
    public void setLatArrayList ( ArrayList lat )
    {
        this.lat = lat;
    }

    // getting the ArrayList value
    public static ArrayList getLatArrayList()
    {
        return lat;
    }

    public void setLongArrayList ( ArrayList lng )
    {
        this.lng = lng;
    }

    // getting the ArrayList value
    public static ArrayList getLongArrayList()
    {
        return lng;
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions= new MarkerOptions().title(name).position(latLng);
        googleMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255,255,0,0));
        circleOptions.fillColor(Color.argb(64,255,0,0));
        circleOptions.strokeWidth(4);
        googleMap.addCircle(circleOptions);
    }
}

















