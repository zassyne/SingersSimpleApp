package com.example.yassine.singers;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yassine on 16.04.16.
 */
public class Artist implements Serializable {

    /*Mandatory to instantiate an Artist class */
    private String name;
    private String id;

    /*Optional to have */
    private ArrayList<String> genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private String smallCover;
    private String bigCover;

    /*Constructor that uses a Builder*/
    public Artist(ArtistBuilder artistBuilder) {
        this.name = artistBuilder.name;
        this.id   = artistBuilder.id;

        this.genres = artistBuilder.genres;
        this.tracks = artistBuilder.tracks;
        this.albums = artistBuilder.albums;
        this.link   = artistBuilder.link;
        this.description = artistBuilder.description;
        this.smallCover = artistBuilder.smallCover;
        this.bigCover = artistBuilder.bigCover;
    }

    /*Getters Methods */
    public String getName() { return name; }
    public String getID() { return id; }
    public ArrayList<String> getGenres() {return genres;}
    public int getTracks() { return tracks; }
    public int getAlbums() { return albums; }
    public String getLink() {return link; }
    public String getDescription() { return description; }
    public String getSmallCover() {return smallCover; }
    public String getBigCover() {return bigCover; }


    /********************************************************************************
     * A class that implements a simple Build pattern
     */
    public static class ArtistBuilder {

        private final String name;
        private final String id;

        private ArrayList<String> genres;
        private int tracks;
        private int albums;
        private String link;
        private String description;
        private String smallCover;
        private String bigCover;


        public ArtistBuilder(String id, String name) {
            this.name = name;
            this.id = id;
        }

        public ArtistBuilder genres(ArrayList<String> genres) {
            this.genres = genres;
            return this;
        }

        public ArtistBuilder tracks(int tracks) {
            this.tracks = tracks;
            return this;
        }

        public ArtistBuilder albums(int albums) {
            this.albums = albums;
            return this;
        }

        public ArtistBuilder link(String link) {
            this.link = link;
            return this;
        }

        public ArtistBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ArtistBuilder small(String small) {
            this.smallCover = small;
            return this;
        }

        public ArtistBuilder big(String big) {
            this.bigCover = big;
            return this;
        }

        public Artist build() {
            Artist artist = new Artist(this);
            return artist;
        }


    }


}
