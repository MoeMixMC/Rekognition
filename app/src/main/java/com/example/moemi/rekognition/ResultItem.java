package com.example.moemi.rekognition;

import android.util.Log;


import java.util.ArrayList;

/**
 * Created by Mohamed on 3/7/2018.
 */

public class ResultItem {

    public ArrayList<ResultItem> resultItems = new ArrayList<>();
    public RootRecyclerViewAdapter.CustomViewHolder holder;


    public ResultItem(RootRecyclerViewAdapter.CustomViewHolder holder){
        this.holder = holder;
        resultItems.add(this);
    }

    public void delete(){
        Log.e("RESULT", "Deleting view holder: " + holder.getAdapterPosition());
    }

    public void setBackgroundColor(int color){
        holder.list_item_root.setBackgroundColor(color);
    }
}
