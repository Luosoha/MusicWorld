package techkids.vn.music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.vn.music.R;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.view_holders.TopSongViewHolder;

/**
 * Created by Lush on 1/10/2017.
 */

public class TopSongAdapter extends RecyclerView.Adapter<TopSongViewHolder> {
    @Override
    public TopSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_top_song, parent, false);
        TopSongViewHolder topSongViewHolder = new TopSongViewHolder(view);
        return topSongViewHolder;
    }

    @Override
    public void onBindViewHolder(TopSongViewHolder holder, int position) {
        holder.bind(Song.SONGS.get(position));
    }

    @Override
    public int getItemCount() {
        return Song.SONGS.size();
    }
}
