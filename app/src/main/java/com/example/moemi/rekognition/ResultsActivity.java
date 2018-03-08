package com.example.moemi.rekognition;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.amazonaws.services.rekognition.model.TextDetection;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView rootRecyclerView;
    private RecyclerView.LayoutManager rootRecyclerViewLayoutManager;
    private RecyclerView.Adapter rootRecyclerViewAdapter;

    private LayoutInflater layoutInflater;

    public ArrayList<String> lineResults = new ArrayList<>();

    public ArrayList<String> itemPrices = new ArrayList<>();

    private Capture capture = Capture.getCapture();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
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
            for(String line : lineResults){
                if(isNumeric(line)){
                    itemPrices.add(line);
                }
            }
            for(String item : itemPrices){
                lineResults.remove(item);
            }
            rootRecyclerViewAdapter = new RootRecyclerViewAdapter(lineResults, itemPrices, layoutInflater);
            rootRecyclerView.setAdapter(rootRecyclerViewAdapter);
            
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rootRecyclerView.getContext(),
                    getResources().getConfiguration().orientation);
            rootRecyclerView.addItemDecoration(dividerItemDecoration);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}