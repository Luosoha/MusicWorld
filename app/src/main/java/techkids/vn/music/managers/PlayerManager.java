package techkids.vn.music.managers;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;

/**
 * Created by HaiLS on 09/04/2018.
 */

public class PlayerManager {

  private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
  private static final int BUFFER_SEGMENT_COUNT = 256;

  private static ExoPlayer exoPlayer;
  private static ExtractorSampleSource sampleSource;
  private static DefaultAllocator allocator;
  private static String userAgent;
  private static DefaultUriDataSource dataSource;
  private static MediaCodecAudioTrackRenderer audioRenderer;

  private PlayerManager() { }

  public static void playNewSong(String songUrl) {
    if (exoPlayer != null) {
      exoPlayer.seekTo(0);
    }
    Uri radioUri = Uri.parse(songUrl);
    sampleSource = new ExtractorSampleSource(
            radioUri, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT
    );
    audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
    exoPlayer.prepare(audioRenderer);
    exoPlayer.setPlayWhenReady(true);
  }

  public static ExoPlayer getInstance() {
    return exoPlayer;
  }

  public static void init(Context context) {
    if (exoPlayer == null) {
      exoPlayer = ExoPlayer.Factory.newInstance(1);
      allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
      userAgent = Util.getUserAgent(context, "ExoPlayerDemo");
      dataSource = new DefaultUriDataSource(context, null, userAgent);
    }
  }

}
