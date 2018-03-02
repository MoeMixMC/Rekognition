package com.example.moemi.rekognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.TextDetection;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView rootRecyclerView;
    private RecyclerView.LayoutManager rootRecyclerViewLayoutManager;
    private RecyclerView.Adapter rootRecyclerViewAdapter;

    private LayoutInflater layoutInflater;

    public ArrayList<String> lineResults = new ArrayList<>();
    private Capture capture = Capture.getCapture();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        new Thread(){
            @Override
            public void run(){
                printLines();
            }
        }.start();

        layoutInflater = getLayoutInflater();

        rootRecyclerView = findViewById(R.id.rootRecyclerView);

        rootRecyclerViewLayoutManager = new LinearLayoutManager(this);

        rootRecyclerView.setLayoutManager(rootRecyclerViewLayoutManager);

        // Getting the new information
        // TODO idk if i wanna implement this...

    }

    public void printLines(){
        for(TextDetection line : ResultsManager.getLines(capture)){
            lineResults.add(line.getDetectedText());
            rootRecyclerViewAdapter = new RootRecyclerViewAdapter(lineResults, layoutInflater);
            rootRecyclerView.setAdapter(rootRecyclerViewAdapter);
        }
    }
}
