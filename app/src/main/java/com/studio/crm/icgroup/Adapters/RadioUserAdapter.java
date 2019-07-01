package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.RadioUserForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class RadioUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, positionTextView;
        ImageView galochkaImageView;
        ConstraintLayout wholeLayout;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            galochkaImageView=(ImageView) view.findViewById(R.id.galochkaImageView);
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
            setFonttype();
        }
        private void setFonttype(){
            setTypeFace("demibold",nameTextView);
            setTypeFace("light",positionTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface((((MainActivity)context).getTypeFace(s)));
            }
        }
        private void setInfo(RadioUserForm form){
            nameTextView.setText(form.getName());
            positionTextView.setText(form.getPosition());
            if(!form.isChecked()){
                nameTextView.setTextColor(context.getResources().getColor(R.color.greyy));
                positionTextView.setTextColor(context.getResources().getColor(R.color.greyy));
                galochkaImageView.setBackgroundResource(R.drawable.greybutton_rec);
            }
            else{
                nameTextView.setTextColor(context.getResources().getColor(R.color.black));
                positionTextView.setTextColor(context.getResources().getColor(R.color.black));
                galochkaImageView.setBackgroundResource(R.drawable.greenbutton_rec);
            }
        }
    }
    Context context;
    List<RadioUserForm> list;
    public RadioUserAdapter(List<RadioUserForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.radiouser_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean a=list.get(position).isChecked();
                list.get(position).setChecked(!a);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
