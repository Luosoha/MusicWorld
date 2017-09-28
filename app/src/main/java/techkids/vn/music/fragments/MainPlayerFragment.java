package techkids.vn.music.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.music.MainActivity;
import techkids.vn.music.R;
import techkids.vn.music.events.MusicProgressChangedEvent;
import techkids.vn.music.events.OpenMainPlayerEvent;
import techkids.vn.music.events.BackFromMainPlayerEvent;
import techkids.vn.music.events.PauseTheMusicFromMainPlayerEvent;
import techkids.vn.music.events.PlaySongEvent;
import techkids.vn.music.events.ResumeTheMusicFromMainPlayerEvent;
import techkids.vn.music.events.SongIsReadyEvent;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.json_models.SearchSongResponseBody;
import techkids.vn.music.networks.json_models.Song;
import techkids.vn.music.utils.ActionHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerFragment extends Fragment {

    private static final String TAG = MainPlayerFragment.class.toString();

    @BindView(R.id.iv_song_image)
    ImageView ivSongImage;

    @BindView(R.id.sb_progress)
    SeekBar sbProgress;

    @BindView(R.id.sb_transparent_progress)
    SeekBar sbTransparentProgress;

    @BindView(R.id.fab_action)
    FloatingActionButton fabAction;

    private Runnable mRunnable;
    private Handler mHandler;
    private Song currentSong;
    private boolean songIsPlaying;

    public MainPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_player, container, false);

        ButterKnife.bind(this, view);
        init();
        addListener();

        startSeekbarProgress();
        mHandler.postDelayed(mRunnable, 100);

        return view;
    }

    private void init() {
        ((MainActivity)getActivity()).getSupportActionBar().show();
        mHandler = new Handler();
    }

    private void startSeekbarProgress() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (songIsPlaying) {
                    sbProgress.setProgress(sbProgress.getProgress() + 100);
                    sbTransparentProgress.setProgress(sbTransparentProgress.getProgress() + 100);
                }
                mHandler.postDelayed(this, 100);
            }
        };
    }

    private void addListener() {
        sbTransparentProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sbProgress.setProgress(sbTransparentProgress.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mRunnable);
                sbProgress.setProgress(sbTransparentProgress.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sbProgress.setProgress(sbTransparentProgress.getProgress());
                mHandler.postDelayed(mRunnable, 100);
                EventBus.getDefault().post(new MusicProgressChangedEvent(sbTransparentProgress.getProgress()));
            }
        });

        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sbTransparentProgress.setProgress(sbProgress.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mRunnable);
                sbTransparentProgress.setProgress(sbProgress.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sbTransparentProgress.setProgress(sbProgress.getProgress());
                mHandler.postDelayed(mRunnable, 100);
                EventBus.getDefault().post(new MusicProgressChangedEvent(sbProgress.getProgress()));
            }
        });
    }

    @OnClick(R.id.fab_action)
    public void onFabActionClick() {
        if (songIsPlaying) {
            fabAction.setImageResource(R.drawable.ic_play_arrow_white_24px);
            songIsPlaying = !songIsPlaying;
            mHandler.removeCallbacks(mRunnable);
            // Send event to main activity to pause the music
            EventBus.getDefault().post(new PauseTheMusicFromMainPlayerEvent());

        } else {
            fabAction.setImageResource(R.drawable.ic_pause_white_24px);
            songIsPlaying = !songIsPlaying;
            mHandler.postDelayed(mRunnable, 100);
            // Send event to main activity to resume the music
            EventBus.getDefault().post(new ResumeTheMusicFromMainPlayerEvent());
        }
    }

    @OnClick(R.id.iv_skip_previous)
    public void onPreviousSongClick() {
        Song song = Song.SONGS.get(ActionHelper.findPreviousSongPositionOf(currentSong));
        currentSong = song;
        songIsPlaying = false;
        sbProgress.setProgress(0);
        sbTransparentProgress.setProgress(0);
        Picasso.with(getActivity()).load(song.getImageUrl()).into(ivSongImage);
        getSongSourceForMainActivity(song);
    }

    @OnClick(R.id.iv_skip_next)
    public void onNextSongClick() {
        Song song = Song.SONGS.get(ActionHelper.findNextSongPositionOf(currentSong));
        currentSong = song;
        songIsPlaying = false;
        sbProgress.setProgress(0);
        sbTransparentProgress.setProgress(0);
        Picasso.with(getActivity()).load(song.getImageUrl()).into(ivSongImage);
        getSongSourceForMainActivity(song);
    }

    private void getSongSourceForMainActivity(final Song song) {
        final String[] result = {null};
        String data = "{\"q\":\"" + song.getName() + " " + song.getArtist()
                + "\", \"sort\":\"hot\", \"start\":\"0\", \"length\":\"10\"}";
        RetrofitContext.getSearchSong(data).enqueue(new Callback<SearchSongResponseBody>() {
            @Override
            public void onResponse(Call<SearchSongResponseBody> call, Response<SearchSongResponseBody> response) {
                Log.d(TAG, "onResponse");
                SearchSongResponseBody songs = response.body();
                Log.d(TAG, String.valueOf(songs.getSongs().length));
                result[0] = songs.getSongs()[0].getSongSource();
                EventBus.getDefault().post(new PlaySongEvent(song, result[0], false));
            }

            @Override
            public void onFailure(Call<SearchSongResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    @Override
    public void onStop() {
        EventBus.getDefault().post(new BackFromMainPlayerEvent());
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onEvent(OpenMainPlayerEvent openMainPlayerEvent) {
        currentSong = openMainPlayerEvent.getCurrentSong();
        songIsPlaying = openMainPlayerEvent.isPlaying();

        if (songIsPlaying) {
            fabAction.setImageResource(R.drawable.ic_pause_white_24px);
        } else {
            fabAction.setImageResource(R.drawable.ic_play_arrow_white_24px);
        }

        sbProgress.setMax(openMainPlayerEvent.getDuration());
        sbTransparentProgress.setMax(openMainPlayerEvent.getDuration());
        sbProgress.setProgress(openMainPlayerEvent.getCurrentPosition());
        sbTransparentProgress.setProgress(openMainPlayerEvent.getCurrentPosition());
        Picasso.with(getActivity()).load(openMainPlayerEvent.getCurrentSong().getImageUrl()).into(ivSongImage);
        EventBus.getDefault().removeStickyEvent(OpenMainPlayerEvent.class);
    }

    @Subscribe(sticky = true)
    public void onEvent(SongIsReadyEvent songIsReadyEvent) {
        Log.d(TAG, "SongIsReadyEvent received: " + songIsReadyEvent.getDuration());
        sbProgress.setMax(songIsReadyEvent.getDuration());
        sbTransparentProgress.setMax(songIsReadyEvent.getDuration());
        songIsPlaying = true;
        EventBus.getDefault().removeStickyEvent(SongIsReadyEvent.class);
    }

}
