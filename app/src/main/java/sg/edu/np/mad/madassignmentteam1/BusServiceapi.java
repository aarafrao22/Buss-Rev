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

public class BusServiceapi {
    private static final String API_URL = "http://datamall2.mytransport.sg/ltaodataservice/BusServices";

    /* Retrieves car park availability data from the API.
     @return List of CarParkAvailability objects representing car park availability data.*/
    private static final double EARTH_RADIUS = 6371.0;


    public List<BusServiceData> getbusservice() {
        List<BusServiceData> busServiceDataList = new ArrayList<>();

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
                    String Category = itemObject.get("Category").toString();
                    String OriginCode = itemObject.getString("OriginCode");
                    String DestinationCode = itemObject.get("DestinationCode").toString();
                    String AM_Peak_Freq = itemObject.get("AM_Peak_Freq").toString();
                    String AM_Offpeak_Freq = itemObject.getString("AM_Offpeak_Freq");
                    String PM_Peak_Freq = itemObject.get("PM_Peak_Freq").toString();
                    String PM_Offpeak_Freq = itemObject.get("PM_Offpeak_Freq").toString();
                    String LoopDesc = itemObject.getString("LoopDesc");
                    //Adding object to array
                    busServiceDataList.add(new BusServiceData(ServiceNo, Operator, Direction, Category, OriginCode, DestinationCode, AM_Peak_Freq, AM_Offpeak_Freq, PM_Peak_Freq, PM_Offpeak_Freq, LoopDesc));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return busServiceDataList;
    }
}
