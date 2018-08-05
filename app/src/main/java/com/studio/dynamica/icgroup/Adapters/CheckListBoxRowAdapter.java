package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class CheckListBoxRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CheckListBoxRowForm> list;
    Context context;
    private class myHolder extends RecyclerView.ViewHolder{
        ConstraintLayout wholeLayout;
        private myHolder(View view){
            super(view);
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
        }
    }
    public CheckListBoxRowAdapter(List<CheckListBoxRowForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.checklistboxrow_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        if(position==list.size()-1){
            holder.wholeLayout.setBackgroundResource(android.R.color.transparent);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
