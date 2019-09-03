package com.example.yashwanth.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView cityName;
    TextView weatherDesc;
    TextView humdityTV;
    TextView tempTV;
    String data;
    JSONObject weatherData;
    JSONObject mainObj;
    JSONObject jObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG = "YM";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = findViewById(R.id.placeName);
        weatherDesc = findViewById(R.id.DayDesc);
        humdityTV = findViewById(R.id.HumidityView);
        tempTV = findViewById(R.id.TempView);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyB7kHIvMZ8yeHoi_raq_M-zvsVLX5Djbo8");
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                //Executing Async Task
                new WeatherHTTPClient(MainActivity.this, place.getName()).execute();
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(MainActivity.this, "Error: " + status, Toast.LENGTH_LONG).show();
            }
        });
        }


public class WeatherHTTPClient extends AsyncTask<Void, Void, Void>
{
    public Context context;
    public String name;


    public WeatherHTTPClient(Context context, String name)
    {
        this.context = context;
        this.name = name;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0)
    {
        //Gathering Data from API
        data = ((new WeatherHttpClient()).getWeatherData(name));
        Log.i("YM", "Received JSON Info: <<< " + data + " >>>");

        try{
            // Parsing data to JSON objects
            jObj = new JSONObject(data);
                        JSONArray jArr = jObj.getJSONArray("weather");
                        weatherData = jArr.getJSONObject(0);
                        mainObj = jObj.getJSONObject("main");

        } catch ( JSONException e) {
            e.printStackTrace();
        }

        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Void result)
    {

        // Updating Text Views with information
        try {
            tempTV.setText((float)((mainObj.getDouble("temp")) - 273.00)+ " °C");
            humdityTV.setText(mainObj.getInt("humidity")+"%");
            cityName.setText(jObj.getString("name"));
            weatherDesc.setText(weatherData.getString("description")+"!");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
        Toast.makeText(context, "Error, Network issue", Toast.LENGTH_LONG).show();
    }
}
}

