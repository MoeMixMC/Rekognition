package com.example.moemi.rekognition;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by moemi on 2/28/2018.
 */

class RootRecyclerViewAdapter extends RecyclerView.Adapter<RootRecyclerViewAdapter.CustomViewHolder> {

    public ArrayList<String> itemPrices;
    public ArrayList<String> results;
    public LayoutInflater layoutInflater;

    public RootRecyclerViewAdapter(ArrayList<String> results, ArrayList<String> itemPrices, LayoutInflater layoutInflater) {
        this.results = results;
        this.layoutInflater = layoutInflater;
        this.itemPrices = itemPrices;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.list_item, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.list_item_text_view.setText(results.get(position));
        holder.list_item_price.setText(itemPrices.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView list_item_text_view;
        ViewGroup list_item_root;
        TextView list_item_price;
        ResultItem resultItem;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.resultItem = new ResultItem(this);
            this.list_item_text_view = itemView.findViewById(R.id.list_item_text_view);
            this.list_item_root = itemView.findViewById(R.id.list_item_root);
            this.list_item_price = itemView.findViewById(R.id.list_item_price);

            this.list_item_root.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            resultItem.setBackgroundColor(Color.RED);
            resultItem.delete();
        }
    }
}
