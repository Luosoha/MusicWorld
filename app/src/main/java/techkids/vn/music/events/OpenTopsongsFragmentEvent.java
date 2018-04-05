package techkids.vn.music.events;

import android.support.v4.app.Fragment;

import techkids.vn.music.fragments.TopsongsFragment;
import techkids.vn.music.networks.models.Subgenres;

/**
 * Created by Lush on 1/9/2017.
 */

public class OpenTopsongsFragmentEvent {

    private TopsongsFragment fragment;
    private boolean addToBackStack;
    private Subgenres subgenres;

    public OpenTopsongsFragmentEvent(TopsongsFragment fragment, boolean addToBackStack, Subgenres subgenres) {
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
        this.subgenres = subgenres;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }

    public Subgenres getSubgenres() {
        return subgenres;
    }

}
