package com.example.rachit.aemjdemo.Adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.databinding.RowRecyclerBinding;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public RecyclerAdapter() {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 17;
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
