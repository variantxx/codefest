package com.variantxx.codefest;

import android.net.Uri;

public class Cake {
    int id;
    Uri thumbnail;
    String name;
    String description;

    Cake(int id, Uri thumbnail, String name, String description) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public Uri getThumbnail() { return thumbnail; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
