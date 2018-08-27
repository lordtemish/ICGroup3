package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class RemontAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class myHolder extends RecyclerView.ViewHolder{
            RadioButton radioButton;
            private myHolder(View view){
                super(view);
                radioButton=(RadioButton) view.findViewById(R.id.radioButton);

            }
            private void deleteRadio(){
                radioButton.setVisibility(View.GONE);

            }
    }
    List<RemontForms> list;
    boolean a=true;
    Context context;
    public RemontAdapter(List<RemontForms> forms){
        list=forms;
    }
    public RemontAdapter(List<RemontForms> forms, boolean a){
        list=forms;
        this.a=a;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.remont_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        if(!a){
            holder.deleteRadio();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
