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

import java.util.List;

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ScoreboardViewHolder> {

    public static final String LOGTAG = Scoreboard.class.getName();

    private Context context;
    private List<Scoreboard> scoreboard;

    ScoreboardAdapter(Context context, List<Scoreboard> scoreboard) {
        this.context = context;
        this.scoreboard = scoreboard;
    }

    @NonNull
    @Override
    public ScoreboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG, "onCreateViewHolder()");
        View itemView = LayoutInflater.from(context).inflate(R.layout.scoreboard_overview_item,
                parent, false);
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
        minigameAttempts.setText("Total score: " + score.getScore());

        //Image
        ImageView imageView = holder.itemView.findViewById(R.id.iconImageViewHS);

        String[] string = score.getUsername().split("(?<=\\D)(?=\\d)");
        String animalName = string[0];
        switch (animalName) {
            case "Monkey":
                imageView.setImageResource(R.drawable.aaptrans);
                break;
            case "Bear":
                imageView.setImageResource(R.drawable.beertrans);
                break;
            case "Hare":
                imageView.setImageResource(R.drawable.haastrans);
                break;
            case "Lion":
                imageView.setImageResource(R.drawable.leeuwtrans);
                break;
            case "Rhino":
                imageView.setImageResource(R.drawable.neushoorntrans);
                break;
            case "Hippo":
                imageView.setImageResource(R.drawable.nijlpaardtrans);
                break;
            case "Elephant":
                imageView.setImageResource(R.drawable.olifanttrans);
                break;
            case "Wolf":
                imageView.setImageResource(R.drawable.wolftrans);
                break;
            case "Zebra":
                imageView.setImageResource(R.drawable.zebratrans);
                break;
            default:

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
