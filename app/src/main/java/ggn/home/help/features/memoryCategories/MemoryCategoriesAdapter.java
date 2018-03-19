package ggn.home.help.features.memoryCategories;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemAddMemoryBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class MemoryCategoriesAdapter extends InfiniteAdapterG<ItemAddMemoryBinding> {

    private List<String> dataList;
    private Context context;
    private MemoryCategoriesAdapterBinder memoryCategoriesAdapterBinder;

    public MemoryCategoriesAdapter(List<String> dataList, Context context, MemoryCategoriesAdapterBinder memoryCategoriesAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.memoryCategoriesAdapterBinder = memoryCategoriesAdapterBinder;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_add_memory;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        baseViewHolder.binding.setBinder(memoryCategoriesAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
