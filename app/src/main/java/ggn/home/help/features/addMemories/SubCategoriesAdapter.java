package ggn.home.help.features.addMemories;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemManageAccountsBinding;
import ggn.home.help.databinding.ItemSubCategoriesBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class SubCategoriesAdapter extends InfiniteAdapterG<ItemSubCategoriesBinding> {

    private List<String> dataList;
    private AddMemoryAdapterBinder addMemoryAdapterBinder;
    private Context context;

    public SubCategoriesAdapter(List<String> dataList, Context context, AddMemoryAdapterBinder addMemoryAdapterBinder) {
        this.dataList = dataList;
        this.addMemoryAdapterBinder = addMemoryAdapterBinder;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_sub_categories;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        baseViewHolder.binding.setBinder(addMemoryAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
