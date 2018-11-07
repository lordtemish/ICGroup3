package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.NotificationForm;
import com.studio.dynamica.icgroup.ObjectFragments.ServiceInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        TextView typeTextView, dateTextView, nameTextView, autor, autorNameTextView, autorPositionTextView, acceptTextView, acceptLabelTextView;
        LinearLayout wholeLayout, extraLayout;
        ConstraintLayout upLayout;
        private myHolder(View view){
            super(view);
            typeTextView=(TextView) view.findViewById(R.id.typeTextView);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            autor=(TextView) view.findViewById(R.id.autor);
            autorNameTextView=(TextView) view.findViewById(R.id.autorNameTextView);
            autorPositionTextView=(TextView) view.findViewById(R.id.autorPositionTextView);
            acceptTextView=(TextView) view.findViewById(R.id.acceptTextView);
            acceptLabelTextView=(TextView) view.findViewById(R.id.acceptLabelTextView);
            extraLayout=(LinearLayout)view.findViewById(R.id.extraLayout);
            upLayout=(ConstraintLayout)view.findViewById(R.id.upLayout);

            ((MainActivity)context).setType("light", typeTextView, autor, acceptLabelTextView, autorPositionTextView);
            ((MainActivity)context).setType("demibold", dateTextView, nameTextView, autorNameTextView, acceptTextView);
        }
        private void setInfo(final NotificationForm form){
            nameTextView.setText(form.getName());
            autorNameTextView.setText(form.getAutorname());
            autorPositionTextView.setText(form.getAutorposition());
            dateTextView.setText(form.getDate());
            typeTextView.setText(form.getType());
            if(form.isIs_archive()){
                extraLayout.setVisibility(View.GONE);
            }
            String type=form.getObjectType();
            switch (type){
                case "task":
                    upLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Fragment fragment=new ServiceInfoFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("id",form.getId());
                            bundle.putString("startend"," ");fragment.setArguments(bundle);
                            ((MainActivity)context).setFragment(R.id.content_frame,fragment);
                        }
                    });

                    break;
            }
        }
    }
    public NotificationAdapter(List<NotificationForm> forms){
        list=forms;
    }
    List<NotificationForm> list;
    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.notificiation_row,parent,false);
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
