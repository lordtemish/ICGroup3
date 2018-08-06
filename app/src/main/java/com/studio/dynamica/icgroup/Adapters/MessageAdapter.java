package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout extraLayout;
        private myHolder(View view){
            super(view);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
        }
    }
    Context context;
    List<MessageForm> list;
    public MessageAdapter(List<MessageForm> forms){
        list=forms;
    }
    public MessageAdapter(MessageForm form){
        list=new ArrayList<>();
        list.add(form);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderq, int position) {
        myHolder holder=(myHolder) holderq;
        if(list.get(position).isFull()){
            holder.extraLayout.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        context=parent.getContext();
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
