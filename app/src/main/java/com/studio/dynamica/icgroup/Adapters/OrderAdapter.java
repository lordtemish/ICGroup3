package com.studio.dynamica.icgroup.Adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.ObjectFragments.AddOrderInfoFragment;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryEquipmentInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout,statusLayout ;
        TextView dateTextView, numberLabel,numberTextView, depLabel, depText, priorityTextView, priorityLabelTextView, statusLabelTextView, statusTextView, dayLeftLabelTextView, dayLeftTextView, inprocessText;
        ProgressBar ProgressBar;
        ImageView galochkaImageView;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            statusLayout=(LinearLayout) view.findViewById(R.id.statusLayout);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            numberLabel=(TextView) view.findViewById(R.id.numberLabel);
            numberTextView=(TextView) view.findViewById(R.id.numberTextView);
            depLabel=(TextView) view.findViewById(R.id.depLabel);
            depText=(TextView) view.findViewById(R.id.depText);
            priorityTextView=(TextView) view.findViewById(R.id.priorityTextView);
            priorityLabelTextView=(TextView) view.findViewById(R.id.priorityLabelTextView);
            statusLabelTextView=(TextView) view.findViewById(R.id.statusLabelTextView);
            statusTextView=(TextView) view.findViewById(R.id.statusTextView);
            dayLeftLabelTextView=(TextView) view.findViewById(R.id.dayLeftLabelTextView);
            dayLeftTextView=(TextView) view.findViewById(R.id.dayLeftTextView);
            inprocessText=(TextView) view.findViewById(R.id.inprocessText);
            galochkaImageView=(ImageView) view.findViewById(R.id.galochkaImageView);
            ProgressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
            ((MainActivity)context).setType("demibold",dateTextView, numberTextView, depText, statusTextView, priorityTextView, dayLeftTextView, inprocessText);
            ((MainActivity)context).setType("light",numberLabel, depLabel, statusLabelTextView, priorityLabelTextView, dayLeftLabelTextView);
        }
        private void setWholeLayoutListener(){

        }
        private void setInfo(final OrderForm form){
            dateTextView.setText(form.getDate());
            numberTextView.setText(form.getNumber());
            priorityTextView.setText(form.getPriority());
            setStatus(form.getStatus());
            dayLeftTextView.setText("Осталось дней: "+form.getNum1()+"/"+form.getNum2());
            int num=0;
            if(form.getNum2()>0){
                num=Integer.parseInt(Math.round(100*(form.getD1()/form.getD2()))+"");
            }
            ProgressBar.setProgress(num);
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new AddOrderInfoFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",form.getId());
                    bundle.putInt("day1",form.getNum1());
                    bundle.putInt("day2",form.getNum2());
                    fragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(R.id.content_frame,fragment);
                }
            });
        }
        private void setStatus(String s){
            switch (s){
                case "WAITING":
                    statusLayout.setBackgroundResource(R.drawable.inwait_yellowpage);
                    statusTextView.setText("Ожидает подтверждения");
                    inprocessText.setText("Ожидает подтверждения");
                    galochkaImageView.setBackgroundResource(R.drawable.yellow_circle);
                    break;
                case "PROCESSING":
                    statusLayout.setBackgroundResource(R.drawable.icgreen_page);
                    statusTextView.setText("В процессе");
                    inprocessText.setText("В процессе");
                    galochkaImageView.setBackgroundResource(R.drawable.green_circle);
                    break;
                case "FINISHED":
                    statusLayout.setBackgroundResource(R.drawable.greyrow_page);
                    statusTextView.setText("Завершено");
                    inprocessText.setText("Завершено");
                    galochkaImageView.setBackgroundResource(R.drawable.grey_circle);
                    break;
                default:
                    statusLayout.setBackgroundResource(R.drawable.failedrow_green);
                    statusTextView.setText("Провалено");
                    inprocessText.setText("Провалено");
                    galochkaImageView.setBackgroundResource(R.drawable.failedrow_circle);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.order_row,parent,false);
        return new myHolder(view);
    }
    Context context;
    List<OrderForm> list;
    Fragment fragment;
    public OrderAdapter(List<OrderForm> forms, Fragment fragment){
        list=forms;
        this.fragment=fragment;
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
