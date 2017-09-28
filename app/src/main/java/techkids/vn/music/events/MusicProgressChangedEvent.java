package techkids.vn.music.events;

/**
 * Created by Lush on 1/21/2017.
 */

public class MusicProgressChangedEvent {
    private int position;

    public MusicProgressChangedEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
