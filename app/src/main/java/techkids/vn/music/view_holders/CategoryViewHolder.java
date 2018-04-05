package techkids.vn.music.view_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = CategoryViewHolder.class.toString();

    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;

    @BindView(R.id.iv_category)
    ImageView ivCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Subgenres subgenres) {
        tvCategoryName.setText(subgenres.getName());
        String src = "genre_" + subgenres.getId();
        int rid = this.ivCategory.getResources().getIdentifier(src,
                "drawable", this.ivCategory.getContext().getPackageName());
        if (rid != 0) {
            Picasso.with(this.itemView.getContext()).load(rid).into(ivCategory);
        }

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, subgenres.getName());
                EventBus.getDefault().post(new OpenTopsongsFragmentEvent(new TopsongsFragment(), true, subgenres));
            }
        });
    }

}
