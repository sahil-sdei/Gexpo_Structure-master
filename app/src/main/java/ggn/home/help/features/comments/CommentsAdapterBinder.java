package ggn.home.help.features.comments;

import android.view.View;

import ggn.home.help.web.response.CommentListResponse;

public interface CommentsAdapterBinder {
    void onMoreItemClicked(View view, CommentListResponse.Datum datum);
}