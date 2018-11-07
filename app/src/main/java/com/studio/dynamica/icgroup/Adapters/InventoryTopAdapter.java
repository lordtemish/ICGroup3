package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class InventoryTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout wholeLayout;
        private myHolder(View view){
            super(view);
            textView=(TextView)view.findViewById(R.id.text);
            ((MainActivity)context).setType("demibold",textView);
            wholeLayout=(ConstraintLayout)view.findViewById(R.id.wholeLayout);
        }

    }
    int page=0;

    public void setPage(int page) {
        this.page = page;
    }

    List<View.OnClickListener> listeners;
    public InventoryTopAdapter(List<View.OnClickListener> listeners){
        this.listeners=listeners;
    }
    public void setListeners(List<View.OnClickListener> listeners) {
        this.listeners = listeners;
    }

    String[] strings={"Инвентарь","Инвентаризация","Заявки"};
    Context context;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder)holder1;
        if(position==page){
            holder.textView.setBackgroundResource(R.drawable.greenbutton);
            holder.textView.setTextColor(context.getResources().getColor(R.color.white));
        }
        else{
            holder.textView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.textView.setTextColor(context.getResources().getColor(R.color.darkgrey));
        }
        holder.textView.setText(strings[position]);
        if(listeners.size()>0){
            holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n=page;
                    page=position;
                    notifyItemChanged(n);
                    notifyItemChanged(position);
                    listeners.get(position).onClick(view);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.inventorytop_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }
}
