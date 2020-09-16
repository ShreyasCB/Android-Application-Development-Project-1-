package com.capulustech.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class SpeechRecognitionActivity extends AppCompatActivity
{
    private TextView speechTV;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speechrecognition);

        speechTV = findViewById(R.id.speechTV);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        initializeSpeech();
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

    @SuppressLint("ClickableViewAccessibility")
    public void initializeSpeech()
    {
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        ImageView speakIV = findViewById(R.id.speakIV);
        speakIV.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        speechTV.setText("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        speechTV.setText("");
                        speechTV.setText("Listening...");
                        break;
                }
                return false;
            }
        });

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener()
        {
            @Override
            public void onReadyForSpeech(Bundle bundle)
            {

            }

            @Override
            public void onBeginningOfSpeech()
            {

            }

            @Override
            public void onRmsChanged(float v)
            {

            }

            @Override
            public void onBufferReceived(byte[] bytes)
            {

            }

            @Override
            public void onEndOfSpeech()
            {

            }

            @Override
            public void onError(int i)
            {

            }

            @Override
            public void onResults(Bundle bundle)
            {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                {
                    String spokenText = matches.get(0);
                    speechTV.setText(spokenText);

                    if(spokenText.toLowerCase().contains("how are you"))
                    {
                        speak("I am Fine");
                    }
                    if(spokenText.toLowerCase().contains("what is my name"))
                    {
                        speak("shreyas");
                    }
                    if(spokenText.toLowerCase().contains("ok"))
                    {
                        speak("command please");
                    }

                    /* TO open gallery */

                    if(spokenText.toLowerCase().contains("open gallery"))
                    {
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    /* GO to register activity */

                    if(spokenText.toLowerCase().contains("register"))
                    {
                        Intent intt = new Intent(SpeechRecognitionActivity.this, StudentRegistrationActivity.class);
                        startActivity(intt);

                    }
                    if(spokenText.toLowerCase().contains("list"))
                    {
                        Intent inttt = new Intent(SpeechRecognitionActivity.this, StudentListActivity.class);
                        startActivity(inttt);

                    }
                    if(spokenText.toLowerCase().contains("login"))
                    {
                        Intent intttt = new Intent(SpeechRecognitionActivity.this, LoginActivity.class);
                        startActivity(intttt);

                    }
                    if(spokenText.toLowerCase().contains("web"))
                    {
                        Intent inttttt = new Intent(SpeechRecognitionActivity.this, WebViewActivity.class);
                        startActivity(inttttt);

                    }
                    if(spokenText.toLowerCase().contains("video"))
                    {
                        Intent intts = new Intent(SpeechRecognitionActivity.this, VideoCaptureActivity.class);
                        startActivity(intts);

                    }
                    if (spokenText.toLowerCase().contains("call"))
                    {
                        Dexter.withActivity(SpeechRecognitionActivity.this)
                                .withPermission(Manifest.permission.CALL_PHONE)
                                .withListener(new PermissionListener()
                                {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response)
                                    {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL,
                                                Uri.parse("tel:" + "8277313019"));
                                        if (ActivityCompat.checkSelfPermission(SpeechRecognitionActivity.this,
                                                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                                        {
                                            startActivity(callIntent);
                                        }
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

                }
            }

            @Override
            public void onPartialResults(Bundle bundle)
            {

            }

            @Override
            public void onEvent(int i, Bundle bundle)
            {

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
                    textToSpeech.setSpeechRate(1.2f);
                    textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH, null, null);
                }


            }
        });
    }


}