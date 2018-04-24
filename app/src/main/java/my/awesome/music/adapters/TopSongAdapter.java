package my.awesome.music.adapters;

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
import my.awesome.music.R;
import my.awesome.music.callbacks.OnTopSongClickListener;
import my.awesome.music.networks.models.Song;

/**
 * Created by HaiLS on 05/04/2018.
 */

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.TopSongViewHolder> {

  private ArrayList<Song> playList;
  private OnTopSongClickListener onTopSongClickListener;

  public TopSongAdapter(ArrayList<Song> playList) {
    this.playList = playList;
  }

  public void setOnTopSongClickListener(OnTopSongClickListener onTopSongClickListener) {
    this.onTopSongClickListener = onTopSongClickListener;
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
    return playList == null ? 0 : playList.size();
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
