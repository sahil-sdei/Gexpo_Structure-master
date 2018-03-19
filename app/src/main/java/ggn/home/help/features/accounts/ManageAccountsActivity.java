package ggn.home.help.features.accounts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityManageAccountsBinding;
import ggn.home.help.features.internal.base.BaseActivity;

public class ManageAccountsActivity extends BaseActivity<ActivityManageAccountsBinding, ManageAccountsPresenter> implements ManageAccountsView {

    public static void start(Context context) {
        Intent starter = new Intent(context, ManageAccountsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_manage_accounts;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new ManageAccountsPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return ManageAccountsActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar(getString(R.string.manage_accounts));

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");

        getDataBinder().recyclerViewAccounts.setHasFixedSize(true);
        getDataBinder().recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        ManageAccountsAdapter manageAccountsAdapter = new ManageAccountsAdapter(list, getActivityG(), getPresenter());
        manageAccountsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewAccounts.setAdapter(manageAccountsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
}
