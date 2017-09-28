package techkids.vn.music.events;

/**
 * Created by Lush on 1/21/2017.
 */

public class SongIsReadyEvent {
    private int duration;

    public SongIsReadyEvent(int duration) {
        this.duration = duration;
    }


    public int getDuration() {
        return duration;
    }
}
