package com.variantxx.codefest;

public class Cake {
    int id;
    String thumbnail;
    String name;
    String description;

    Cake(int id, String thumbnail, String name, String description) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public String getThumbnail() { return thumbnail; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
