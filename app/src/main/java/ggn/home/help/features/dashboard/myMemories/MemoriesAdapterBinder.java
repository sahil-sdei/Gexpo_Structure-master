package ggn.home.help.features.dashboard.myMemories;

import ggn.home.help.web.response.PostsResponse;

public interface MemoriesAdapterBinder {
    void onProfileClicked(PostsResponse.Datum memory);

    void onLikeClicked(PostsResponse.Datum memory);

    void showComments(PostsResponse.Datum datum);
}