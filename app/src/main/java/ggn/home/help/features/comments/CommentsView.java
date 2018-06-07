package ggn.home.help.features.comments;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.CommentListResponse;

public interface CommentsView extends Viewable<CommentsPresenter> {

    void showComments(CommentListResponse output);

    void noDataFound();

    void commentAddedSuccessfully();
}