package com.g7tech.guribile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.R;

public class SplashScreen extends AppCompatActivity implements LocationListener {

    SessionManager session;
    private static int  SPLASH_TIME_OUT = 3000;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        session = new SessionManager(getApplicationContext());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(session.isLoggedIn()){
                    Intent intent = new Intent (getApplicationContext(), DashboardActivity.class);
                    startActivity(intent);
                }else{
                    if (session.getLanguage().length() != 0){
                        Intent intent = new Intent (getApplicationContext(), ActivityLogin.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent (getApplicationContext(), Language.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        },SPLASH_TIME_OUT);
    }
    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        session.setLat(String.valueOf(latitude));
        session.setLongt(String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
