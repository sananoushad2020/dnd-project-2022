package com.example.recip_easy;
import android.media.Image;
import android.widget.ImageView;

public class Recipe {
    String name;
    String id;
    ImageView img;

    public Recipe(String name, String id, ImageView img){
        this.name = name;
        this.id = id;
        this.img = img;
    }
}
