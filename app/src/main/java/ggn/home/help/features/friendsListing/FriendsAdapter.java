package ggn.home.help.features.friendsListing;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemFriendsFamilyBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.AllFriendsResponse;

public class FriendsAdapter extends InfiniteAdapterG<ItemFriendsFamilyBinding> {

    private List<AllFriendsResponse.Datum> dataList;
    private Context context;
    private FriendsAdapterBinder commentsAdapterBinder;

    public FriendsAdapter(List<AllFriendsResponse.Datum> dataList, Context context, FriendsAdapterBinder commentsAdapterBinder) {
        this.commentsAdapterBinder = commentsAdapterBinder;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_friends_family;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(final int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        baseViewHolder.binding.setBinder(commentsAdapterBinder);

        if (!TextUtils.isEmpty(dataList.get(position).profileImage))
            ImageLoader.loadImageSmall(baseViewHolder.binding.imageViewProfilePic, dataList.get(position).profileImage);
        else
            baseViewHolder.binding.imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);

        baseViewHolder.binding.executePendingBindings();
    }
}
