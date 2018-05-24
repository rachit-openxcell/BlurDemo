package com.example.rachit.aemjdemo.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rachit.aemjdemo.Model.ServerData;
import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.Utility.GlideApp;
import com.example.rachit.aemjdemo.databinding.RowRecyclerBinding;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    ServerData serverData;
    int pos;

    public RecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public void addAll(ServerData serverData, int pos){
        this.serverData = serverData;
        this.pos = pos;
        notifyDataSetChanged();
//        Log.e(TAG, "getItemCount: "+ serverData.getData().get(pos).getContent().size()  +" ***********************" );
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        RowRecyclerBinding rowRecyclerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_recycler, parent, false);
        viewHolder = new BlogViewHolder(rowRecyclerBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        BlogViewHolder holder = (BlogViewHolder) holder1;
//        Log.e(TAG, "onBindViewHolder: position " + position +" ***********************"  );
        GlideApp.with(mContext)
                .load(serverData.getData().get(pos).getContent().get(position).getContentImage())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.rowRecyclerBinding.contentImage);

        holder.rowRecyclerBinding.contentTitle.setText(serverData.getData().get(pos).getContent().get(position).getContentTitle());
        holder.rowRecyclerBinding.description.setText(serverData.getData().get(pos).getContent().get(position).getDescription());
    }

    private static final String TAG = "RecyclerAdapter";
    @Override
    public int getItemCount() {
        return serverData.getData().get(pos).getContent().size();
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final RowRecyclerBinding rowRecyclerBinding;

        public BlogViewHolder(RowRecyclerBinding rowRecyclerBinding) {
            super(rowRecyclerBinding.getRoot());
            mView = rowRecyclerBinding.getRoot();
            this.rowRecyclerBinding = rowRecyclerBinding;
        }
    }
}
