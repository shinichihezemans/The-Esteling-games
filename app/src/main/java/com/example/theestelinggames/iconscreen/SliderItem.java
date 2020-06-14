package com.example.theestelinggames.iconscreen;

class SliderItem {

    private int image;
    private int iconName;

    SliderItem(int iconName, int image) {
        this.iconName = iconName;
        this.image = image;
    }

    int getIconName() {
        return iconName;
    }

    int getImage() {
        return image;
    }

}
