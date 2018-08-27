package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.InventorizationForm;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryInventorizationInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class InventorizationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ConstraintLayout infoLayout;
        Context context;
        private myHolder(View view){
            super(view);
            infoLayout=(ConstraintLayout)view.findViewById(R.id.infoLayout);
            context=view.getContext();
            infoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClicked();
                }
            });
        }
        private void onClicked(){
            ((MainActivity)context).setFragment(R.id.content_frame,new InventoryInventorizationInfoFragment());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
