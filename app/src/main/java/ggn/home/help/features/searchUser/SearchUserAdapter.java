package ggn.home.help.features.searchUser;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemManageAccountsBinding;
import ggn.home.help.databinding.ItemSearchUserBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.SearchUserResponse;

public class SearchUserAdapter extends InfiniteAdapterG<ItemSearchUserBinding> {

    private List<SearchUserResponse.Datum> dataList;
    private SearchUserAdapterBinder searchUserAdapterBinder;
    private Context context;

    public SearchUserAdapter(List<SearchUserResponse.Datum> dataList, Context context, SearchUserAdapterBinder searchUserAdapterBinder) {
        this.dataList = dataList;
        this.searchUserAdapterBinder = searchUserAdapterBinder;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_search_user;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));

        if(!TextUtils.isEmpty(dataList.get(position).profileImage)){
            ImageLoader.loadImageSmall(baseViewHolder.binding.imageViewProfilePic, dataList.get(position).profileImage);
        }else {
            baseViewHolder.binding.imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);
        }

        baseViewHolder.binding.setBinder(searchUserAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
