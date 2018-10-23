package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.ExtraFragments.RadioButtonFragment;
import com.studio.dynamica.icgroup.Forms.InventorizationPassingSetForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class InventorizationPassingSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        Context context;
        LinearLayout extraLayout;
        RadioButtonFragment radioButtonFragment;
        ImageView statusImageView;
        ConstraintLayout headerLayout;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            statusImageView=(ImageView) view.findViewById(R.id.statusImageView);
            headerLayout=(ConstraintLayout) view.findViewById(R.id.headerLayout);

            radioButtonFragment=new RadioButtonFragment();
            Bundle bundle=new Bundle();bundle.putInt("checked",0);
            radioButtonFragment.setArguments(bundle);
        }
        private void setInfo(InventorizationPassingSetForm form){
            if(!form.isStatus()){
                extraLayout.setVisibility(View.VISIBLE);
                statusImageView.setBackgroundResource(R.drawable.yellow_circle);
            }
            else {
                statusImageView.setBackgroundResource(R.drawable.green_circle);
                extraLayout.setVisibility(View.GONE);
            }
        }
    }
    List<InventorizationPassingSetForm> list;
    Context context;
    public InventorizationPassingSetAdapter(List<InventorizationPassingSetForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.inventorization_passingset_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
        holder.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position).setStatus(!list.get(position).isStatus());
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
