package com.example.theestelinggames;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String LOGTAG = ItemViewHolder.class.getName();

    private OnItemClickListener clickListener;

    public ItemViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        clickListener = listener;
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        Log.d(LOGTAG, "Item " + clickedPosition + " clicked");
        clickListener.onItemClick(clickedPosition);
    }


}
