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

/**
 * Adapter class for setting the Assignments in the AssignmentList
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private static final String LOGTAG = AssignmentAdapter.class.getName();

    private Context context;
    private List<Assignment> assignments;
    private OnItemClickListener listener;

    /**
     * Basic constructor of AssignmentAdapter.
     *
     * @param context     The application context.
     * @param assignments List of Assignments which need to be shown
     * @param listener    An onItemClickListener to see if an Assignment is clicked
     */
    AssignmentAdapter(Context context, List<Assignment> assignments, OnItemClickListener listener) {
        Log.d(LOGTAG, "new AssignmentAdapter");

        this.context = context;
        this.assignments = assignments;
        this.listener = listener;
    }


    /**
     * Creates a ViewHolder for the ItemView.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG, "onCreateViewHolder()");

        View itemView = LayoutInflater.from(context).inflate(R.layout.assignment_overview_item,
                parent, false);
        return new AssignmentViewHolder(itemView, listener);
    }

    /**
     * Binds the viewHolder
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Log.d(LOGTAG, "onBindViewHolder()");

        Assignment assignment = assignments.get(position);

        holder.setData(assignment, this.context);
    }

    /**
     * Gets the amount of items in the list.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return assignments.size();
    }

    /**
     * ViewHolder inner class.
     */
    static class AssignmentViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private static final String LOGTAG = AssignmentViewHolder.class.getName();

        private OnItemClickListener clickListener;
        private Assignment assignment;

        /**
         * Basic constructor of AssignmentViewHolder.
         *
         * @param itemView The view of the clicked item.
         * @param listener The listener of the item of the assignment.
         */
        AssignmentViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            Log.d(LOGTAG, "new AssignmentViewHolder");

            itemView.setOnClickListener(this);
            clickListener = listener;
        }

        /**
         * Sets what is shown in the RecyclerView.
         *
         * @param context    The application context.
         * @param assignment The assignment of which the information is taken.
         */
        void setData(Assignment assignment, Context context) {
            Log.d(LOGTAG, "setData()");

            this.assignment = assignment;
            TextView minigameName = itemView.findViewById(R.id.nameIDTextView);
            minigameName.setText(assignment.getName());
            TextView minigameAttempts = itemView.findViewById(R.id.attemptTextView);

            minigameAttempts.setText(context.getString(R.string.attempts)
                    + ": " + assignment.getAttempts() + "/3");
            TextView minigameScore = itemView.findViewById(R.id.minigameScore);
            minigameScore.setText(context.getString(R.string.score)
                    + ": " + assignment.getScore());
            ImageView imageView = itemView.findViewById(R.id.minigamePhoto_item);
            imageView.setImageResource(assignment.getImageResourceId());
        }

        /**
         * Holds which Assignment is clicked.
         *
         * @param view The view that was clicked.
         */
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
