package sg.edu.np.mad.madassignmentteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class busstop extends AppCompatActivity {
    ArrayList<busstopdata> busstopdataArrayList;
    private RecyclerView recyclerView;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    public double latitude = 0.0;
    public double longitude = 0.0;

    public boolean flat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busstop);

        // Initialize car park availability list and adapter
        busstopdataArrayList = new ArrayList<>();
//        flat = true;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.bus_stopitem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(busstop.this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        // Handle the received location
                        if (flat) {
                            flat = false;
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            busstopdataArrayList.clear();
                            new busstop.FetchCarParkAvailabilityTask().execute();
                        }


                    }
                }
            }
        };

//        new busstop.FetchCarParkAvailabilityTask().execute();

        if (ContextCompat.checkSelfPermission(busstop.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            requestLocationPermissions();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000); // Update interval in milliseconds
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Explain to the user why location permissions are needed (optional)
        }

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                // Handle denied permission (optional)
            }
        }
    }

    private class FetchCarParkAvailabilityTask extends AsyncTask<Void, Void, List<busstopdata>> {
        //Fetches car availability data and returns it ( needs to be fixed )

        protected List<busstopdata> doInBackground(Void... voids) {
            busstopapi busstopapi = new busstopapi();
            return busstopapi.getbusstop(latitude, longitude);
        }

        protected void onPostExecute(List<busstopdata> result) {
            if (result != null) {
                busstopdataArrayList = (ArrayList<busstopdata>) result;
//                busstop_adapter adapter = new busstop_adapter(Bus_Moving.this, busstopdataArrayList);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(busstop.this, "Failed to fetch car park availability data", Toast.LENGTH_SHORT).show();
            }
        }


        //protected void onProgressUpdate (Progress)
    }


}