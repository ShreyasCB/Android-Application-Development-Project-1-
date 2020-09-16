package com.capulustech.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
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

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity
{
    Student student;
    TextToSpeech textToSpeech;
    boolean isSpeaking;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        final EditText nameET = findViewById(R.id.nameET);
        final EditText usnET = findViewById(R.id.usnET);
        final EditText mobileNumberET = findViewById(R.id.mobileET);
        final Spinner sectionSpn = findViewById(R.id.sectionSpn);
        final Spinner branchSpn = findViewById(R.id.branchSpn);
        final Button speakBtn = findViewById(R.id.speakBtn);
        final Button stopBtn = findViewById(R.id.stopBtn);

        speakBtn.setOnClickListener(new View.OnClickListener()
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
                        + "Mobile Number: " + student.mobileNumber;

                speak(message);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (textToSpeech != null && textToSpeech.isSpeaking())
                {
                    textToSpeech.stop();
                }
            }
        });
    }

    public void speak(final String message)
    {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status != TextToSpeech.ERROR)
                {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate(1f);
                    textToSpeech.setPitch(1f);
                    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }
}
