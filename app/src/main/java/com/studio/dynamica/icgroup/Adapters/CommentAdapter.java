package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<CommentForm> list;
    public CommentAdapter(List<CommentForm> list){
        this.list=list;
    }
    class MyHolder extends RecyclerView.ViewHolder{
        public MyHolder(View view){
            super(view);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        MyHolder holder=(MyHolder) holder1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
