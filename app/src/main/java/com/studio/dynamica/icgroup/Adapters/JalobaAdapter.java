package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.JalobaForm;
import com.studio.dynamica.icgroup.ObjectFragments.JalobaInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class JalobaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ConstraintLayout answerLayout;
        LinearLayout jalobaInfoLayout;
        TextView  senderTextView, senderInfoTextView,nameTextView, positionTextView, textTextView, dateTextView,answerTextView;
        ImageView mainPhotoImageView;
        private myHolder(View view){
            super(view);

            answerLayout=(ConstraintLayout) view.findViewById(R.id.answerLayout);
            answerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putBoolean("answerable",true);
                    JalobaInfoFragment infoFragment=new JalobaInfoFragment();
                    infoFragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(R.id.content_frame,infoFragment);

                }
            });
            jalobaInfoLayout=(LinearLayout) view.findViewById(R.id.jalobaInfoLayout);
            senderTextView=(TextView) view.findViewById(R.id.senderTextView);senderInfoTextView=(TextView) view.findViewById(R.id.senderInfoTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            textTextView=(TextView) view.findViewById(R.id.textTextView);
            answerTextView=(TextView) view.findViewById(R.id.answerTextView);
            mainPhotoImageView=(ImageView) view.findViewById(R.id.mainPhotoImageView);

            setFonttype();
        }

        private void setInfo(JalobaForm f){
            positionTextView.setText(f.getPosition());
            nameTextView.setText(f.getName());
            textTextView.setText(f.getText());
            senderInfoTextView.setText(f.getClients());
            dateTextView.setText(f.getDate());
            if(!answer){
                answerLayout.setVisibility(View.GONE);
            }
            else answerLayout.setVisibility(View.VISIBLE);
        }
        private void setFonttype(){
            setTypeFace("it");
            setTypeFace("demibold", dateTextView, senderInfoTextView, nameTextView, positionTextView, answerTextView);
            setTypeFace("light", senderTextView, textTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface((((MainActivity)context).getTypeFace(s)));
            }
        }
    }
    Context context;
    List<JalobaForm> list;
    boolean answer;
    public JalobaAdapter(List<JalobaForm> forms, boolean f){
        list=forms;
        answer=f;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
        holder.jalobaInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putBoolean("answerable",false);
                JalobaInfoFragment infoFragment=new JalobaInfoFragment();
                infoFragment.setArguments(bundle);
                ((MainActivity) context).setFragment(R.id.content_frame,infoFragment);
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.jaloba_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
