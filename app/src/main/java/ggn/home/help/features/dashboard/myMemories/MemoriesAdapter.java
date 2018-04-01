package ggn.home.help.features.dashboard.myMemories;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemMemoriesBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import me.relex.circleindicator.CircleIndicator;

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

        baseViewHolder.binding.imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.getMenuInflater().inflate(R.menu.menu_share, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
            }
        });

        baseViewHolder.binding.setBinder(memoriesAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
