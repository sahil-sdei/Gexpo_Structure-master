package ggn.home.help.features.comments;


import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.apiInterfaces.PostsAPI;
import ggn.home.help.web.request.AddCommentRequest;
import ggn.home.help.web.request.CommentListRequest;
import ggn.home.help.web.request.DeleteCommentRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.CommentListResponse;

public class CommentsPresenter extends BasePresenter<CommentsView> implements CommentsAdapterBinder {

    public void getComments(int pageNo, String postId) {
        CommentListRequest commentListRequest = new CommentListRequest();
        commentListRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        commentListRequest.token = getView().getLocalData().getAuthToken();
        commentListRequest.page = pageNo;
        commentListRequest.postId = Integer.parseInt(postId);

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(PostsAPI.class)
                .getPostComments(gson.toJson(commentListRequest)), new CallBackG<CommentListResponse>() {
            @Override
            public void callBack(CommentListResponse output) {
                if (output.status == 1)
                    getView().showComments(output);
                else {
                    getView().noDataFound();
                }
            }
        });
    }

    public void addComment(String postId, String comment) {
        AddCommentRequest addCommentRequest= new AddCommentRequest();
        addCommentRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        addCommentRequest.token = getView().getLocalData().getAuthToken();
        addCommentRequest.comment = comment;
        addCommentRequest.postId = postId;

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(PostsAPI.class)
                .addComment(gson.toJson(addCommentRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                if (output.status == 1)
                    getView().commentAddedSuccessfully();
                else {
                    getView().displayError(output.message);
                    getView().noDataFound();
                }
            }
        });
    }

    public void deleteCommentPopUp(View view, final CommentListResponse.Datum commentObj){
        PopupMenu popup = new PopupMenu(getView().getActivityG(), view);
        popup.getMenuInflater().inflate(R.menu.menu_delete_comment, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    deleteComment(commentObj);
                }
                return true;
            }
        });
        popup.show();
    }

    public void deleteComment(final CommentListResponse.Datum commentObj) {
        DeleteCommentRequest deleteCommentRequest= new DeleteCommentRequest();
        deleteCommentRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        deleteCommentRequest.token = getView().getLocalData().getAuthToken();
        deleteCommentRequest.id = commentObj.id;

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(PostsAPI.class)
                .deleteComment(gson.toJson(deleteCommentRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                if (output.status == 1)
                    getView().commentDeletedSuccessfully(commentObj);
                else {
                    getView().displayError(output.message);
                    getView().noDataFound();
                }
            }
        });
    }

    @Override
    public void onMoreItemClicked(View view, CommentListResponse.Datum datum) {
        deleteCommentPopUp(view, datum);
    }
}
