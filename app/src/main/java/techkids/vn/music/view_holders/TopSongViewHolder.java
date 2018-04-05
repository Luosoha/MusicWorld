package techkids.vn.music.view_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class TopSongViewHolder extends RecyclerView.ViewHolder {

  private static final String TAG = TopSongViewHolder.class.toString();

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
            Log.d(TAG, "onFailure: " + t.toString());
          }
        });
      }
    });
  }

}
