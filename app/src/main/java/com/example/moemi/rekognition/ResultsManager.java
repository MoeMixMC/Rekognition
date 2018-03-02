package com.example.moemi.rekognition;

import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.TextDetection;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 2/28/2018.
 */

public class ResultsManager {
    public static String getDetectedTextType(DetectTextResult results, int position){
        return results.getTextDetections().get(position).getType();
    }

    public static List<TextDetection> getLines(Capture capture){
        List<TextDetection> lines = new ArrayList<>();
        for(TextDetection text : capture.getRekognitionClient().detectText(new DetectTextRequest().withImage(new Image().withBytes(ByteBuffer.wrap(capture.getImageBytes())))).getTextDetections()){
            if(text.getType().equalsIgnoreCase("LINE")){
                lines.add(text);
            }
        }
        return lines;
    }

    public static List<TextDetection> getWords(Capture capture){
        AmazonRekognitionClient rekognitionClient = capture.getRekognitionClient();
        List<TextDetection> words = new ArrayList<>();
        for(TextDetection text : rekognitionClient.detectText(new DetectTextRequest().withImage(new Image().withBytes(ByteBuffer.wrap(capture.getImageBytes())))).getTextDetections()){
            if(text.getType().equalsIgnoreCase("WORD")){
                words.add(text);
            }
        }
        return words;
    }
}
