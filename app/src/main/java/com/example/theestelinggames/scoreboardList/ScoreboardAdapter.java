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
    private static final String LOGTAG = ScoreboardAdapter.class.getName();

    private Context context;
    private List<Scoreboard> scoreboard;
    private ArrayList<View> views;

    ScoreboardAdapter(Context context, List<Scoreboard> scoreboard) {
        Log.d(LOGTAG, "new ScoreboardAdapter");
        this.context = context;
        this.scoreboard = scoreboard;
        views = new ArrayList<>();
    }

    public void setColor(int color){
        Log.d(LOGTAG, "setColor()");
        for (View item : views) {
            item.setBackgroundColor(color);
        }
    }

    @NonNull
    @Override
    public ScoreboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG, "onCreateViewHolder()");
        View itemView = LayoutInflater.from(context).inflate(R.layout.scoreboard_overview_item,
                parent, false);
        views.add(itemView);
        return new ScoreboardAdapter.ScoreboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreboardViewHolder holder, int position) {
        Log.d(LOGTAG,"onBindViewHolder()");

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

        if (context.getString(R.string.Monkey).equals(animalName) || animalName.equals("Monkey") || animalName.equals("Aap")) {
            imageView.setImageResource(R.drawable.aaptrans);
        } else if (context.getString(R.string.Bear).equals(animalName) || animalName.equals("Bear") || animalName.equals("Beer")) {
            imageView.setImageResource(R.drawable.beertrans);
        } else if (context.getString(R.string.Hare).equals(animalName) || animalName.equals("Hare") || animalName.equals("Haas")) {
            imageView.setImageResource(R.drawable.haastrans);
        } else if (context.getString(R.string.Lion).equals(animalName) || animalName.equals("Lion") || animalName.equals("Leeuw")) {
            imageView.setImageResource(R.drawable.leeuwtrans);
        } else if (context.getString(R.string.Rhino).equals(animalName) || animalName.equals("Rhino") || animalName.equals("Neushorn")) {
            imageView.setImageResource(R.drawable.neushoorntrans);
        } else if (context.getString(R.string.Hippo).equals(animalName) || animalName.equals("Hippo") || animalName.equals("Nijlpaard")) {
            imageView.setImageResource(R.drawable.nijlpaardtrans);
        } else if (context.getString(R.string.Elephant).equals(animalName) || animalName.equals("Elephant") || animalName.equals("Olifant")) {
            imageView.setImageResource(R.drawable.olifanttrans);
        } else if (context.getString(R.string.Wolf).equals(animalName) || animalName.equals("Wolf")) {
            imageView.setImageResource(R.drawable.wolftrans);
        } else if (context.getString(R.string.Zebra).equals(animalName) || animalName.equals("Zebra")) {
            imageView.setImageResource(R.drawable.zebratrans);
        }

    }

    @Override
    public int getItemCount() {
        Log.d(LOGTAG, "getItemCount()");
        return scoreboard.size();
    }

    static class ScoreboardViewHolder extends RecyclerView.ViewHolder {
        private static final String LOGTAG = ScoreboardViewHolder.class.getName();

        ScoreboardViewHolder(View itemView) {
            super(itemView);
            Log.d(LOGTAG, "new ScoreboardViewHolder");
        }
    }
}
