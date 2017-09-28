package techkids.vn.music.events;

import techkids.vn.music.networks.json_models.Song;

/**
 * Created by Lush on 1/15/2017.
 */

public class PlaySongEvent {
    private Song song;
    private String songSource;
    private boolean revealMiniPlayer;

    public PlaySongEvent(Song song, String songSource, boolean revealMiniPlayer) {
        this.song = song;
        this.songSource = songSource;
        this.revealMiniPlayer = revealMiniPlayer;
    }

    public Song getSong() {
        return song;
    }

    public String getSongSource() {
        return songSource;
    }

    public boolean isRevealMiniPlayer() {
        return revealMiniPlayer;
    }
}
