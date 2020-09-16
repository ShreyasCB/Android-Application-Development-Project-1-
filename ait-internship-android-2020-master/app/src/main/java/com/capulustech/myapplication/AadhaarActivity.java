package com.capulustech.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class AadhaarActivity extends AppCompatActivity
{

    private TextView uidTV;
    private TextView nameTV;
    private TextView genderTV;
    private TextView yobTV;
    private TextView dobTV;
    private TextView coTV;
    private TextView houseTV;
    private TextView streetTV;
    private TextView lmTV;
    private TextView locTV;
    private TextView vtcTV;
    private TextView poTV;
    private TextView distTV;
    private TextView subdistTV;
    private TextView stateTV;
    private TextView pcTV;
    private TextView aadhaarDetailsTV;
    private TextToSpeech textToSpeech;
    TableLayout detailsTable;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhaar);


        uidTV = findViewById(R.id.uidTV);
        nameTV = findViewById(R.id.nameTV);
        genderTV = findViewById(R.id.genderTV);
        yobTV = findViewById(R.id.yobTV);
        dobTV = findViewById(R.id.dobTV);
        coTV = findViewById(R.id.coTV);
        houseTV = findViewById(R.id.houseTV);
        streetTV = findViewById(R.id.streetTV);
        lmTV = findViewById(R.id.lmTV);
        locTV = findViewById(R.id.locTV);
        vtcTV = findViewById(R.id.vtcTV);
        poTV = findViewById(R.id.poTV);
        distTV = findViewById(R.id.distTV);
        subdistTV = findViewById(R.id.subdistTV);
        stateTV = findViewById(R.id.stateTV);
        pcTV = findViewById(R.id.pcTV);
        detailsTable = findViewById(R.id.detailsTable);
        aadhaarDetailsTV = findViewById(R.id.adhartv);
        detailsTable.setVisibility(View.GONE);


        Button scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                IntentIntegrator qrScan = new IntentIntegrator(AadhaarActivity.this);
                qrScan.initiateScan();
            }
        });
    }


    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else
            {
                Log.d("nk", result.getContents());
                Log.d("nk", result.getContents());

                Toast.makeText(this, "" + result.getContents(), Toast.LENGTH_SHORT).show();

                aadhaarDetailsTV.setText(result.getContents());

              /*  String aadhaarXML = result.getContents();
                XmlToJson xmlToJson = new XmlToJson.Builder(aadhaarXML).build();
                JSONObject jsonObject = xmlToJson.toJson();
                Log.d("Aadhaar Data", jsonObject.toString());
                detailsTable.setVisibility(View.VISIBLE);
                try
                {
                    JSONObject printLetterBarcodeData = jsonObject.getJSONObject("PrintLetterBarcodeData");
                    String uid = printLetterBarcodeData.getString("uid");
                    String name = printLetterBarcodeData.getString("name");
                    String gender = printLetterBarcodeData.getString("gender");
                    String yob = printLetterBarcodeData.getString("yob");
                    String dob = printLetterBarcodeData.getString("dob");
                    String co = printLetterBarcodeData.getString("co");
                    String house = printLetterBarcodeData.getString("house");
                    String street = printLetterBarcodeData.getString("street");
                    String lm = printLetterBarcodeData.getString("lm");
                    String loc = printLetterBarcodeData.getString("loc");
                    String vtc = printLetterBarcodeData.getString("vtc");
                    String po = printLetterBarcodeData.getString("po");
                    String dist = printLetterBarcodeData.getString("dist");
                    String subdist = printLetterBarcodeData.getString("subdist");
                    String state = printLetterBarcodeData.getString("state");
                    String pc = printLetterBarcodeData.getString("pc");
                    uidTV.setText(uid);
                    nameTV.setText(name);
                    genderTV.setText(gender);
                    yobTV.setText(yob);
                    dobTV.setText(dob);
                    coTV.setText(co);
                    houseTV.setText(house);
                    streetTV.setText(street);
                    lmTV.setText(lm);
                    locTV.setText(loc);
                    vtcTV.setText(vtc);
                    poTV.setText(po);
                    distTV.setText(dist);
                    subdistTV.setText(subdist);
                    stateTV.setText(state);
                    pcTV.setText(pc);*//*
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }*/
//                Log.d("nk", jsonObject.toString());
//                Toast.makeText(this, "" + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
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