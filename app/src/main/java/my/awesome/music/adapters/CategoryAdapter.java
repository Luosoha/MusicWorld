package my.awesome.music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.awesome.music.R;
import my.awesome.music.networks.models.Subgenres;

/**
 * Created by Lush on 1/9/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

  private OnCategoryClickListener onCategoryClickListener;

  public CategoryAdapter(OnCategoryClickListener onCategoryClickListener) {
    this.onCategoryClickListener = onCategoryClickListener;
  }

  @Override
  public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.item_genre, parent, false);
    return new CategoryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CategoryViewHolder holder, int position) {
    final Subgenres subgenres = Subgenres.subgenres.get(position);
    holder.categoryNameTv.setText(subgenres.getName());
    String src = "genre_" + subgenres.getId();
    int rid = holder.categoryIv.getResources().getIdentifier(
            src, "drawable", holder.categoryIv.getContext().getPackageName()
    );
    if (rid != 0) {
      Picasso.with(holder.itemView.getContext()).load(rid).into(holder.categoryIv);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onCategoryClickListener != null) {
          onCategoryClickListener.onCategoryClick(subgenres);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return Subgenres.subgenres.size();
  }

  public interface OnCategoryClickListener {
    void onCategoryClick(Subgenres subgenres);
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
  }

}
