package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.R;

import java.util.List;

public class CityRadioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        RadioButton radioButton;
        int position=0;
        private myHolder(View view){
            super(view);
            radioButton=(RadioButton)view.findViewById(R.id.radioButton);
            ((MainActivity)context).setType("demibold",radioButton);
        }
        private void setInfo(String info, int position){
            radioButton.setText("  "+info);
            this.position=position;
            if(position==page){
                radioButton.setChecked(true);
            }
            else{
                radioButton.setChecked(false);
            }
        }
    }

    List<View.OnClickListener> listeners;

    public void setListeners(List<View.OnClickListener> listeners) {
        this.listeners = listeners;
    }
Context context;
    List<String> list;
    int page=0;
    public CityRadioAdapter(List<String> form){
        list=form;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position),position);
        try {
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page = position;
                    listeners.get(position).onClick(view);
                    notifyDataSetChanged();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.radio_row, parent, false);
        return new myHolder(view);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
