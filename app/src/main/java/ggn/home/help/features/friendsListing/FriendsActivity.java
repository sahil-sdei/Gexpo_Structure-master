package ggn.home.help.features.friendsListing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityRecyclerViewBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.AllFriendsResponse;

public class FriendsActivity extends BaseActivity<ActivityRecyclerViewBinding, FriendsPresenter> implements FriendsView, View.OnClickListener {

    private FriendsAdapter commentsAdapter;
    private List<AllFriendsResponse.Datum> listFriends;

    public static void start(Context context, String type) {
        Intent starter = new Intent(context, FriendsActivity.class);
        starter.putExtra(Constants.Extras.TYPE, type);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new FriendsPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return FriendsActivity.this;
    }

    @Override
    public void initViews() {
        String type = getIntent().getStringExtra(Constants.Extras.TYPE);
        setupToolbar(type);

    }

    @Override
    protected void onResume() {
        super.onResume();
        listFriends = new ArrayList<>();
        getPresenter().getFriends(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void showFriends(AllFriendsResponse output) {
        listFriends = output.data;
        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        commentsAdapter = new FriendsAdapter(listFriends, getActivityG(), getPresenter());
        commentsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(commentsAdapter);
        getDataBinder().textViewNoRecords.setVisibility(View.GONE);

    }

    @Override
    public void noDataFound() {
        getDataBinder().textViewNoRecords.setVisibility(View.VISIBLE);
        listFriends.clear();
        if (commentsAdapter != null)
            commentsAdapter.notifyDataSetChanged();
    }
}
