package techkids.vn.music.events;

import techkids.vn.music.networks.models.Song;

/**
 * Created by Lush on 1/20/2017.
 */

public class OpenMainPlayerEvent {
    private Song currentSong;
    private boolean isPlaying;

    public OpenMainPlayerEvent(Song currentSong, boolean isPlaying) {
        this.currentSong = currentSong;
        this.isPlaying = isPlaying;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
