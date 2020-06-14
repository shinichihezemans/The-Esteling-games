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

    SliderAdapter(List<SliderItem> sliderItems, OnItemClickListener listener) {
        Log.d(LOGTAG, "new SliderAdapter");
        this.sliderItems = sliderItems;
        clickListener = listener;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG, "onCreateViewHolder()");

        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_character_item, parent, false), clickListener);
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
     * This method is used to see how many slider items there are.
     */
    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    /**
     * With this method we put the image and the icon name in the screen and
     * we get which character the user clicked on.
     */
    public static class SliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOGTAG = SliderViewHolder.class.getName();

        private TextView textView;
        private RoundedImageView imageView;
        private OnItemClickListener clickListener;

        SliderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            Log.d(LOGTAG, "new SliderViewHolder");
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageSlide);
            textView = itemView.findViewById(R.id.iconName);
            clickListener = listener;
        }

        void setData(SliderItem sliderItem) {
            Log.d(LOGTAG, "setData()");

            textView.setText(sliderItem.getIconName());
            imageView.setImageResource(sliderItem.getImage());
        }

        /**
         * This method is used to see where the user clicked.
         * @param v
         */
        @Override
        public void onClick(View v) {
            Log.d(LOGTAG, "onClick()");

            int clickedPosition = getAdapterPosition();
            clickListener.onItemClick(clickedPosition);
        }
    }
}
