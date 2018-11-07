package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class PagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class holderT extends RecyclerView.ViewHolder{
        TextView text;
        private holderT(View view){
             super(view);
             text=(TextView) view.findViewById(R.id.text);
            ((MainActivity)context).setType("demibold",text);
        }
        private void setInfo(String s,int position){
            text.setText(s);
            if(page==position){
                text.setTextColor(context.getResources().getColor(R.color.black));
            }
            else
                text.setTextColor(context.getResources().getColor(R.color.darkgrey));
        }
    }
    List<String> list;
    List<View.OnClickListener> listeners;
    int page=1;
Context context;
    public void setListeners(List<View.OnClickListener> listeners) {
        this.listeners = listeners;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PagesAdapter(List<String> strings){
            list=strings;
        }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        holderT holder=(holderT)holder1;
        holder.setInfo(list.get(position),position+1);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=position+1;
                try {
                    listeners.get(position).onClick(view);
                }catch (Exception e){e.printStackTrace();}
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.pages_row, parent, false);
        return new holderT(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
