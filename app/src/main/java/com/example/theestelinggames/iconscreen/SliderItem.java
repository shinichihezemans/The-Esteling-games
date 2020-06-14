package com.example.theestelinggames.iconscreen;

class SliderItem {

    private int image;
    private int iconName;

    /**
     * This is the constructor that combines the png fils and names of the characters
     * so we can slide through them or use them for other methods.
     * @param iconName
     * @param image
     */
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
