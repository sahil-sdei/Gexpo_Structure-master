package ggn.home.help.features.dashboard.myMemories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemMemoriesBinding;
import ggn.home.help.features.comments.CommentsActivity;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.share.ShareActivity;

public class MemoriesAdapter extends InfiniteAdapterG<ItemMemoriesBinding> {

    private List<Memory> dataList;
    private Context context;
    private MemoriesAdapterBinder memoriesAdapterBinder;

    public MemoriesAdapter(List<Memory> dataList, Context context, MemoriesAdapterBinder memoriesAdapterBinder) {
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
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));

        SlidingImageAdapter slidingImageAdapter = new SlidingImageAdapter(context, dataList.get(position).listImages);
        baseViewHolder.binding.viewPagerImages.setAdapter(slidingImageAdapter);
        baseViewHolder.binding.indicator.setViewPager(baseViewHolder.binding.viewPagerImages);

//        if(dataList.get(position).isLiked){
//            baseViewHolder.binding.textViewLikes.setCompoundDrawables(,0, 0, 0);
//        }

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
                CommentsActivity.start(context);
            }
        });

        baseViewHolder.binding.setBinder(memoriesAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
