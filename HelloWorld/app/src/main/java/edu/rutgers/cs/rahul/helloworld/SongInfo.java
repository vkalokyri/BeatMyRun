package edu.rutgers.cs.rahul.helloworld;

/*
* Created by Rahul Shome
* Tested by Rahul Shome
* Debugged by Rahul Shome
 */
public class SongInfo {

    String youtube_id;
    String song_title;
    String artist;
    double tempo;
    double duration;
    double liveness;
    double energy;
    double danceability;

    public SongInfo(String youtube_id, String song_title, String artist, double tempo, double duration, double liveness, double energy, double danceability) {
        this.youtube_id = youtube_id;
        this.song_title = song_title;
        this.artist = artist;
        this.tempo = tempo;
        this.duration = duration;
        this.liveness = liveness;
        this.energy = energy;
        this.danceability = danceability;
    }


    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }

    public String getTitle() {
        return song_title;
    }

    public void setTitle(String song_title) {
        this.song_title = song_title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getLiveness() {
        return liveness;
    }

    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getDanceability() {
        return danceability;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

}
