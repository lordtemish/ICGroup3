package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.EquipmentMObjectForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class EquipmentMObjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, numberLabelTextView, numberTextView, totalLabelTextView, totalMarkTextView, totalUnitTextView;
        ProgressBar ProgressBar;
        LinearLayout progressBarLayout, totalLayout;
        private myHolder(View view){
            super(view);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            numberLabelTextView=(TextView)view.findViewById(R.id.numberLabelTextView);
            numberTextView=(TextView)view.findViewById(R.id.numberTextView);
            totalLabelTextView=(TextView)view.findViewById(R.id.totalLabelTextView);
            totalMarkTextView=(TextView)view.findViewById(R.id.totalMarkTextView);
            totalUnitTextView=(TextView)view.findViewById(R.id.totalUnitTextView);
            ProgressBar=(ProgressBar)view.findViewById(R.id.ProgressBar);
            progressBarLayout=(LinearLayout) view.findViewById(R.id.progressBarLayout);
            totalLayout=(LinearLayout) view.findViewById(R.id.totalLayout);
            ((MainActivity)context).setType("demibold", nameTextView, numberTextView, totalMarkTextView, totalUnitTextView);
            ((MainActivity)context).setType("light", numberLabelTextView, totalLabelTextView);
        }
        private void setInfo(EquipmentMObjectForm form){
            nameTextView.setText(form.getName());
            if (form.isWhole()){
                totalLayout.setVisibility(View.VISIBLE);
                progressBarLayout.setVisibility(View.GONE);
            }
            else {
                totalLayout.setVisibility(View.GONE);
                progressBarLayout.setVisibility(View.VISIBLE);
                numberTextView.setText(form.getQua()+" "+form.getUnit()+"/"+form.getPerc()+"%");
                ProgressBar.setProgress(form.getPerc());
            }
        }
    }

    List<EquipmentMObjectForm> list;
    Context context;
    public EquipmentMObjectAdapter(List<EquipmentMObjectForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.equipment_mobject_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
