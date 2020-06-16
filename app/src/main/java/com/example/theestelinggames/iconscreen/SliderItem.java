package com.example.theestelinggames.iconscreen;

class SliderItem {

    private int image;
    private int iconName;

    /**
     * Basic constructor of SliderItem.
     *
     * @param iconName The string resource index of the icon name.
     * @param image    The ID of the image.
     */
    SliderItem(int iconName, int image) {
        this.iconName = iconName;
        this.image = image;
    }

    /**
     * Getter for the icon name.
     *
     * @return The variable iconName.
     */
    int getIconName() {
        return iconName;
    }

    /**
     * Getter for the image ID.
     *
     * @return The variable Image.
     */
    int getImage() {
        return image;
    }

}
