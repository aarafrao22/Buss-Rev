package sg.edu.np.mad.madassignmentteam1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class Bus_Moving extends AppCompatActivity {

    ArrayList<busstopdata> busstopdataArrayList;

    ArrayList<BusServiceData> busServiceDataArrayList;
    ArrayList<BusRouteData> busRouteDataArrayList;
    ConstraintLayout onclickbusstop, onclickbusservice, onclickbusroute;
    private RecyclerView recyclerView, busservicerecyclerView, busrouterecyclerView;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    public double latitude = 0.0;
    public double longitude = 0.0;

    public boolean flat = true;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_moving);

        busstopdataArrayList = new ArrayList<>();
        busServiceDataArrayList = new ArrayList<>();
        busRouteDataArrayList = new ArrayList<>();
//        flat = true;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.child_busstop);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Waits");
        progressDialog.setCanceledOnTouchOutside(false);


        busservicerecyclerView = findViewById(R.id.child_busservice);
        busservicerecyclerView.setLayoutManager(new LinearLayoutManager(this));

        busrouterecyclerView = findViewById(R.id.child_busroute);
        busrouterecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Bus_Moving.this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();

                    if (location != null) {
                        // Handle the received location
                        if (flat) {
                            stopLocationUpdates();
                            flat = false;
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            busstopdataArrayList.clear();
                            new Bus_Moving.FetchBusStopAvailabilityTask().execute();
                            new Bus_Moving.FetchBusServiceAvailabilityTask().execute();
                            new Bus_Moving.FetchBusRouteAvailabilityTask().execute();
                        }


                    }
                }
            }
        };

        if (ContextCompat.checkSelfPermission(Bus_Moving.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            requestLocationPermissions();
        }
        RecyclerView child_busstop = findViewById(R.id.child_busstop);
        onclickbusstop = findViewById(R.id.onclickstop);

        RelativeLayout myRelativeLayout = findViewById(R.id.child_busstop_expandable_layout);
        RelativeLayout myRelativeLayout1 = findViewById(R.id.child_busservice_expandable_layout);
        RelativeLayout myRelativeLayout2 = findViewById(R.id.child_busroute_expandable_layout);

        myRelativeLayout.setVisibility(View.VISIBLE);
        myRelativeLayout1.setVisibility(View.GONE);
        myRelativeLayout2.setVisibility(View.GONE);

        onclickbusstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRelativeLayout1.setVisibility(View.GONE);
                myRelativeLayout2.setVisibility(View.GONE);
                if (myRelativeLayout.getVisibility() == View.VISIBLE) {
                    // Hide the RecyclerView
                    myRelativeLayout.setVisibility(View.GONE);

                } else {
                    // Show the RecyclerView
                    myRelativeLayout.setVisibility(View.VISIBLE);

                }
            }
        });

        onclickbusservice = findViewById(R.id.onclickservice);

        onclickbusservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRelativeLayout.setVisibility(View.GONE);
                myRelativeLayout2.setVisibility(View.GONE);
                if (myRelativeLayout1.getVisibility() == View.VISIBLE) {
                    // Hide the RecyclerView
                    myRelativeLayout1.setVisibility(View.GONE);

                } else {
                    // Show the RecyclerView
                    myRelativeLayout1.setVisibility(View.VISIBLE);

                }
            }
        });


        onclickbusroute = findViewById(R.id.onclickroute);

        onclickbusroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRelativeLayout1.setVisibility(View.GONE);
                myRelativeLayout.setVisibility(View.GONE);
                if (myRelativeLayout2.getVisibility() == View.VISIBLE) {
                    // Hide the RecyclerView
                    myRelativeLayout2.setVisibility(View.GONE);

                } else {
                    // Show the RecyclerView
                    myRelativeLayout2.setVisibility(View.VISIBLE);

                }
            }
        });

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

    private class FetchBusStopAvailabilityTask extends AsyncTask<Void, Void, List<busstopdata>> {
        //Fetches car availability data and returns it ( needs to be fixed )

        protected List<busstopdata> doInBackground(Void... voids) {
            busstopapi busstopapi = new busstopapi();
            return busstopapi.getbusstop(latitude, longitude);
        }

        protected void onPostExecute(List<busstopdata> result) {
            if (result != null) {
                progressDialog.setMessage("FetchBusStopAvailabilityTask");
                progressDialog.show();


                busstopdataArrayList = (ArrayList<busstopdata>) result;
                busstop_adapter adapter = new busstop_adapter(Bus_Moving.this, busstopdataArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
            } else {
                Toast.makeText(Bus_Moving.this, "Failed to fetch FetchBusStopAvailabilityTask data", Toast.LENGTH_SHORT).show();
            }
        }


        //protected void onProgressUpdate (Progress)
    }

    private class FetchBusServiceAvailabilityTask extends AsyncTask<Void, Void, List<BusServiceData>> {
        //Fetches car availability data and returns it ( needs to be fixed )
        protected List<BusServiceData> doInBackground(Void... voids) {
            BusServiceapi busServiceapi = new BusServiceapi();
            return busServiceapi.getbusservice();
        }

        protected void onPostExecute(List<BusServiceData> result) {
            if (result != null) {
                progressDialog.setMessage("FetchBusServiceAvailabilityTask");
                progressDialog.show();
                busServiceDataArrayList = (ArrayList<BusServiceData>) result;
                Bus_Services_Adapter adapter = new Bus_Services_Adapter(Bus_Moving.this, busServiceDataArrayList);
                busservicerecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
            } else {
                Toast.makeText(Bus_Moving.this, "Failed to fetch busServiceDataArrayList availability data", Toast.LENGTH_SHORT).show();
            }
        }
        //protected void onProgressUpdate (Progress)
    }

    private class FetchBusRouteAvailabilityTask extends AsyncTask<Void, Void, List<BusRouteData>> {
        //Fetches car availability data and returns it ( needs to be fixed )
        protected List<BusRouteData> doInBackground(Void... voids) {
            BusRouteapi busRouteapi = new BusRouteapi();
            return busRouteapi.getbusroute();
        }

        protected void onPostExecute(List<BusRouteData> result) {
            if (result != null) {
                progressDialog.setMessage("FetchBusRouteAvailabilityTask");
                progressDialog.show();
                busRouteDataArrayList = (ArrayList<BusRouteData>) result;
                Bus_Route_Adapter adapter = new Bus_Route_Adapter(Bus_Moving.this, busRouteDataArrayList);
                busrouterecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            } else {
                Toast.makeText(Bus_Moving.this, "Failed to fetch Bus_Route_Adapter availability data", Toast.LENGTH_SHORT).show();
            }
        }
        //protected void onProgressUpdate (Progress)
    }
}