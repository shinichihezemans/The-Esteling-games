package com.example.theestelinggames.iconscreen;

import com.example.theestelinggames.R;

class SliderItem {

    private int image;
    private int iconName;

    /**
     * This is the constructor that combines the png fils and names of the characters
     * so we can slide through them or use them for other methods.
     * @param iconName
     * @param image
     */
    public SliderItem(int iconName, int image){
        this.iconName = iconName;
        this.image = image;
    }

    public int getIconName() {
        return iconName;
    }

    public int getImage() {
        return image;
    }

}
