package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class AcceptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, placeTextView, positionTextView, statusTextView;
        private myHolder(View view){
            super(view);
        }
    }
    List<AcceptForm> list;
    Context context;
    public AcceptAdapter(List<AcceptForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.accept_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
            myHolder holder=(myHolder) holder1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
