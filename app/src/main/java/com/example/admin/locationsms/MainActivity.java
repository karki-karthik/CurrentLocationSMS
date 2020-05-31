package com.example.admin.locationsms;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Actitvity must implement Location Listener to use the location services
public class MainActivity extends Activity implements LocationListener{
    // Location manager and listener for getting location services
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    public String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLat = (TextView) findViewById(R.id.locationText);
        sendBtn = (Button) findViewById(R.id.sendSms);
        txtphoneNo = (EditText) findViewById(R.id.editText);
        //txtMessage = (EditText) findViewById(R.id.editText2);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Function for sending messages
                sendSMSMessage();
            }
        });
        // Getting the system services 
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            // Requesting location manager for location updates from gps provider
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        catch (SecurityException  e)
        {
            // Well no need to explain
            Toast toast = Toast.makeText(this, "error", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        // Set the lattitude and longitude 
        txtLat = (TextView) findViewById(R.id.locationText);

        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    protected void sendSMSMessage() {
        Log.i("Send SMS", "");
        // Get the reciever phone number and send the location interms of lattitude and longitude to the number
        String phoneNo = txtphoneNo.getText().toString();
        
        // Message field already contains location of the place
        String message = txtMessage.getText().toString();

        try {
            // Get sms manager to send the message 
            SmsManager smsManager = SmsManager.getDefault();
            // Send the message
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
