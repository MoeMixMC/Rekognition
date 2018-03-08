package com.example.moemi.rekognition;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Window;

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
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_results);

        layoutInflater = getLayoutInflater();

        rootRecyclerView = findViewById(R.id.rootRecyclerView);

        rootRecyclerViewLayoutManager = new LinearLayoutManager(this);

        rootRecyclerView.setLayoutManager(rootRecyclerViewLayoutManager);

        new AsyncResultsGetter().execute();

    }

    public void populateLineResults(){
        if(capture != null){
            for(TextDetection line : ResultsManager.getLines(capture)){
                lineResults.add(line.getDetectedText());
            }
        }else{
            // TODO kick back to take picture
        }
    }

    class AsyncResultsGetter extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            populateLineResults();
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            rootRecyclerViewAdapter = new RootRecyclerViewAdapter(lineResults, layoutInflater);
            rootRecyclerView.setAdapter(rootRecyclerViewAdapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
