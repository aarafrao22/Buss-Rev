package sg.edu.np.mad.madassignmentteam1;

import static android.content.ContentValues.TAG;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class busstopapi {
    private static final String API_URL = "http://datamall2.mytransport.sg/ltaodataservice/BusStops";

    /* Retrieves car park availability data from the API.
     @return List of CarParkAvailability objects representing car park availability data.*/
    private static final double EARTH_RADIUS = 6371.0;

    public static double distance(double previousLatitude, double previousLongitude, double latitude, double longitude) {
//        long currentTime = new Date().getTime();
        // Convert latitude and longitude to radians
        double lat1 = Math.toRadians(previousLatitude);
        double lon1 = Math.toRadians(previousLongitude);
        double lat2 = Math.toRadians(latitude);
        double lon2 = Math.toRadians(longitude);

        // Calculate distance using Haversine formula
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        return distance;
    }


    public List<busstopdata> getbusstop(double lat, double lon) {
        List<busstopdata> busstopdataList = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("AccountKey", "ZLwg+friTO+5ltLR6fIoeQ==");
            connection.setRequestProperty("accept", "application/json");

            int responseCode = connection.getResponseCode();
            Log.i("responseCode", String.valueOf(responseCode));
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                Log.d(TAG, "getCarParkAvailabilityData: " + jsonObject);
                Log.i("jsonobject", String.valueOf(jsonObject));
                JSONArray itemsArray = jsonObject.getJSONArray("value");
                Log.i("itemarray", String.valueOf(itemsArray));
                //Creating arraylist
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject itemObject = itemsArray.getJSONObject(i);
                    String BusStopCode = itemObject.getString("BusStopCode");
                    String RoadName = itemObject.get("RoadName").toString();
                    String Description = itemObject.get("Description").toString();
                    String Latitude = itemObject.getString("Latitude");
                    String Longitude = itemObject.get("Longitude").toString();
                    double distance = distance(lat, lon, Double.valueOf(Latitude), Double.valueOf(Longitude));
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");

                    // Use the format() method to round the double value to 2 decimal places
                    String roundedNumber = decimalFormat.format(distance);
                    //Adding object to array
                    busstopdataList.add(new busstopdata(BusStopCode, RoadName, String.valueOf(roundedNumber) + " km"));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return busstopdataList;
    }
}
