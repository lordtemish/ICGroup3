package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.CheckListBoxForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class CheckListBoxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CheckListBoxForm> list;
    Context context;
    boolean text;
    int open=0;
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout;
        RecyclerView checkListBoxRowsRecyclerView;
        ImageView galochkaImageView, plusImageView;
        Context context;
        TextView infoTextView;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            checkListBoxRowsRecyclerView=(RecyclerView) view.findViewById(R.id.checkListBoxRowsRecyclerView);
            galochkaImageView=(ImageView) view.findViewById(R.id.galochkaImageView);
            plusImageView=(ImageView) view.findViewById(R.id.plusImageView);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            infoTextView=(TextView) view.findViewById(R.id.infoTextView);
            setFonttype();
        }
        private void setLast(){
            wholeLayout.setBottom(20);
        }
        private void setFonttype(){
            setTypeFace("it");
            setTypeFace("demibold", infoTextView);
            setTypeFace("light");
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface((((MainActivity)context).getTypeFace(s)));
            }
        }
    }
    public CheckListBoxAdapter(List<CheckListBoxForm> forms){
        list=forms;
        text=false;
    }
    public CheckListBoxAdapter(List<CheckListBoxForm> forms,boolean text){
        list=forms;
        this.text=text;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.checklistbox_row,parent,false);
        return new myHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder) holder1;
        ((MainActivity) context).setRecyclerViewOrientation(holder.checkListBoxRowsRecyclerView, LinearLayout.VERTICAL);
        holder.infoTextView.setText(list.get(position).getName());
        CheckListBoxRowAdapter rowAdapter=new CheckListBoxRowAdapter(list.get(position).getList(), text);
        holder.checkListBoxRowsRecyclerView.setAdapter(rowAdapter);
        if(list.get(position).isAccept()){
            holder.galochkaImageView.setBackgroundResource(R.drawable.green_circle);
        }

        if(position==list.size()-1){
            holder.setLast();
        }
        holder.plusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean open=list.get(position).isOpen();
                list.get(position).setOpen(!open);
                notifyItemChanged(position);
            }
        });

        if(list.get(position).isOpen()){
            holder.plusImageView.setImageResource(R.drawable.ic_minus);
            holder.checkListBoxRowsRecyclerView.setVisibility(View.VISIBLE);
        }
        else{
            holder.plusImageView.setImageResource(R.drawable.ic_plus);
            holder.checkListBoxRowsRecyclerView.setVisibility(View.GONE);
        }
    }

    public int getOpen() {
        return open;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
