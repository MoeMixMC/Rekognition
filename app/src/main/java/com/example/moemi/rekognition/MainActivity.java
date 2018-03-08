package com.example.moemi.rekognition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.s3.AmazonS3Client;


public class MainActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;

    AmazonRekognitionClient rekognitionClient;
    AmazonS3Client s3;
    TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAmazonRekognitionClient(getCredentialsProvider());

        frameLayout = findViewById(R.id.frameLayout);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //ask for authorisation
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 50);
        }else{
            camera = camera.open();
            showCamera = new ShowCamera(this, camera);
            frameLayout.addView(showCamera);
        }
    }

    public CognitoCachingCredentialsProvider getCredentialsProvider(){
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:d2067f78-4495-4f7b-a890-cb82cac8d1a6", // Identity pool ID
                Regions.US_EAST_1 // Region
        );
        return credentialsProvider;
    }

    // Setting the Amazon S3 Client
    public void setAmazonS3Client(CognitoCachingCredentialsProvider credentialsProvider) {
        s3 = new AmazonS3Client(credentialsProvider);
        s3.setRegion(Region.getRegion(Regions.US_EAST_1));
    }

    // Setting the Amazon Rekognition Client
    public void setAmazonRekognitionClient(CognitoCachingCredentialsProvider credentialsProvider){
        rekognitionClient = new AmazonRekognitionClient(credentialsProvider);
        rekognitionClient.setRegion(Region.getRegion(Regions.US_EAST_1));
    }

    public void setTransferUtility(){
        transferUtility = new TransferUtility(s3, getApplicationContext());
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(final byte[] bytes, final Camera camera) {
            Capture capture = new Capture(camera, bytes, rekognitionClient);
            Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
            startActivity(intent);
        }
    };

    public void captureImage(View v){
        if(camera != null){
            camera.takePicture(null,null, mPictureCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 50: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //If user presses allow
                    Toast.makeText(getApplicationContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
                    camera = camera.open();
                    showCamera = new ShowCamera(this, camera);
                    frameLayout.addView(showCamera);
                } else {
                    //If user presses deny
                    Toast.makeText(getApplicationContext(), "You must grant this app permission to use your fucking camera idiot.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


}
