package ggn.home.help.features.pickMedia.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import ggn.home.help.R;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {
    private List<String> bitmapList;
    private List<Boolean> selected;
    private Context context;
    private DisplayMetrics displayMetrics;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail, check;
        public RelativeLayout parent;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.image);
            check = (ImageView) view.findViewById(R.id.image2);
            parent = view.findViewById(R.id.relativeLayoutParent);

        }
    }

    public MediaAdapter(List<String> bitmapList, List<Boolean> selected, Context context) {
        this.bitmapList = bitmapList;
        this.context = context;
        this.selected = selected;
        displayMetrics = new DisplayMetrics();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load("file://"+bitmapList.get(position)).override(300,300).centerCrop().into(holder.thumbnail);
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        holder.parent.getLayoutParams().height = (width / 3);
        holder.parent.getLayoutParams().width = (width / 3) - 2;
        if (selected.size() > 0)
            if (selected.get(position).equals(true)) {
                holder.check.setVisibility(View.VISIBLE);
                holder.check.setAlpha(150);
            } else {
                holder.check.setVisibility(View.GONE);
            }

    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }
}

