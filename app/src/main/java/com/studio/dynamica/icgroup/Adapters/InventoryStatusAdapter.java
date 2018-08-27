package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.InventoryStatusFactoryForm;
import com.studio.dynamica.icgroup.Forms.InventoryStatusForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView textView;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
            textView=(TextView) view.findViewById(R.id.text);
        }
        private void setInfo(InventoryStatusFactoryForm factoryForm){
            ((MainActivity) context).setRecyclerViewOrientation(recyclerView, LinearLayout.VERTICAL);
            InventoryAdapter inventoryAdapter=new InventoryAdapter(factoryForm.getStatusForms());
            recyclerView.setAdapter(inventoryAdapter);
            textView.setText(factoryForm.getText());
        }
    }

    List<InventoryStatusFactoryForm> list;
    Context context;
    public InventoryStatusAdapter(List<InventoryStatusFactoryForm> form){
        list=form;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.inventory_status_row, parent, false);
            return new myHolder(view);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
