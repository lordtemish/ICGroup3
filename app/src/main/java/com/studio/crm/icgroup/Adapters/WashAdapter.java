package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.WashForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class WashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<WashForm> list;
    Context context;
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, idTextView, numberTextView;
        public myHolder(View v){
            super(v);
            numberTextView=(TextView) v.findViewById(R.id.numberTextView);
            nameTextView=(TextView) v.findViewById(R.id.nameTextView);
            idTextView=(TextView) v.findViewById(R.id.idTextView);

            numberTextView.setTypeface(((MainActivity)v.getContext()).getTypeFace("demibold"));
            idTextView.setTypeface(((MainActivity)v.getContext()).getTypeFace("demibold"));
            nameTextView.setTypeface(((MainActivity)v.getContext()).getTypeFace("light"));
        }
        private void setInfo(WashForm washForm){
            numberTextView.setText(washForm.getNumbers()+" шт");idTextView.setText(washForm.getId());nameTextView.setText(washForm.getName());
        }
    }
    public WashAdapter(List<WashForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wash_row,parent,false);
        context=parent.getContext();
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

