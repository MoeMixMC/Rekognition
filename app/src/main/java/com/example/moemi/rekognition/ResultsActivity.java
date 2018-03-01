package com.example.moemi.rekognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView rootRecyclerView;
    private RecyclerView.LayoutManager rootRecyclerViewLayoutManager;
    private RecyclerView.Adapter rootRecyclerViewAdapter;

    private LayoutInflater layoutInflater;

    public ArrayList<String> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        for(String string : getIntent().getStringArrayListExtra("results")){
            results.add(string);
        }

        layoutInflater = getLayoutInflater();

        rootRecyclerView = findViewById(R.id.rootRecyclerView);

        rootRecyclerViewLayoutManager = new LinearLayoutManager(this);

        rootRecyclerView.setLayoutManager(rootRecyclerViewLayoutManager);

        rootRecyclerViewAdapter = new RootRecyclerViewAdapter(results, layoutInflater);
        rootRecyclerView.setAdapter(rootRecyclerViewAdapter);
    }
}
