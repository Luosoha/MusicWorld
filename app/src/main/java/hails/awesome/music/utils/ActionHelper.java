package hails.awesome.music.utils;

import hails.awesome.music.networks.models.Song;

/**
 * Created by Lush on 1/21/2017.
 */

public class ActionHelper {

    public static int findCurrentSongPositionOf(Song song) {
        for (int i = 0; i < Song.SONGS.size(); i++) {
            if (song.getName().equals(Song.SONGS.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public static int findPreviousSongPositionOf(Song song) {
        int currentPosition = findCurrentSongPositionOf(song);
        if (currentPosition == 0) {
            return 49;
        }
        return (currentPosition - 1);
    }

    public static int findNextSongPositionOf(Song song) {
        int currentPosition = findCurrentSongPositionOf(song);
        if (currentPosition == 49) {
            return 0;
        }
        return (currentPosition + 1);
    }

}
