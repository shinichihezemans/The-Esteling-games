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

    private static final SliderItem[] staticSliderItems = {
        new SliderItem("Monkey", R.drawable.aaptrans),
        new SliderItem("Bear",R.drawable.beertrans),
        new SliderItem("Hare",R.drawable.haastrans),
        new SliderItem("Lion",R.drawable.leeuwtrans),
        new SliderItem("Rhino",R.drawable.neushoorntrans),
        new SliderItem("Hippo",R.drawable.nijlpaardtrans),
        new SliderItem("Elephant",R.drawable.olifanttrans),
        new SliderItem("Wolf",R.drawable.wolftrans),
        new SliderItem("Zebra",R.drawable.zebratrans),
    };

    public static SliderItem[] getStaticSliderItems() {
        return staticSliderItems;
    }

}
