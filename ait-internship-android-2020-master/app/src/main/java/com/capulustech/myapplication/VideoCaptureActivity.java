package com.capulustech.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
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

public class VideoCaptureActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        final Button videoBtn = findViewById(R.id.captureVideoBtn);

        videoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dexter.withActivity(VideoCaptureActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                videoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                                startActivityForResult(videoIntent, 1111);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK)
        {
            Uri videoUri = data.getData();
            final VideoView videoView = findViewById(R.id.videoView);
            videoView.setVideoURI(videoUri);
            videoView.start();

            videoView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (videoView.isPlaying())
                    {
                        videoView.pause();
                    }
                    else
                    {
                        videoView.start();
                        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                        {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer)
                            {
                                videoView.start();
                            }
                        });
                    }
                }
            });
        }
    }
}
