package ggn.home.help.features.familyMembers;

import android.content.Context;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemFamilyMembersBinding;
import ggn.home.help.databinding.ItemRequestsBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class FamilyMemberAdapter extends InfiniteAdapterG<ItemFamilyMembersBinding> {

    private List<FamilyMembers> dataList;
    private Context context;

    public FamilyMemberAdapter(List<FamilyMembers> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_family_members;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(final int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        if(dataList.get(position).isSelected){
            baseViewHolder.binding.imageViewSelected.setVisibility(View.VISIBLE);
        }else{
            baseViewHolder.binding.imageViewSelected.setVisibility(View.GONE);
        }

        baseViewHolder.binding.relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.get(position).isSelected = !dataList.get(position).isSelected;
                notifyDataSetChanged();
            }
        });

        baseViewHolder.binding.executePendingBindings();
    }
}
