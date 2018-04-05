package techkids.vn.music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.vn.music.R;
import techkids.vn.music.networks.models.Subgenres;
import techkids.vn.music.view_holders.CategoryViewHolder;

/**
 * Created by Lush on 1/9/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_genre, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.bind(Subgenres.subgenres.get(position));
    }

    @Override
    public int getItemCount() {
        return Subgenres.subgenres.size();
    }

}
