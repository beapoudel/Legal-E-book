package com.example.legallib;

import android.graphics.Bitmap;

public class ContactModel {
    String title;
    String description;
    Bitmap img;
    String author;
    String webreader;
    String download;
    Boolean avaibility;
    public ContactModel(Bitmap img, String title, String description,String author,String webreader,String download,Boolean avaibility){
        this.img = img;
        this.title = title;
        this.description = description;
        this.author = author;
        this.webreader = webreader;
        this.download = download;
        this.avaibility = avaibility;
    }
}
