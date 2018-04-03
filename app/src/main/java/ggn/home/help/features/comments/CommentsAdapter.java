package ggn.home.help.features.comments;

import android.content.Context;
import android.text.Html;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemCommentsBinding;
import ggn.home.help.databinding.ItemNotificationBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.notifications.Notifications;

public class CommentsAdapter extends InfiniteAdapterG<ItemCommentsBinding> {

    private List<Comments> dataList;
    private Context context;
    private CommentsAdapterBinder commentsAdapterBinder;

    public CommentsAdapter(List<Comments> dataList, Context context, CommentsAdapterBinder commentsAdapterBinder) {
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
        String htmlText = "<b>"+dataList.get(position).username+"</b>"+" "+dataList.get(position).text;
        baseViewHolder.binding.textViewNotification.setText(Html.fromHtml(htmlText));
        baseViewHolder.binding.setBinder(commentsAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
