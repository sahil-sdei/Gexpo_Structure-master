package ggn.home.help.features.dashboard.menu;

import android.content.Context;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemAccountsBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.Child;

public class AccountsAdapter extends InfiniteAdapterG<ItemAccountsBinding> {

    private List<Child> dataList;
    private Context context;
    private AccountsAdapterBinder accountsAdapterBinder;

    public AccountsAdapter(List<Child> dataList, Context context) {
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
        baseViewHolder.binding.relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountsAdapterBinder.onAccountClicked();
            }
        });

        ImageLoader.loadImageSmall(baseViewHolder.binding.imageViewProfilePic, "http://18.216.102.186/memoreeta/files/profileimage/"+dataList.get(position).profileImage);


        baseViewHolder.binding.executePendingBindings();
    }

    public void setAccountsAdapterBinder(AccountsAdapterBinder accountsAdapterBinder) {
        this.accountsAdapterBinder = accountsAdapterBinder;
    }
}
