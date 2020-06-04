package com.example.theestelinggames.assignmentlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.util.OnItemClickListener;
import com.example.theestelinggames.R;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private static final String LOGTAG = AssignmentAdapter.class.getName();

    private Context context;
    private List<Assignment> assignments;
    private OnItemClickListener listener;

    AssignmentAdapter(Context context, List<Assignment> assignments, OnItemClickListener listener) {
        this.context = context;
        this.assignments = assignments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(LOGTAG,"onCreateViewHolder()");
        View itemView = LayoutInflater.from(context).inflate(R.layout.assignment_overview_item,
                parent,false);
        return new AssignmentViewHolder(itemView, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//        Log.d(LOGTAG, "onBindViewHolder() - position: " + position);
        Assignment assignment = assignments.get(position);
//        Log.d(LOGTAG, "Assignment: " + assignment.getName());

        holder.bind(assignment);
        //Name
//        TextView minigameName = holder.itemView.findViewById(R.id.minigameName);
////        Log.i("Info", assignment.getName());
//        minigameName.setText(assignment.getName());
//
//        //attempts
//        TextView minigameAttempts = holder.itemView.findViewById(R.id.minigameAttempts);
////        Log.i("Info", assignment.getAttempts() + "/3");
//        minigameAttempts.setText(assignment.getAttempts() + "/3");
//
////        TextView minigameScore = holder.itemView.findViewById(R.id.minigameScore);
////
////        minigameScore.setText(assignment.getScore());
    }

    @Override
    public int getItemCount() {
//        Log.d(LOGTAG, "getItemCount()");
        return assignments.size();
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOGTAG = AssignmentViewHolder.class.getName();

        private OnItemClickListener clickListener;
        private Assignment assignment;

        AssignmentViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            clickListener = listener;
        }

        public void bind(Assignment assignment){
            this.assignment = assignment;
            TextView minigameName = itemView.findViewById(R.id.minigameName);
            minigameName.setText(assignment.getName());
            TextView minigameAttempts = itemView.findViewById(R.id.minigameAttempts);
            minigameAttempts.setText(assignment.getAttempts() + "/3");
            TextView minigameScore = itemView.findViewById(R.id.minigameScore);
            minigameScore.setText("" + assignment.getScore());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            assignment.setLineLength(assignment.getLineLength() + 1);
            assignment.saveData();
            Log.d(LOGTAG, "Item " + clickedPosition + " clicked");
            clickListener.onItemClick(clickedPosition);
        }
    }
}
