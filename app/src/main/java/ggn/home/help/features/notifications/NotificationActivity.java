package ggn.home.help.features.notifications;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityRecyclerViewBinding;
import ggn.home.help.features.internal.base.BaseActivity;

public class NotificationActivity extends BaseActivity<ActivityRecyclerViewBinding, NotificationPresenter> implements NotificationView, View.OnClickListener {

    private List<Notifications> list;
    private NotificationAdapter notificationAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, NotificationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new NotificationPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return NotificationActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar(getString(R.string.notifications));

        list = new ArrayList<>();
        list.add(new Notifications("Andre Nil", "sent you friend request.", "1h", "img1"));
        list.add(new Notifications("Eve Waugh", "added a new photo.", "yesterday", "img2"));
        list.add(new Notifications("Mark Waugh", "shared a video.", "2 days ago", "img3"));
        list.add(new Notifications("Tom Petty", "sent you friend request.", "3 days ago", "img4"));

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        notificationAdapter = new NotificationAdapter(list, getActivityG(), getPresenter());
        notificationAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(notificationAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
