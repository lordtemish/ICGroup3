package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.InventorizationForm;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryInventorizationInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class InventorizationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ConstraintLayout infoLayout;
        Context context;
        ProgressBar ProgressBar;
        String id="";
        TextView dateTextView, PercentageTextView,nameTextView,  positionTextView;
        private myHolder(View view){
            super(view);
            infoLayout=(ConstraintLayout)view.findViewById(R.id.infoLayout);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);


            ProgressBar=(ProgressBar)view.findViewById(R.id.ProgressBar);
            context=view.getContext();
            infoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClicked();
                }
            });
            ((MainActivity)context).setType("demibold", PercentageTextView, nameTextView);
            ((MainActivity)context).setType("light", dateTextView, positionTextView);
        }
        private void onClicked(){
            Fragment fragment=new InventoryInventorizationInfoFragment();
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            fragment.setArguments(bundle);
            ((MainActivity)context).setFragment(R.id.content_frame,fragment);
        }
        private void  setInfo(InventorizationForm form){
            id=form.getId();
            PercentageTextView.setText(form.getPercentage()+"%");
            ProgressBar.setProgress(form.getPercentage());
            nameTextView.setText(form.getName());
            positionTextView.setText(form.getPosition());
            dateTextView.setText(form.getDate());
        }
    }
    List<InventorizationForm> list;
    Context context;
    public InventorizationAdapter(List<InventorizationForm> forms){
        list=forms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.inventorization_row,parent,false);
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
