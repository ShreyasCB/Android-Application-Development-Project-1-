package com.capulustech.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LocationActivity extends AppCompatActivity
{


    TextView weatherTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final TextView locationTV = findViewById(R.id.locationTV);
        weatherTV = findViewById(R.id.weatherTV);
        final Button mapBtn = findViewById(R.id.mapBtn);
        final Button locationBtn = findViewById(R.id.locationBtn);
        final Button weatherBtn = findViewById(R.id.weatherBtn);

        locationBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dexter.withActivity(LocationActivity.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                FusedLocation fusedLocation =
                                        new FusedLocation(LocationActivity.this,
                                                false);
                                fusedLocation.onLocReceived(new MyLocListener()
                                {
                                    @Override
                                    public void onLocReceived(final LatLng latLng)
                                    {

                                        locationTV.setText("Location : " + latLng.latitude + "," + latLng.longitude);

                                        weatherBtn.setVisibility(View.VISIBLE);

                                        weatherBtn.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                getWeatherDetails(latLng);
                                            }
                                        });

                                        mapBtn.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Uri uri = Uri.parse("http://maps.google.com/maps?q=" +
                                                        latLng.latitude + "," + latLng.longitude);

                                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                                                mapIntent.setPackage("com.google.android.apps.maps");
                                                startActivity(mapIntent);
                                            }
                                        });
                                    }
                                });
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response)
                            {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                           PermissionToken token)
                            {

                            }
                        })
                        .check();
            }
        });
    }

    public void getWeatherDetails(LatLng latLng)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?lat=" + latLng.latitude +
                        "&lon=" + latLng.longitude +
                        "&units=metric&APPID" +
                        "=5a6bd43795ec1f1e5e9e2362dec72e9c")
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);
                }
                else
                {
                    String responseString = response.body().string();
                    try
                    {
                        JSONObject jsonObject = new JSONObject(responseString);
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        String weatherType = jsonArray.getJSONObject(0).getString("description");
                        String temperature = jsonObject.getJSONObject("main").getString("temp");
                        String city = jsonObject.getString("name");

                        /*image



                        JSONObject weatherObject = (JSONObject) jsonArray.get(0);
                        String weathertype = weatherObject.get("main").toString().toLowerCase();

                        if(weatherType=="rain")
                        {
                            rain.setImageDrawable(getDrawable(R.drawable.app_logo));
                        }

                         */



                        weatherTV.setText(city + " - " + weatherType.toUpperCase() + " - " + temperature + "C");

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
