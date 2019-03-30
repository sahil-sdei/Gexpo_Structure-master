package ggn.home.help.features.friendsNFamily;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemRequestsBinding;
import ggn.home.help.databinding.ItemSubCategoriesBinding;
import ggn.home.help.features.addMemories.AddMemoryAdapterBinder;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.FriendRequestsResponse;

public class RequestsAdapter extends InfiniteAdapterG<ItemRequestsBinding> {

    private List<FriendRequestsResponse.Datum> dataList;
    private Context context;
    private RequestsAdapterBinder requestsAdapterBinder;

    public RequestsAdapter(List<FriendRequestsResponse.Datum> dataList, Context context, RequestsAdapterBinder requestsAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.requestsAdapterBinder = requestsAdapterBinder;
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
        baseViewHolder.binding.setBinder(requestsAdapterBinder);

        if(!TextUtils.isEmpty(dataList.get(position).profileImage))
            ImageLoader.loadImageSmall(baseViewHolder.binding.imageViewProfilePic, dataList.get(position).profileImage);
        else
            baseViewHolder.binding.imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);

        baseViewHolder.binding.executePendingBindings();
    }
}
