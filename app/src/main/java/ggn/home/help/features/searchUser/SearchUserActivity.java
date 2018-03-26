package ggn.home.help.features.searchUser;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityManageAccountsBinding;
import ggn.home.help.databinding.ActivitySearchUserBinding;
import ggn.home.help.features.internal.base.BaseActivity;

public class SearchUserActivity extends BaseActivity<ActivitySearchUserBinding, SearchUserPresenter> implements SearchUserView {

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchUserActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search_user;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SearchUserPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return SearchUserActivity.this;
    }

    @Override
    public void initViews() {
        getDataBinder().editTextSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                    getDataBinder().recyclerViewUsers.setVisibility(View.VISIBLE);
                else
                    getDataBinder().recyclerViewUsers.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");

        getDataBinder().recyclerViewUsers.setHasFixedSize(true);
        getDataBinder().recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(list, getActivityG(), getPresenter());
        searchUserAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewUsers.setAdapter(searchUserAdapter);
    }
}
