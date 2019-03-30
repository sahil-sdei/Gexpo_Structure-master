package ggn.home.help.features.comments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityCommentsBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.response.CommentListResponse;
import ggn.home.help.web.response.PostsResponse;

public class CommentsActivity extends BaseActivity<ActivityCommentsBinding, CommentsPresenter> implements CommentsView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private CommentsAdapter commentsAdapter;
    boolean isCommentAdded = false;
    private PostsResponse.Datum postObj;
    private List<CommentListResponse.Datum> listComments;

    public static void start(Context context, String postID) {
        Intent starter = new Intent(context, CommentsActivity.class);
        starter.putExtra(Constants.Extras.POST_ID, postID);
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

        postObj = (PostsResponse.Datum) getIntent().getSerializableExtra(Constants.Extras.DATA);
    }

    @Override
    public Context getActivityG() {
        return CommentsActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Comments");
        getDataBinder().imageViewSend.setOnClickListener(this);

        getDataBinder().swipeRefreshLayout.setOnRefreshListener(this);

        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getComments(1, postObj.id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewSend:
                if (!TextUtils.isEmpty(getDataBinder().editTextComment.getText().toString())) {
                    getPresenter().addComment(postObj.id, getDataBinder().editTextComment.getText().toString());
                    getDataBinder().swipeRefreshLayout.setRefreshing(true);
                    getDataBinder().imageViewSend.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showComments(CommentListResponse output) {
        listComments = output.data;
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        getDataBinder().imageViewSend.setEnabled(true);
        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        commentsAdapter = new CommentsAdapter(output.data, getActivityG(), getPresenter());
        commentsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(commentsAdapter);

        if (isCommentAdded) {
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    getDataBinder().recyclerView.smoothScrollToPosition(list.size() - 1);
//                }
//            }, 100);
            isCommentAdded = false;
        }
    }

    @Override
    public void noDataFound() {
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        getDataBinder().imageViewSend.setEnabled(true);

    }

    @Override
    public void commentAddedSuccessfully() {
        UtillsG.hideKeyboard(getActivityG(), getParentView());
        isCommentAdded = true;
        getDataBinder().editTextComment.setText("");
        getDataBinder().imageViewSend.setEnabled(false);
        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getComments(1, postObj.id);
    }

    @Override
    public void onRefresh() {
        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getComments(1, postObj.id);
    }

    @Override
    public void onBackPressed() {
        if (listComments != null) {
            Intent intent = getIntent();
            intent.putExtra(Constants.Extras.DATA, postObj);
            intent.putExtra(Constants.Extras.COMMENT_COUNT, listComments.size());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void commentDeletedSuccessfully(CommentListResponse.Datum commentObj) {
        listComments.remove(commentObj);
        commentsAdapter.notifyDataSetChanged();
    }
}
