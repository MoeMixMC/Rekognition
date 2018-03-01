package com.example.moemi.rekognition;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by moemi on 2/28/2018.
 */

class RootRecyclerViewAdapter extends RecyclerView.Adapter<RootRecyclerViewAdapter.CustomViewHolder> {

    public ArrayList<String> results;
    public LayoutInflater layoutInflater;

    public RootRecyclerViewAdapter(ArrayList<String> results, LayoutInflater layoutInflater) {
        this.results = results;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.list_item_text_view.setText(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView list_item_text_view;
        private ViewGroup list_item_root;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.list_item_text_view = itemView.findViewById(R.id.list_item_text_view);
            this.list_item_root = itemView.findViewById(R.id.list_item_root);

            this.list_item_root.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
