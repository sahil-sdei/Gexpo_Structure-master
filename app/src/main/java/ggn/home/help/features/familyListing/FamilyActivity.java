package ggn.home.help.features.familyListing;

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
import ggn.home.help.web.response.AllFamilyResponse;
import ggn.home.help.web.response.AllFriendsResponse;

public class FamilyActivity extends BaseActivity<ActivityRecyclerViewBinding, FamilyPresenter> implements FamilyView, View.OnClickListener {

    private FamilyAdapter commentsAdapter;
    private List<AllFamilyResponse.Datum> listFamily;

    public static void start(Context context, String type) {
        Intent starter = new Intent(context, FamilyActivity.class);
        starter.putExtra(Constants.Extras.TYPE, type);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new FamilyPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return FamilyActivity.this;
    }

    @Override
    public void initViews() {
        String type = getIntent().getStringExtra(Constants.Extras.TYPE);
        setupToolbar(type);

    }

    @Override
    protected void onResume() {
        super.onResume();
        listFamily = new ArrayList<>();
        getPresenter().getFamily(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void noDataFound() {
        getDataBinder().textViewNoRecords.setVisibility(View.VISIBLE);
        listFamily.clear();
        if (commentsAdapter != null)
            commentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFamily(AllFamilyResponse output) {
        listFamily = output.data;
        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        commentsAdapter = new FamilyAdapter(listFamily, getActivityG(), getPresenter());
        commentsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(commentsAdapter);
        getDataBinder().textViewNoRecords.setVisibility(View.GONE);
    }
}
