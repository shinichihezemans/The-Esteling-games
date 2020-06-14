package com.example.theestelinggames.assignmentlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.util.OnItemClickListener;
import com.example.theestelinggames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for setting the Assignments in the AssignmentList
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private static final String LOGTAG = AssignmentAdapter.class.getName();

    private Context context;
    private List<Assignment> assignments;
    private OnItemClickListener listener;
    private ArrayList<View> views;

    /**
     * basic constructor
     * @param context
     * @param assignments list of Assignments which need to be shown
     * @param listener an onItemClickListener to see if an Assignment is clicked
     */
    AssignmentAdapter(Context context, List<Assignment> assignments, OnItemClickListener listener) {
        this.context = context;
        this.assignments = assignments;
        this.listener = listener;
        views = new ArrayList<>();
    }

    /**
     * sets the background color
     * @param color the number of the color
     */
    public void setColor(int color){
        for (View item : views) {
            item.setBackgroundColor(color);
        }
    }

    /**
     * creates a viewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.assignment_overview_item,
                parent,false);
        views.add(itemView);
        return new AssignmentViewHolder(itemView, listener);
    }

    /**
     * binds the viewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
//        Log.d(LOGTAG, "Assignment: " + assignment.getName());

        holder.bind(assignment, this.context);
    }

    /**
     * gets the amount of items in the list
     * @return the size of the AssignmentList
     */
    @Override
    public int getItemCount() {
//        Log.d(LOGTAG, "getItemCount()");
        return assignments.size();
    }

    /**
     * extra class which makes sets the Assignments to the recyclerview
     */
    public static class AssignmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOGTAG = AssignmentViewHolder.class.getName();

        private OnItemClickListener clickListener;
        private Assignment assignment;

        AssignmentViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            clickListener = listener;
        }

        /**
         * Sets what is shown in the Recylcerview
         * @param assignment The assignment of which the information is taken
         * @param context
         */
        public void bind(Assignment assignment, Context context){
            this.assignment = assignment;
            TextView minigameName = itemView.findViewById(R.id.nameIDTextView);
            minigameName.setText(assignment.getName());
            TextView minigameAttempts = itemView.findViewById(R.id.attemptTextView);

            minigameAttempts.setText(context.getString(R.string.attempts) + ": " + assignment.getAttempts() + "/3");
            TextView minigameScore = itemView.findViewById(R.id.minigameScore);
            minigameScore.setText(context.getString(R.string.score) + ": " +  assignment.getScore());
            ImageView imageView = itemView.findViewById(R.id.minigamePhoto_item);
            imageView.setImageResource(assignment.getImageResourceId());
        }

        /**
         * Holds which Assignment is clicked.
         * @param view
         */
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
