package techkids.vn.music.events;

import techkids.vn.music.networks.json_models.Song;

/**
 * Created by Lush on 1/20/2017.
 */

public class OpenMainPlayerEvent {
    private Song currentSong;
    private int currentPosition;
    private int duration;
    private boolean isPlaying;

    public OpenMainPlayerEvent(Song currentSong, int currentPosition, int duration, boolean isPlaying) {
        this.currentSong = currentSong;
        this.currentPosition = currentPosition;
        this.duration = duration;
        this.isPlaying = isPlaying;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
