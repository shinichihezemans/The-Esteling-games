package com.example.theestelinggames.iconscreen;

import com.example.theestelinggames.R;

class SliderItem {

    private int image;
    private String iconName;

    public SliderItem(String iconName, int image){
        this.iconName = iconName;
        this.image = image;
    }

    public String getIconName() {
        return iconName;
    }

    public int getImage() {
        return image;
    }

}
