package ggn.home.help.features.notifications;

import android.content.Context;
import android.text.Html;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemFamilyMembersBinding;
import ggn.home.help.databinding.ItemNotificationBinding;
import ggn.home.help.features.familyMembers.FamilyMembers;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class NotificationAdapter extends InfiniteAdapterG<ItemNotificationBinding> {

    private List<Notifications> dataList;
    private Context context;
    private NotificationAdapterBinder notificationAdapterBinder;

    public NotificationAdapter(List<Notifications> dataList, Context context, NotificationAdapterBinder notificationAdapterBinder) {
        this.notificationAdapterBinder = notificationAdapterBinder;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_notification;
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
        baseViewHolder.binding.setBinder(notificationAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }
}
