package hails.awesome.music.screens.topsongs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import hails.awesome.music.R;
import hails.awesome.music.callbacks.OnTopSongClickListener;
import hails.awesome.music.networks.models.Song;

/**
 * The top song adapter
 */

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.TopSongViewHolder> {

  private OnTopSongClickListener onTopSongClickListener;
  private ArrayList<Song> playList;

  public TopSongAdapter(OnTopSongClickListener onTopSongClickListener, ArrayList<Song> playList) {
    this.onTopSongClickListener = onTopSongClickListener;
    this.playList = playList;
  }

  @Override
  public TopSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.item_top_song, parent, false);
    return new TopSongViewHolder(view);
  }

  @Override
  public void onBindViewHolder(TopSongViewHolder holder, int position) {
    holder.bind(playList.get(position));
  }

  @Override
  public int getItemCount() {
    return playList.size();
  }

  public void filterSong(ArrayList<Song> filterSongList) {
    playList = filterSongList;
    notifyDataSetChanged();
  }

  class TopSongViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.civ_top_song)
    CircleImageView topSongCiv;
    @BindView(R.id.tv_top_song_name)
    TextView topSongNameTv;
    @BindView(R.id.tv_top_song_artist)
    TextView topSongArtistTv;

    public TopSongViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(final Song song) {
      topSongNameTv.setText(song.getName());
      topSongArtistTv.setText(song.getArtist());
      Picasso.with(this.itemView.getContext()).load(song.getIconUrl()).into(topSongCiv);

      this.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (onTopSongClickListener != null) {
            onTopSongClickListener.onSongClick(song);
          }
        }
      });
    }
  }

}
