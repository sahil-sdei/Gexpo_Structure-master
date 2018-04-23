package ggn.home.help.features.comments;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityCommentsBinding;
import ggn.home.help.databinding.ActivityRecyclerViewBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.notifications.Notifications;

public class CommentsActivity extends BaseActivity<ActivityCommentsBinding, CommentsPresenter> implements CommentsView, View.OnClickListener {

    private List<Comments> list;
    private CommentsAdapter commentsAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, CommentsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new CommentsPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return CommentsActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Comments");

        list = new ArrayList<>();
        list.add(new Comments("Andre Nil", "Nice!", "1h", "img1"));
        list.add(new Comments("Eve Waugh", "Great buddy.", "yesterday", "img2"));

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        commentsAdapter = new CommentsAdapter(list, getActivityG(), getPresenter());
        commentsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(commentsAdapter);


        getDataBinder().imageViewSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewSend:
                list.add(new Comments("Andre Nil", getDataBinder().editTextComment.getText().toString(), "just now", "img1"));
                commentsAdapter.notifyDataSetChanged();
                getDataBinder().editTextComment.setText("");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataBinder().recyclerView.smoothScrollToPosition(list.size() - 1);
                    }
                }, 100);
                break;
        }
    }
}
