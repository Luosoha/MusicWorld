package hails.awesome.music.utils;

import java.util.ArrayList;

import hails.awesome.music.networks.models.Song;

/**
 * Created by Lush on 1/21/2017.
 */

public class ActionHelper {

  public static int findCurrentSongPositionOf(ArrayList<Song> playList, Song song) {
    for (int i = 0; i < playList.size(); i++) {
      if (song.getName().equals(playList.get(i).getName())) {
        return i;
      }
    }
    return -1;
  }

  public static int findPreviousSongPositionOf(ArrayList<Song> playList, Song song) {
    int currentPosition = findCurrentSongPositionOf(playList, song);
    if (currentPosition == 0) {
      return playList.size() - 1;
    }
    return (currentPosition - 1);
  }

  public static int findNextSongPositionOf(ArrayList<Song> playList, Song song) {
    int currentPosition = findCurrentSongPositionOf(playList, song);
    if (currentPosition == playList.size() - 1) {
      return 0;
    }
    return (currentPosition + 1);
  }

}
