package com.example.theestelinggames.assignmentlist;

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
import com.example.theestelinggames.util.OnItemClickListener;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private static final String LOGTAG = AssignmentAdapter.class.getName();

    private Context context;
    private List<Assignment> assignments;
    private OnItemClickListener listener;

    AssignmentAdapter(Context context, List<Assignment> assignments, OnItemClickListener listener) {
        Log.d(LOGTAG, "new AssignmentAdapter");

        this.context = context;
        this.assignments = assignments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG, "onCreateViewHolder()");

        View itemView = LayoutInflater.from(context).inflate(R.layout.assignment_overview_item,
                parent, false);
        return new AssignmentViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Log.d(LOGTAG, "onBindViewHolder()");

        Assignment assignment = assignments.get(position);

        holder.setData(assignment, this.context);
    }


    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOGTAG = AssignmentViewHolder.class.getName();

        private OnItemClickListener clickListener;
        private Assignment assignment;

        AssignmentViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            Log.d(LOGTAG, "new AssignmentViewHolder");

            itemView.setOnClickListener(this);
            clickListener = listener;
        }

        void setData(Assignment assignment, Context context) {
            Log.d(LOGTAG, "setData()");

            this.assignment = assignment;
            TextView minigameName = itemView.findViewById(R.id.nameIDTextView);
            minigameName.setText(assignment.getName());
            TextView minigameAttempts = itemView.findViewById(R.id.attemptTextView);

            minigameAttempts.setText(context.getString(R.string.attempts) + ": " + assignment.getAttempts() + "/3");
            TextView minigameScore = itemView.findViewById(R.id.minigameScore);
            minigameScore.setText(context.getString(R.string.score) + ": " + assignment.getScore());
            ImageView imageView = itemView.findViewById(R.id.minigamePhoto_item);
            imageView.setImageResource(assignment.getImageResourceId());
        }

        @Override
        public void onClick(View view) {
            Log.d(LOGTAG, "onClick()");

            int clickedPosition = getAdapterPosition();
            assignment.setLineLength(assignment.getLineLength() + 1);
            assignment.saveData();
            Log.d(LOGTAG, "Item " + clickedPosition + " clicked");
            clickListener.onItemClick(clickedPosition);
        }
    }
}
