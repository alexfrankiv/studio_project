package model;

import app.Application;

public class Song {
    private long id;

    private String author;
    private String name;
    private long album_id;




    public Song() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }


    @Override
    public String toString() {
        return this.getName();
    }

    public Song(long id, String author, String name, long album_id) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.album_id = album_id;
    }
}
