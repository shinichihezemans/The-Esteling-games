package com.example.theestelinggames.scoreboardList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ScoreboardViewHolder> {

    public static final String LOGTAG = Scoreboard.class.getName();

    private Context context;
    private List<Scoreboard> scoreboard;
    private ArrayList<View> views;

    ScoreboardAdapter(Context context, List<Scoreboard> scoreboard) {
        this.context = context;
        this.scoreboard = scoreboard;
        views = new ArrayList<>();
    }

    public void setColor(int color){
        for (View item : views) {
            item.setBackgroundColor(color);
        }
    }

    @NonNull
    @Override
    public ScoreboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(LOGTAG, "onCreateViewHolder()");
        View itemView = LayoutInflater.from(context).inflate(R.layout.scoreboard_overview_item,
                parent, false);
        views.add(itemView);
        return new ScoreboardAdapter.ScoreboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreboardViewHolder holder, int position) {

        Scoreboard score = scoreboard.get(position);

        //Name
        TextView minigameName = holder.itemView.findViewById(R.id.nameIDTextView);
        minigameName.setText(score.getUsername());

        //score
        TextView minigameAttempts = holder.itemView.findViewById(R.id.scoreTextView);
        String totalScore = context.getString(R.string.total_score);
        minigameAttempts.setText(totalScore + ": " + score.getScore());

        //Image
        ImageView imageView = holder.itemView.findViewById(R.id.iconImageViewHS);

        String[] string = score.getUsername().split("(?<=\\D)(?=\\d)");
        String animalName = string[0];
        if (context.getString(R.string.Monkey).equals(animalName)) {
            imageView.setImageResource(R.drawable.aaptrans);
        } else if (context.getString(R.string.Bear).equals(animalName)) {
            imageView.setImageResource(R.drawable.beertrans);
        } else if (context.getString(R.string.Hare).equals(animalName)) {
            imageView.setImageResource(R.drawable.haastrans);
        } else if (context.getString(R.string.Lion).equals(animalName)) {
            imageView.setImageResource(R.drawable.leeuwtrans);
        } else if (context.getString(R.string.Rhino).equals(animalName)) {
            imageView.setImageResource(R.drawable.neushoorntrans);
        } else if (context.getString(R.string.Hippo).equals(animalName)) {
            imageView.setImageResource(R.drawable.nijlpaardtrans);
        } else if (context.getString(R.string.Elephant).equals(animalName)) {
            imageView.setImageResource(R.drawable.olifanttrans);
        } else if (context.getString(R.string.Wolf).equals(animalName)) {
            imageView.setImageResource(R.drawable.wolftrans);
        } else if (context.getString(R.string.Zebra).equals(animalName)) {
            imageView.setImageResource(R.drawable.zebratrans);
        }

    }

    @Override
    public int getItemCount() {
        return scoreboard.size();
    }

    static class ScoreboardViewHolder extends RecyclerView.ViewHolder {
        ScoreboardViewHolder(View itemView) {
            super(itemView);
        }
    }
}
