package com.example.moemi.rekognition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.Signer;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;

    AmazonRekognitionClient rekognitionClient;
    DetectTextResult results;
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
        public void onPictureTaken(final byte[] bytes, Camera camera) {
            new Thread(){
                @Override
                public void run(){
                    ArrayList<String> detectedTextList = new ArrayList<>();

                    results = rekognitionClient.detectText(new DetectTextRequest().withImage(new Image().withBytes(ByteBuffer.wrap(bytes))));

                    for(TextDetection detectedText : results.getTextDetections()){
                        detectedTextList.add(detectedText.getDetectedText());
                    }
                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    intent.putStringArrayListExtra("results", detectedTextList);
                    startActivity(intent);
                }
            }.start();
        }
    };

    public void captureImage(View v){
        if(camera != null){
            camera.takePicture(null,null, mPictureCallback);
        }
    }


}
