package com.example.theestelinggames.scoreboardList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.R;
import com.example.theestelinggames.assignmentlist.AssignmentAdapter;
import com.example.theestelinggames.util.OnItemClickListener;

import java.util.List;

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ScoreboardViewHolder> {

    public static final String LOGTAG = Scoreboard.class.getName();

    private Context context;
    private List<Scoreboard> scoreboard;
    private OnItemClickListener listener;

    ScoreboardAdapter(Context context, List<Scoreboard> scoreboard, OnItemClickListener listener) {
        this.context = context;
        this.scoreboard = scoreboard;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScoreboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG,"onCreateViewHolder()");
        View itemView = LayoutInflater.from(context).inflate(R.layout.scoreboard_overview_item,
                parent,false);
        return new ScoreboardAdapter.ScoreboardViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreboardViewHolder holder, int position) {

        Scoreboard score = scoreboard.get(position);

        //Name
        TextView minigameName = holder.itemView.findViewById(R.id.nameIDTextView);
        minigameName.setText(score.getUsername());

        //score
        TextView minigameAttempts = holder.itemView.findViewById(R.id.scoreTextView);
        minigameAttempts.setText(String.valueOf(score.getScore()));


    }

    @Override
    public int getItemCount() {
        return scoreboard.size();
    }

    public static class ScoreboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOGTAG = AssignmentAdapter.AssignmentViewHolder.class.getName();

        private OnItemClickListener clickListener;

        ScoreboardViewHolder(View itemView, OnItemClickListener listener) {
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
}
