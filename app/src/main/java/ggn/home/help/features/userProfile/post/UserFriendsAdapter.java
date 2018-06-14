package ggn.home.help.features.userProfile.post;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemSearchUserBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.searchUser.SearchUserAdapterBinder;

public class UserFriendsAdapter extends InfiniteAdapterG<ItemSearchUserBinding> {

    private List<String> dataList;
    private SearchUserAdapterBinder searchUserAdapterBinder;
    private Context context;

    public UserFriendsAdapter(List<String> dataList, Context context, SearchUserAdapterBinder searchUserAdapterBinder) {
        this.dataList = dataList;
        this.searchUserAdapterBinder = searchUserAdapterBinder;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_friends_horizontal;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
//        baseViewHolder.binding.setData(dataList.get(position));
//        baseViewHolder.binding.setBinder(searchUserAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
