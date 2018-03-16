package ggn.home.help.features.accounts;

import android.content.Context;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemManageAccountsBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class ManageAccountsAdapter extends InfiniteAdapterG<ItemManageAccountsBinding> {

    private List<String> dataList;
    private ManageAccountsAdapterBinder manageAccountsAdapterBinder;
    private Context context;

    public ManageAccountsAdapter(List<String> dataList, Context context, ManageAccountsAdapterBinder manageAccountsAdapterBinder) {
        this.dataList = dataList;
        this.manageAccountsAdapterBinder = manageAccountsAdapterBinder;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_manage_accounts;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        baseViewHolder.binding.setBinder(manageAccountsAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
