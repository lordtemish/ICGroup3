package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.R;

import java.util.List;

public class TasktypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        TextView text;
        ConstraintLayout wholeLayout;
        int position;
        private myHolder(View view){
            super(view);
            text=(TextView) view.findViewById(R.id.text);
            wholeLayout=(ConstraintLayout)view.findViewById(R.id.wholeLayout);
            ((MainActivity)context).setType("demibold",text);
        }
        private void setInfo(String s, int position){
            this.position=position;
            text.setText(s);
            if(position==page) {
                wholeLayout.setBackgroundResource(R.drawable.icgreen_page);
                text.setTextColor(context.getResources().getColor(R.color.white));
            }
            else{
                wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
                text.setTextColor(context.getResources().getColor(R.color.darkgrey));
            }
        }
    }
    int page=0;
    Context context;
    List<View.OnClickListener> listeners;
    List<String> list;
    public TasktypeAdapter(List<String> strings){
        list=strings;
    }

    public void setListeners(List<View.OnClickListener> listeners) {
        this.listeners = listeners;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.tasktype_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
            myHolder holder=(myHolder)holder1;
            holder.setInfo(list.get(position),position);
            try{
                holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        page=position;
                        listeners.get(position).onClick(view);
                        notifyDataSetChanged();
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
