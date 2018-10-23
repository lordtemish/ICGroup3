package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.UserRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class UserRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, positionTextView, dateTextView, jalobaLabelTextView, jalobaInfoTextView;
        ImageView mainPhotoImageView;
        ConstraintLayout wholeLayout;
        private myHolder(View view){
            super(view);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            mainPhotoImageView=(ImageView) view.findViewById(R.id.mainPhotoImageView);
            jalobaInfoTextView=(TextView) view.findViewById(R.id.jalobaInfoTextView);
            jalobaLabelTextView=(TextView) view.findViewById(R.id.jalobaLabelTextView);
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
            setFonttype();
        }

        private void setInfo(UserRowForm form){
            nameTextView.setText(form.getName());
            positionTextView.setText(form.getPosition());
            //dateTextView.setText(form.getDate());
            jalobaLabelTextView.setText(form.getTypeLabel());
            jalobaInfoTextView.setText(form.getType());
            if(!form.isFull()){
                wholeLayout.setVisibility(View.GONE);
            }
        }

        private void setFonttype(){
            setTypeFace("it");
            setTypeFace("demibold", dateTextView, nameTextView, jalobaInfoTextView);
            setTypeFace("light", jalobaLabelTextView,positionTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface((((MainActivity)context).getTypeFace(s)));
            }
        }

    }
    Context context;
    List<UserRowForm> list;
    public UserRowAdapter(List<UserRowForm> list){
        this.list=list;
    }
    public UserRowAdapter(UserRowForm form){
        list=new ArrayList<>();
        list.add(form);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.user_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
            myHolder holder=(myHolder) holder1;
            UserRowForm form=list.get(position);
            holder.setInfo(form);
            if(form.getUrl().length()>0){
                ((MainActivity) context).setPhoto(form.getUrl(),holder.mainPhotoImageView);
            }
    }
}
