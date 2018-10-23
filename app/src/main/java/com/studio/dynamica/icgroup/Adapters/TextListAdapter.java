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

public class TextListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public class myHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Context context;
        public myHolder(View view){
            super(view);
            context=view.getContext();
            textView=(TextView) view.findViewById(R.id.text);
            textView.setTypeface(((MainActivity)context).getTypeFace("regular"));
        }
    }
    List<String> list;
    public TextListAdapter(List<String> list){
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.text_list_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
