package com.example.mynotes;

import android.app.PendingIntent;
import android.content.Context;
// content. Context provides the connection to the Android system and the resources of the project. It is the interface to global information about the application environment. The Context also provides access to Android Services, e.g. the Location Service. Activities and Services extend the Context class.
import android.content.ContextWrapper;
import android.content.Intent;
import android.widget.Switch;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofenceHelper extends ContextWrapper {
// user ko ek screen se duusri screen par navigate karwana hai
// agar aapne back button ko press kia then you should go to the previous activity
// so this is done by using stack
// aapas mein activities ko link karte hain
// ek A activity se duudri B mein jaane ko intent kehte hain
// App is a collection of multiple activities.
// An activity is a collection of Java and XML file 
    private static final String TAG = "GeofenceHelper";
    PendingIntent pendingIntent;

    public GeofenceHelper(Context base) {
        super(base);
    } //The super keyword in java is a reference variable that is used to refer parent class objects.

    public GeofencingRequest getGeofencingRequest(Geofence geofence) {
        return new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }
// builder.setInitialTrigge Sets the geofence notification behavior at the moment when the geofences are added
//    
    public Geofence getGeofence(String ID, LatLng latLng, float radius, int transitionTypes) {
        System.out.println(latLng.toString());
        return new Geofence.Builder()
                .setCircularRegion(latLng.latitude,latLng.longitude,radius)
                .setRequestId(ID)
                .setTransitionTypes(transitionTypes)
                .setLoiteringDelay(500)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }
// A PendingIntent is a token that you give to a foreign application (e.g. NotificationManager, AlarmManager, Home Screen AppWidgetManager, or other 3rd party applications), which allows the foreign application to use your application's permissions to execute a predefined piece of code.
//If you give the foreign application an Intent, it will execute your Intent with its own permissions. But if you give the foreign application a PendingIntent, that application will execute your Intent using your application's permission.
    public PendingIntent getPendingIntent(String item) {
        if(pendingIntent!=null)
        {
            System.out.println("*******#############################################################################  if wala  Pending Intent call to ho rha hai.....;......?????????????????????????????????????");

            return  pendingIntent;
        }

        System.out.println("*******#############################################################################   Pending Intent call to ho rha hai.....;......?????????????????????????????????????");

        Intent intent=new Intent(this, GeofenceBroadcastReceiver.class);
        intent.putExtra("item",item);
        pendingIntent = PendingIntent.getBroadcast(this,2607,intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
//int.FLAG_UPDATE_CURRENT. Flag indicating that if the described PendingIntent already exists, then keep it but replace its extra data with what is in this new Intent.
        return pendingIntent;
    }

    public String getErrorString(Exception e) {
        if(e  instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            switch (apiException.getStatusCode()) {
                case GeofenceStatusCodes
                        .GEOFENCE_NOT_AVAILABLE :
                    return "GEOFENCE_NOT_AVAILABLE";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_GEOFENCES :
                    return "GEOFENCE_TOO_MANY_GEOFENCES";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_PENDING_INTENTS :
                    return "GEOFENCE_TOO_MANY_PENDING_INTENTS";
            }
        }
        return e.getLocalizedMessage();
        // getMessage() returns the name of the exception. getLocalizedMessage() returns the name of the exception in the local language of the user (Chinese, Japanese etc.).
        //What is getLocalizedMessage()?
        // The getLocalizedMessage() method of Throwable class is used to get a locale-specific description of the Throwable object when an Exception Occurred. It helps us to modify the description of the Throwable object according to the local Specific message.
    }
}