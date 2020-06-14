package com.example.theestelinggames.iconscreen;

import com.example.theestelinggames.R;

class SliderItem {

    private int image;
    private int iconName;

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
