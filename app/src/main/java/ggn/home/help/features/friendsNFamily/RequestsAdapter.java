package ggn.home.help.features.friendsNFamily;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemRequestsBinding;
import ggn.home.help.databinding.ItemSubCategoriesBinding;
import ggn.home.help.features.addMemories.AddMemoryAdapterBinder;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.memoryCategories.SubCategories;

public class RequestsAdapter extends InfiniteAdapterG<ItemRequestsBinding> {

    private List<String> dataList;
    private Context context;

    public RequestsAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_requests;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        baseViewHolder.binding.executePendingBindings();
    }
}
