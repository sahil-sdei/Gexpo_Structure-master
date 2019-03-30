package ggn.home.help.features.accounts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityManageAccountsBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.ChildAccountsResponse;

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

        getPresenter().getChildAccounts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent resultIntent = getIntent();
                setResult(RESULT_OK, resultIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showChildAccounts(ChildAccountsResponse output) {
        if (output.data.childs.size() > 0) {
            getDataBinder().recyclerViewAccounts.setHasFixedSize(true);
            getDataBinder().recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
            ManageAccountsAdapter manageAccountsAdapter = new ManageAccountsAdapter(output.data.childs, getActivityG(), getPresenter());
            manageAccountsAdapter.setShouldLoadMore(false);
            getDataBinder().recyclerViewAccounts.setAdapter(manageAccountsAdapter);
            getDataBinder().textViewNoRecords.setVisibility(View.GONE);
            getDataBinder().recyclerViewAccounts.setVisibility(View.VISIBLE);
        } else {
            getDataBinder().recyclerViewAccounts.setVisibility(View.GONE);
            getDataBinder().textViewNoRecords.setVisibility(View.VISIBLE);
            getLocalData().saveChildAccounts(null);
        }
    }

    @Override
    public void accountTransferSuccessfully(BasicResponse output) {
        getPresenter().getChildAccounts();
    }

    @Override
    public void accountDeletedSuccessfully(BasicResponse output) {
        getPresenter().getChildAccounts();
    }
}
