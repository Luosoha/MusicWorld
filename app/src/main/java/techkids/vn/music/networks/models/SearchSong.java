package techkids.vn.music.networks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lush on 1/15/2017.
 */

public class SearchSong {

    @SerializedName("title")
    private String title;

    @SerializedName("artist")
    private String artist;

    @SerializedName("source")
    private SongSource songSource;

    public SearchSong(String title, String artist, SongSource songSource) {
        this.title = title;
        this.artist = artist;
        this.songSource = songSource;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongSource() {
        return songSource.getSource();
    }

    @Override
    public String toString() {
        return "SearchSong{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", songSource=" + songSource +
                '}';
    }

    class SongSource {
        @SerializedName("128")
        private String source;

        public SongSource(String source) {
            this.source = source;
        }

        public String getSource() {
            return source;
        }

        @Override
        public String toString() {
            return "SongSource{" +
                    "source='" + source + '\'' +
                    '}';
        }
    }
}
