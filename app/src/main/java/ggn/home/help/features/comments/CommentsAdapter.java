package ggn.home.help.features.comments;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemCommentsBinding;
import ggn.home.help.databinding.ItemNotificationBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.notifications.Notifications;
import ggn.home.help.features.userProfile.UserProfileActivity;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.CommentListResponse;

public class CommentsAdapter extends InfiniteAdapterG<ItemCommentsBinding> {

    private List<CommentListResponse.Datum> dataList;
    private Context context;
    private CommentsAdapterBinder commentsAdapterBinder;

    public CommentsAdapter(List<CommentListResponse.Datum> dataList, Context context, CommentsAdapterBinder commentsAdapterBinder) {
        this.commentsAdapterBinder = commentsAdapterBinder;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_comments;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(final int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));

        if (!TextUtils.isEmpty(dataList.get(position).user.profileImage))
            ImageLoader.loadUserImageSmall(baseViewHolder.binding.imageViewProfilePic, dataList.get(position).user.profileImage);
        else
            baseViewHolder.binding.imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);

        baseViewHolder.binding.imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileActivity.start(context, dataList.get(position).user.id);
            }
        });

        baseViewHolder.binding.imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               commentsAdapterBinder.onMoreItemClicked(view, dataList.get(position));
            }
        });


        String htmlText = "<b>" + dataList.get(position).user.name + "</b>" + "  " + dataList.get(position).comment;
        baseViewHolder.binding.textViewNotification.setText(Html.fromHtml(htmlText));

        if (!TextUtils.isEmpty(dataList.get(position).date))
            baseViewHolder.binding.textViewTimeAgo.setText(UtillsG.getTimeAgo(dataList.get(position).date));

        baseViewHolder.binding.setBinder(commentsAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
