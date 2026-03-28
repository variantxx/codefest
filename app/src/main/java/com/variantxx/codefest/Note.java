package com.variantxx.codefest;

public class Note {
    int id;
    int ownerId;
    String title;
    String content;

    Note(int id, int ownerId, String title, String content) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
    }

    public int getId() { return id; }

    public int getOwnerId() { return ownerId; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

}
