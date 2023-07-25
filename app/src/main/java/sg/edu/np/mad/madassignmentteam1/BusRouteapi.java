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
import java.util.ArrayList;
import java.util.List;

public class BusRouteapi {
    private static final String API_URL = "http://datamall2.mytransport.sg/ltaodataservice/BusRoutes";

    /* Retrieves car park availability data from the API.
     @return List of CarParkAvailability objects representing car park availability data.*/


    public List<BusRouteData> getbusroute() {
        List<BusRouteData> busRouteDataArrayList = new ArrayList<>();

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
                    String ServiceNo = itemObject.getString("ServiceNo");
                    String Operator = itemObject.get("Operator").toString();
                    String Direction = itemObject.get("Direction").toString();
                    String StopSequence = itemObject.get("StopSequence").toString();
                    String BusStopCode = itemObject.getString("BusStopCode");
                    String Distance = itemObject.get("Distance").toString();
                    String WD_FirstBus = itemObject.get("WD_FirstBus").toString();
                    String WD_LastBus = itemObject.getString("WD_LastBus");
                    String SAT_FirstBus = itemObject.get("SAT_FirstBus").toString();
                    String SAT_LastBus = itemObject.get("SAT_LastBus").toString();
                    String SUN_FirstBus = itemObject.getString("SUN_FirstBus");
                    String SUN_LastBus = itemObject.getString("SUN_LastBus");
                    //Adding object to array
                    busRouteDataArrayList.add(new BusRouteData(ServiceNo, Operator, Direction, StopSequence, BusStopCode, Distance, WD_FirstBus, WD_LastBus, SAT_FirstBus, SAT_LastBus, SUN_FirstBus, SUN_LastBus));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return busRouteDataArrayList;
    }
}
