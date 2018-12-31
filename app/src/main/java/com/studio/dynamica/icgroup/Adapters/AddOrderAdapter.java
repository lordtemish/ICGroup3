package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.AddOrderForm;
import com.studio.dynamica.icgroup.InventoryFragments.MassCreationInfoFragment;
import com.studio.dynamica.icgroup.ObjectFragments.AddOrderInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class AddOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout, statusLayout;
        TextView statusTextView, dateTextView, idLabelTextView, idTextView, placeLabelTextView, placeTextView,
                priorityLabelTextView, statusLabelTextView, priorityTextView, timeLabelTextView, timeLeftTextView, typeLabelTextView, typeTextView, mainStatusTextView;
        ProgressBar ProgressBar;
        Context context;
        ImageView boGalo;
        String status;
        String id="";
        boolean mass=false;
        int d1,d2;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            statusTextView=(TextView) view.findViewById(R.id.statusTextView);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            idLabelTextView=(TextView) view.findViewById(R.id.idLabelTextView);
            idTextView=(TextView) view.findViewById(R.id.idTextView);
            placeLabelTextView=(TextView) view.findViewById(R.id.placeLabelTextView);
            placeTextView=(TextView) view.findViewById(R.id.placeTextView);
            priorityLabelTextView=(TextView) view.findViewById(R.id.priorityLabelTextView);
            statusLabelTextView=(TextView) view.findViewById(R.id.statusLabelTextView);
            priorityTextView=(TextView) view.findViewById(R.id.priorityTextView);
            timeLabelTextView=(TextView) view.findViewById(R.id.timeLabelTextView);
            timeLeftTextView=(TextView) view.findViewById(R.id.timeLeftTextView);
            typeLabelTextView=(TextView) view.findViewById(R.id.typeLabelTextView);
            typeTextView=(TextView) view.findViewById(R.id.typeTextView);
            mainStatusTextView=(TextView) view.findViewById(R.id.mainStatusTextView);
            ProgressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
            boGalo=(ImageView) view.findViewById(R.id.boGalo);
            ((MainActivity)context).setType("demibold", statusTextView, mainStatusTextView, idTextView, placeTextView, priorityTextView, statusTextView, timeLeftTextView, typeTextView);
            ((MainActivity)context).setType("light", dateTextView, idLabelTextView, placeLabelTextView, priorityLabelTextView, statusLabelTextView, timeLabelTextView, typeLabelTextView);
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
            if(mass) {
                MassCreationInfoFragment infoFragment = new MassCreationInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("status", status);
                bundle.putString("id", id);
                bundle.putInt("day1", d1);
                bundle.putInt("day2", d2);
                infoFragment.setArguments(bundle);
                ((MainActivity) context).setFragment(R.id.content_frame, infoFragment);
            }
            else {
                AddOrderInfoFragment infoFragment = new AddOrderInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("status", status);
                bundle.putString("id", id);
                bundle.putInt("day1", d1);
                bundle.putInt("day2", d2);
                infoFragment.setArguments(bundle);
                ((MainActivity) context).setFragment(R.id.content_frame, infoFragment);
            }
        }
        private void setInfo(AddOrderForm form){
            mass=form.isMass();
            d1=form.getDay1();d2=form.getDay2();
            id=form.getId();
            dateTextView.setText(form.getDate());
            idTextView.setText("IC"+form.getId());
            priorityTextView.setText(form.getPriority());
            typeTextView.setText(form.getType());
            timeLeftTextView.setText("Осталось дней: "+form.getDay1()+"/"+form.getDay2());
            int num=0;
            if(form.getDay2()>0){
                num=Integer.parseInt(Math.round(Double.parseDouble(form.getDay1()+"")/Double.parseDouble(form.getDay2()+"")*100)+"");
            }
            ProgressBar.setProgress(num);
            setStatus(form.getStatus());
        }

        private void setStatus(String s){
            status=s;
            switch (s){
                case "PROCESSING":
                    statusLayout.setBackgroundResource(R.drawable.icgreen_page);
                    statusTextView.setText("В процессе");
                    mainStatusTextView.setText("В процессе");
                    boGalo.setBackgroundResource(R.drawable.green_circle);
                    break;
                case "accepted":
                    statusLayout.setBackgroundResource(R.drawable.failedrow_green);
                    statusTextView.setText("Выполненно");
                    mainStatusTextView.setText("Выполненно");
                    boGalo.setBackgroundResource(R.drawable.failedrow_circle);
                    break;
                case "WAITING":
                    statusLayout.setBackgroundResource(R.drawable.inwait_yellowpage);
                    statusTextView.setText("Ожидает");
                    mainStatusTextView.setText("Ожидает");
                    boGalo.setBackgroundResource(R.drawable.yellow_circle);
                    break;
                case "FINISHED":
                    statusLayout.setBackgroundResource(R.drawable.greyrow_page);
                    statusTextView.setText("Завершенно");
                    mainStatusTextView.setText("Завершенно");
                    boGalo.setBackgroundResource(R.drawable.grey_circle);
                    break;
                case "CLOSED":
                    statusLayout.setBackgroundResource(R.drawable.closed_page);
                    statusTextView.setText("Закрыто");
                    mainStatusTextView.setText("Закрыто");
                    boGalo.setBackgroundResource(R.drawable.closed_circle);
                    break;
                case "FAILED":
                    statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                    statusTextView.setText("Провалено");
                    mainStatusTextView.setText("Провалено");
                    boGalo.setBackgroundResource(R.drawable.related_darkgreen_circle);
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
