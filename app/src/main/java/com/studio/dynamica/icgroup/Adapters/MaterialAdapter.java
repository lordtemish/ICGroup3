package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
import com.studio.dynamica.icgroup.Forms.MaterialForm;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryMaterialAddFragment;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryMaterialInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout;
        ConstraintLayout passportLayout;
        RecyclerView orderRecycler;
        ImageView openArrow, plusImageVIew;
        boolean open;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            openArrow=(ImageView) view.findViewById(R.id.openArrowImageView);
            wholeLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            orderRecycler=(RecyclerView) view.findViewById(R.id.orderRecyclerView);
            passportLayout=(ConstraintLayout) view.findViewById(R.id.passportLayout);
            plusImageVIew=(ImageView) view.findViewById(R.id.plusImageView);
            passportLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            open=false;

            openArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOpen(!open);
                }
            });

            plusImageVIew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)context).setFragment(R.id.content_frame,new InventoryMaterialAddFragment());
                }
            });
        }
        public void setInfo(MaterialForm form){
            List<OrderForm> orderForms=form.getOrderForms();
            if(orderForms.size()>0){
                ((MainActivity) context).setRecyclerViewOrientation(orderRecycler, LinearLayoutManager.VERTICAL);
                OrderAdapter orderAdapter=new OrderAdapter(orderForms, new InventoryMaterialInfoFragment());
                orderRecycler.setAdapter(orderAdapter);
            }
        }

        private void setOpen(boolean tr){
            int visibility;
            if(tr){
                visibility=View.VISIBLE;
            }
            else{
                visibility=View.GONE;
            }
            setImageDown(!tr);
            wholeLayout.setVisibility(visibility);
            open=tr;
        }
        private void setImageDown(boolean tr){
            if(tr){
                openArrow.setImageResource(R.drawable.ic_arrowdown);
            }
            else openArrow.setImageResource(R.drawable.ic_arrowup);
        }
    }
    List<MaterialForm> list;
    Context context;
    public MaterialAdapter(List<MaterialForm> forms){
        list=forms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.material_row,parent,false);
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
