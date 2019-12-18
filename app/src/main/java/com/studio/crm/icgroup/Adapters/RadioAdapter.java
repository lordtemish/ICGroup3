package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.OneCallBack;
import com.studio.crm.icgroup.Forms.RadioForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        RadioButton radioButton;
        private myHolder(View view){
            super(view);
            radioButton=(RadioButton) view.findViewById(R.id.radioButton);
            ((MainActivity)context).setType("demibold",radioButton);
        }

        private void setInfo(RadioForm form){
            radioButton.setChecked(form.isStatus());
            radioButton.setText("  "+form.getText());
        }
    }
    List<RadioForm> list;
    Context context;
    boolean checkable=false, radioable=false;
    public int check=0;
    OneCallBack callBack;

    public void setCallBack(OneCallBack callBack) {
        this.callBack = callBack;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public void setRadioable(boolean radioable) {
        this.radioable = radioable;
    }

    public RadioAdapter(List<RadioForm> forms){ this.list=forms; }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.radio_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
        if(radioable && check==position){
            holder.radioButton.setChecked(true);
        }
        else{
            holder.radioButton.setChecked(false);
        }
        if(checkable) {
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean a=!list.get(position).isStatus();
                    Log.d("position "+position,a+" bool");
                    list.get(position).setStatus(a);
                    notifyItemChanged(position);
                }
            });
            holder.radioButton.setChecked(list.get(position).isStatus());
        }
        if(radioable){
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int prev=check;
                    check=position;
                    notifyItemChanged(prev);
                    notifyItemChanged(check);
                    callBack.callBackCal();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
