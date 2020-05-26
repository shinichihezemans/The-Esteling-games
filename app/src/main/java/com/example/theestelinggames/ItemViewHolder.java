package com.example.theestelinggames;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String LOGTAG = ItemViewHolder.class.getName();

    public final TextView minigameName;
    public OnItemClickListener clickListener;
    public AssignmentAdapter adapter;

    public ItemViewHolder(View itemView, AssignmentAdapter adapter, OnItemClickListener listener) {
        super(itemView);
        minigameName = itemView.findViewById(R.id.minigameName);
        itemView.setOnClickListener(this);
        this.adapter = adapter;
        clickListener = listener;
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        Log.i(LOGTAG, "Item " + clickedPosition + " clicked");
        clickListener.onItemClick(clickedPosition);
    }


}
