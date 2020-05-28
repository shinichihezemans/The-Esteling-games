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

    public static final SliderItem[] staticSliderItems = {
        new SliderItem("Monkey", R.drawable.aap),
        new SliderItem("Bear",R.drawable.beer),
        new SliderItem("Hare",R.drawable.haas),
        new SliderItem("Lion",R.drawable.leeuw),
        new SliderItem("Rhino",R.drawable.neushoorn),
        new SliderItem("Hippo",R.drawable.nijlpaard),
        new SliderItem("Elephant",R.drawable.olifant),
        new SliderItem("Wolf",R.drawable.wolf),
        new SliderItem("Zebra",R.drawable.zebra),
    };

    public static SliderItem[] getStaticSliderItems() {
        return staticSliderItems;
    }

    public static SliderItem getStaticSliderItem(int id) {
        return staticSliderItems[id];
    }
}
