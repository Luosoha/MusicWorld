package techkids.vn.music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.music.R;
import techkids.vn.music.events.PlaySongEvent;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.SearchSongResponseBody;
import techkids.vn.music.networks.models.Song;

/**
 * Created by Lush on 1/10/2017.
 */

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.TopSongViewHolder> {
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

  class TopSongViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.civ_top_song)
    CircleImageView civTopSong;
    @BindView(R.id.tv_top_song_name)
    TextView tvTopSongName;
    @BindView(R.id.tv_top_song_artist)
    TextView tvTopSongArtist;

    public TopSongViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(final Song song) {
      tvTopSongName.setText(song.getName());
      tvTopSongArtist.setText(song.getArtist());
      Picasso.with(this.itemView.getContext()).load(song.getIconUrl()).into(civTopSong);

      this.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String keyword = song.getName() + " " + song.getArtist();
          RetrofitContext.getSearchSong(keyword).enqueue(new Callback<SearchSongResponseBody>() {
            @Override
            public void onResponse(Call<SearchSongResponseBody> call, Response<SearchSongResponseBody> response) {
              SearchSongResponseBody songs = response.body();
              if (songs != null && !songs.getSongs().isEmpty()) {
                EventBus.getDefault().post(new PlaySongEvent(song, songs.getSongUrl(), true));
              }
            }

            @Override
            public void onFailure(Call<SearchSongResponseBody> call, Throwable t) {

            }
          });
        }
      });
    }
  }

}
