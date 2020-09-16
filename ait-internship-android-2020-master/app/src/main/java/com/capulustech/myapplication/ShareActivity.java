package com.capulustech.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Locale;

public class ShareActivity extends AppCompatActivity
{
    Student student;
    LatLng locationLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        final EditText nameET = findViewById(R.id.nameET);
        final EditText usnET = findViewById(R.id.usnET);
        final EditText mobileNumberET = findViewById(R.id.mobileET);
        final TextView locationTV = findViewById(R.id.locationTV);
        final Spinner sectionSpn = findViewById(R.id.sectionSpn);
        final Spinner branchSpn = findViewById(R.id.branchSpn);
        final Button shareBtn = findViewById(R.id.shareBtn);

        Dexter.withActivity(ShareActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        FusedLocation fusedLocation =
                                new FusedLocation(ShareActivity.this,
                                        false);
                        fusedLocation.onLocReceived(new MyLocListener()
                        {
                            @Override
                            public void onLocReceived(final LatLng latLng)
                            {
                                locationLatLng = latLng;

                                locationTV.setText("Location : " + latLng.latitude + "," + latLng.longitude);

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

        shareBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String name = nameET.getText().toString();
                String usn = usnET.getText().toString();
                String branch = branchSpn.getSelectedItem().toString();
                String section = sectionSpn.getSelectedItem().toString();
                String mobileNumber = mobileNumberET.getText().toString();

                student = new Student();
                student.name = name;
                student.branch = branch;
                student.usn = usn;
                student.mobileNumber = mobileNumber;
                student.section = section;

                String message = "Name: " + student.name + "\n"
                        + "USN: " + student.usn + "\n"
                        + "Branch: " + student.branch + "\n"
                        + "Section: " + student.section + "\n"
                        + "Mobile Number: " + student.mobileNumber + "\n";

                if (locationLatLng != null)
                {
                    message += "This is my Location: \n" + "http://maps.google.com/maps?q="
                            + locationLatLng.latitude + "," + locationLatLng.longitude;
                }

                
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
}
