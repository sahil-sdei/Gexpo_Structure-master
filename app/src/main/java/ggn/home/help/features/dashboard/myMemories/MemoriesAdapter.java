package ggn.home.help.features.dashboard.myMemories;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemMemoriesBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

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
        baseViewHolder.binding.setBinder(memoriesAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
