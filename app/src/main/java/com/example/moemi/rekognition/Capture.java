package com.example.moemi.rekognition;

import android.hardware.Camera;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.TextDetection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 2/28/2018.
 */

public class Capture {
    // instance of Capture to use in ResultActivity
    private static Capture capture;

    private AmazonRekognitionClient rekognitionClient;

    public byte[] bytes;
    public Camera camera;

    public Capture(Camera camera, byte[] bytes, AmazonRekognitionClient rekognitionClient){
        this.capture = this;
        this.camera = camera;
        this.bytes = bytes;
        this.rekognitionClient = rekognitionClient;
    }

    public byte[] getImageBytes(){
        return bytes;
    }

    public Camera getCamera(){
        return camera;
    }

    public static Capture getCapture(){
        return capture;
    }

    public AmazonRekognitionClient getRekognitionClient(){
        return rekognitionClient;
    }

}
