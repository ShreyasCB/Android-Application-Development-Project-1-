package com.capulustech.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Serializable;
import java.util.Locale;

public class StudentRegistrationActivity extends AppCompatActivity
{
    Student student;

    EditText nameET;
    EditText usnET;
    EditText mobileNumberET;
    Spinner sectionSpn;
    Spinner branchSpn;
    TextView locationTV;
    Button registerBtn;
    ImageView profileIV;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        nameET = findViewById(R.id.nameET);
        usnET = findViewById(R.id.usnET);
        mobileNumberET = findViewById(R.id.mobileET);
        sectionSpn = findViewById(R.id.sectionSpn);
        branchSpn = findViewById(R.id.branchSpn);
        locationTV = findViewById(R.id.locationTV);
        registerBtn = findViewById(R.id.registerBtn);
        profileIV = findViewById(R.id.ivRegLogo);


/*        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.demo);
        profileIV.startAnimation(animation);
        registerBtn.startAnimation(animation);*/

        profileIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dexter.withActivity(StudentRegistrationActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                startActivityForResult(intent, 6789);
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

        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                confirm();
            }
        });


        Dexter.withActivity(StudentRegistrationActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        FusedLocation fusedLocation =
                                new FusedLocation(StudentRegistrationActivity.this,
                                        false);
                        fusedLocation.onLocReceived(new MyLocListener()
                        {
                            @Override
                            public void onLocReceived(final LatLng latLng)
                            {
                                /*Toast.makeText(StudentRegistrationActivity.this,
                                        "Location : " + latLng.latitude + "," + latLng.longitude,
                                        Toast.LENGTH_LONG).show();*/

                                locationTV.setText("Location : " + latLng.latitude + ","
                                        + latLng.longitude);


                                locationTV.setOnClickListener(new View.OnClickListener()
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6789 && resultCode == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ImageView iv = findViewById(R.id.ivRegLogo);
            iv.setImageBitmap(bitmap);
        }


    }


    public void confirm()
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Save?");
        alertBuilder.setMessage("Do you want to save this Student Information?");
        alertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
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

                Student.addStudent(StudentRegistrationActivity.this, student);

                Toast.makeText(StudentRegistrationActivity.this,
                        "Student added to Database", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StudentRegistrationActivity.this,
                        StudentListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });

        alertBuilder.create().show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /*Toast.makeText(this, "Student Registration Activity Resumed",
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        /*Toast.makeText(this, "destroy called",
                Toast.LENGTH_SHORT).show();*/
    }
}
