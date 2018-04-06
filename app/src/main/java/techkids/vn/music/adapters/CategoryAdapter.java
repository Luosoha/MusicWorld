package techkids.vn.music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.music.R;
import techkids.vn.music.events.OpenTopsongsFragmentEvent;
import techkids.vn.music.fragments.TopsongsFragment;
import techkids.vn.music.networks.models.Subgenres;

/**
 * Created by Lush on 1/9/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

  @Override
  public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.item_genre, parent, false);
    return new CategoryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CategoryViewHolder holder, int position) {
    holder.bind(Subgenres.subgenres.get(position));
  }

  @Override
  public int getItemCount() {
    return Subgenres.subgenres.size();
  }

  class CategoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_category_name)
    TextView categoryNameTv;
    @BindView(R.id.iv_category)
    ImageView categoryIv;

    public CategoryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(final Subgenres subgenres) {
      categoryNameTv.setText(subgenres.getName());
      String src = "genre_" + subgenres.getId();
      int rid = this.categoryIv.getResources().getIdentifier(src,
              "drawable", this.categoryIv.getContext().getPackageName());
      if (rid != 0) {
        Picasso.with(this.itemView.getContext()).load(rid).into(categoryIv);
      }

      this.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          EventBus.getDefault().post(new OpenTopsongsFragmentEvent(new TopsongsFragment(), true, subgenres));
        }
      });
    }
  }

}
