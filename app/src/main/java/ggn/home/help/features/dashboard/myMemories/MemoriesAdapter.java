package ggn.home.help.features.dashboard.myMemories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemMemoriesBinding;
import ggn.home.help.features.comments.CommentsActivity;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.share.ShareActivity;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.PostsResponse;

public class MemoriesAdapter extends InfiniteAdapterG<ItemMemoriesBinding> {

    private List<PostsResponse.Datum> dataList;
    private Context context;
    private MemoriesAdapterBinder memoriesAdapterBinder;

    public MemoriesAdapter(List<PostsResponse.Datum> dataList, Context context, MemoriesAdapterBinder memoriesAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.memoriesAdapterBinder = memoriesAdapterBinder;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_memories;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(final int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));

        SlidingImageAdapter slidingImageAdapter = new SlidingImageAdapter(context, dataList.get(position).galleries);
        baseViewHolder.binding.viewPagerImages.setAdapter(slidingImageAdapter);
        baseViewHolder.binding.indicator.setViewPager(baseViewHolder.binding.viewPagerImages);

        if(!TextUtils.isEmpty(dataList.get(position).user.profileImage))
            ImageLoader.loadUserImageSmall(baseViewHolder.binding.imageViewProfilePic, dataList.get(position).user.profileImage);
        else
            baseViewHolder.binding.imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);

        if (dataList.get(position).galleries != null) {
            if (dataList.get(position).galleries.size() > 1) {
                baseViewHolder.binding.indicator.setVisibility(View.VISIBLE);
            } else {
                baseViewHolder.binding.indicator.setVisibility(View.INVISIBLE);
            }
        }

        if (dataList.get(position).isUserLiked == 1) {
            baseViewHolder.binding.textViewLikes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_smile_like, 0, 0, 0);
        } else {
            baseViewHolder.binding.textViewLikes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_smile_unlike, 0, 0, 0);
        }

        if (!TextUtils.isEmpty(dataList.get(position).createdAt))
            baseViewHolder.binding.textViewTimeAgo.setText(UtillsG.getTimeAgo(dataList.get(position).createdAt));

        baseViewHolder.binding.imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.getMenuInflater().inflate(R.menu.menu_share, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equalsIgnoreCase(context.getString(R.string.share_in_the_memoreeta))) {
                            ShareActivity.start(context);
                        } else {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Here is the share content body";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        baseViewHolder.binding.textViewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CommentsActivity.start(context, dataList.get(position).id);
                memoriesAdapterBinder.showComments(dataList.get(position));
            }
        });

        baseViewHolder.binding.setBinder(memoriesAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
