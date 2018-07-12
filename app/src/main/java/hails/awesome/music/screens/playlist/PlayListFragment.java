package hails.awesome.music.screens.playlist;

import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import hails.awesome.music.R;
import hails.awesome.music.base.ViewFragment;

/**
 * Created by HaiLS on 12/07/2018.
 */

public class PlayListFragment extends ViewFragment<PlayListContract.Presenter>
        implements PlayListContract.View {

  @BindView(R.id.play_list_rv)
  RecyclerView playListRv;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_play_list;
  }

}
