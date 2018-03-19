package ggn.home.help.features.profile;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemFamilyBinding;
import ggn.home.help.databinding.ItemMemoriesBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class FamilyAdapter extends InfiniteAdapterG<ItemFamilyBinding> {

    private List<String> dataList;
    private Context context;
    private FamilyFriendsAdapterBinder familyFriendsAdapterBinder;

    public FamilyAdapter(List<String> dataList, Context context, FamilyFriendsAdapterBinder familyFriendsAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.familyFriendsAdapterBinder = familyFriendsAdapterBinder;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_family;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        baseViewHolder.binding.setBinder(familyFriendsAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
