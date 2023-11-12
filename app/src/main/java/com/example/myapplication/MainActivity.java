package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    int REQ_PERMISSION = 100;
    double strCurrentLatitude;
    double strCurrentLongitude;
    String strCurrentLocation, strTitle;

    private FirebaseUser firebaseUser;
    private TextView textNama;


    LocationServices locationServices;

    CardView cvPemadam, cvAmbulance, cvBencana, cvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNama = findViewById(R.id.textViewNama);


        setStatusBar();
        setPermission();
        setLocation();
        setInitLayout();
        setCurrentLocation();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null) {

            textNama.setText(firebaseUser.getDisplayName());
        }else{

            textNama.setText("Username tidak ditemukan");
        }

    }

    private void setLocation() {
        FusedLocationProviderClient locationServices = LocationServices.getFusedLocationProviderClient(this);

        //get location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Handle permission request
            return;
        }
        locationServices.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null) {
                    strCurrentLatitude = location.getLatitude();
                    strCurrentLongitude = location.getLongitude();
                    //set location lat long
                    strCurrentLocation = strCurrentLatitude + "," + strCurrentLongitude;
                } else {
                    //location is null
                }
            } else {
                // Handle the error.
                // You can call task.getException() to get more details.
            }
        });
    }


    private void setInitLayout() {
        cvPemadam = findViewById(R.id.cvPemadam);
        cvAmbulance = findViewById(R.id.cvAmbulance);
        cvBencana = findViewById(R.id.cvBencana);
        cvHistory = findViewById(R.id.cvHistory);

        cvPemadam.setOnClickListener(v -> {
            strTitle = "Laporan Kebakaran";
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        cvAmbulance.setOnClickListener(v -> {
            strTitle = "Laporan Medis";
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        cvBencana.setOnClickListener(v -> {
            strTitle = "Laporan Bencana Alam";
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        cvHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }


    private void setCurrentLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Constant.lokasiPengaduan = addressList.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_PERMISSION && resultCode == RESULT_OK) {

        }
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

}