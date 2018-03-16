package ggn.home.help.features.dashboard.menu;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemAccountsBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class AccountsAdapter extends InfiniteAdapterG<ItemAccountsBinding> {

    private List<String> dataList;
    private Context context;

    public AccountsAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_accounts;
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
