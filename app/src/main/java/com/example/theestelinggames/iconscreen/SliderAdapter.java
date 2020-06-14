package com.example.theestelinggames.iconscreen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theestelinggames.R;
import com.example.theestelinggames.util.OnItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private static final String LOGTAG = SliderAdapter.class.getName();

    private List<SliderItem> sliderItems;
    private OnItemClickListener clickListener;

    /**
     * Basic constructor of SliderAdapter.
     *
     * @param sliderItems The list of items.
     * @param listener    The clickListener of the items.
     */
    SliderAdapter(List<SliderItem> sliderItems, OnItemClickListener listener) {
        Log.d(LOGTAG, "new SliderAdapter");
        this.sliderItems = sliderItems;
        clickListener = listener;
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
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG, "onCreateViewHolder()");

        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_character_item, parent, false), clickListener);
    }

    /**
     * This method is used to see to what slider page the user went.
     */
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Log.d(LOGTAG, "onBindViewHolder()");

        holder.setData(sliderItems.get(position));
    }

    /**
     * Gets the amount of items in the list.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    /**
     * ViewHolder inner class.
     */
    public static class SliderViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private static final String LOGTAG = SliderViewHolder.class.getName();

        private TextView textView;
        private RoundedImageView imageView;
        private OnItemClickListener clickListener;

        /**
         * Basic constructor of SliderViewHolder.
         *
         * @param itemView The view of the clicked item.
         * @param listener The listener of the item of the sliderItem.
         */
        SliderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            Log.d(LOGTAG, "new SliderViewHolder");
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageSlide);
            textView = itemView.findViewById(R.id.iconName);
            clickListener = listener;
        }

        /**
         * Method used to set the data of the item.
         *
         * @param sliderItem Current sliderItem.
         */
        void setData(SliderItem sliderItem) {
            Log.d(LOGTAG, "setData()");

            textView.setText(sliderItem.getIconName());
            imageView.setImageResource(sliderItem.getImage());
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
            clickListener.onItemClick(clickedPosition);
        }
    }
}
