package ggn.home.help.features.userProfile.adapters;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemUserFriendsBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.web.response.AllFriendsResponse;

public class UserFriendsAdapter extends InfiniteAdapterG<ItemUserFriendsBinding> {

    private List<AllFriendsResponse.Datum> dataList;
    private Context context;

    public UserFriendsAdapter(List<AllFriendsResponse.Datum> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_user_friends;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
//        baseViewHolder.binding.setBinder(familyFriendsAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
