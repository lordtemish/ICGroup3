package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.AddOrderForm;
import com.studio.dynamica.icgroup.ObjectFragments.AddOrderInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class AddOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout, statusLayout;
        TextView statusTextView;
        Context context;
        String status;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            statusTextView=(TextView) view.findViewById(R.id.statusTextView);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            statusLayout=(LinearLayout) view.findViewById(R.id.statusLayout);
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclick();
                }
            });
        }
        private void onclick(){
            AddOrderInfoFragment infoFragment=new AddOrderInfoFragment();
            Bundle bundle=new Bundle();
            bundle.putString("status",status);
            infoFragment.setArguments(bundle);
            ((MainActivity)context).setFragment(R.id.content_frame,infoFragment);
        }
        private void setInfo(AddOrderForm form){
            setStatus(form.getStatus());
        }

        private void setStatus(String s){
            status=s;
            switch (s){
                case "actual":

                    break;
                case "accepted":
                    statusLayout.setBackgroundResource(R.drawable.failedrow_green);
                    statusTextView.setText("Выполненно");
                    break;
                case "waiting":
                    statusTextView.setText("Ожидает");
                    break;
                case "finished":
                    statusLayout.setBackgroundResource(R.drawable.closed_page);
                    statusTextView.setText("Завершенно");
                    break;
                case "timeout":
                    statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                    statusTextView.setText("Время вышло");
                    break;
                case "cancel":
                    statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                    statusTextView.setText("Отказано");
                    break;
            }
        }
    }

    List<AddOrderForm> list;
    Context context;
    public AddOrderAdapter(List<AddOrderForm> forms){
        list=forms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.addorder_row,parent,false);
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
