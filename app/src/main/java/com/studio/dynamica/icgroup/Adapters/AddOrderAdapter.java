package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.AddOrderForm;
import com.studio.dynamica.icgroup.ObjectFragments.AddOrderInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class AddOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclick();
                }
            });
        }
        private void onclick(){
            ((MainActivity)context).setFragment(R.id.content_frame,new AddOrderInfoFragment());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
