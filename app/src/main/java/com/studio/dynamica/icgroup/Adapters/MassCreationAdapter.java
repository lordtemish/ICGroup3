package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.MassCreationForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class MassCreationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ProgressBar ProgressBar;
        TextView nameTextView, vendorTextView, minusTextView,   numberTextView, plusTextView, dayLeftLabelTextView, num1, num2, unitLabelTextView, unitTextView ;
        ImageView galochkaImageView;
        int index=0;
        private myHolder(View view){
            super(view);
            galochkaImageView=(ImageView)view.findViewById(R.id.galochkaImageView);
            ProgressBar=(ProgressBar)view.findViewById(R.id.ProgressBar);

            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            vendorTextView=(TextView)view.findViewById(R.id.vendorTextView);
            minusTextView=(TextView)view.findViewById(R.id.minusTextView);
            numberTextView=(TextView)view.findViewById(R.id.numberTextView);
            plusTextView=(TextView)view.findViewById(R.id.plusTextView);
            dayLeftLabelTextView=(TextView)view.findViewById(R.id.dayLeftLabelTextView);
            num1=(TextView)view.findViewById(R.id.num1);
            num2=(TextView)view.findViewById(R.id.num2);
            unitLabelTextView=(TextView)view.findViewById(R.id.unitLabelTextView);
            unitTextView=(TextView)view.findViewById(R.id.unitTextView);
            ((MainActivity)context).setType("demibold",nameTextView, minusTextView,numberTextView,plusTextView);
            ((MainActivity)context).setType("light",vendorTextView, dayLeftLabelTextView, num1, num2, unitLabelTextView, unitTextView);
        }
        private void setInfo(MassCreationForm form){
            index=list.indexOf(form);
            if(update){
                minusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int num=list.get(index).getTotal();
                        num--;
                        if(num<0){
                            num=1000;
                        }
                        list.get(index).setTotal(num);
                        notifyItemChanged(index);
                    }
                });
                plusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int num=list.get(index).getTotal();
                        num++;
                        if(num>1000){
                            num=0;
                        }
                        list.get(index).setTotal(num);
                        notifyItemChanged(index);
                    }
                });
            }
            else{
                plusTextView.setVisibility(View.GONE);
                minusTextView.setVisibility(View.GONE);
            }
            if(!form.isSet()){
                galochkaImageView.setVisibility(View.VISIBLE);
            }
            else{
                galochkaImageView.setVisibility(View.GONE);
            }
            nameTextView.setText(form.getName());
            vendorTextView.setText(form.getVendor());
            unitTextView.setText(form.getUnit());
            numberTextView.setText(form.getTotal()+"");
            num1.setText(form.getN1()+"/");
            num2.setText(form.getN2()+"");
            if(form.getN2()>0) {
                int perc = Integer.parseInt("" + Math.round((Double.parseDouble(form.getN1() + "") / Double.parseDouble(form.getN2() + "")) * 100));
                ProgressBar.setProgress(perc);
            }
            else {
                ProgressBar.setProgress(0);
            }
        }
    }
    boolean update=false;
    List<MassCreationForm> list;
    public MassCreationAdapter(List<MassCreationForm> forms){
        list=forms;
    }
    public MassCreationAdapter(List<MassCreationForm> forms, boolean u){
        list=forms;
        update=u;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
    }
    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.masscreation_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
