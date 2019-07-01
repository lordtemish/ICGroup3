package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.GraphicDateForm;
import com.studio.crm.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class GraphicDateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        FrameLayout backLayout, highLayout,mediumLayout ,lowLayout;
        TextView dayTextView;int position=0;
FrameLayout wholeLayout;
        public void setPosition(final int position) {
            this.position = position;
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listeners.size()>0){
                        listeners.get(position).onClick(view);
                    }
                    clearFOrms();
                    list.get(position).setClicked(true);
                    notifyDataSetChanged();
                }
            });
        }

        private myHolder(View view){
            super(view);
            wholeLayout=(FrameLayout)view.findViewById(R.id.wholeLayout);
            backLayout=(FrameLayout)view.findViewById(R.id.backLayout);
            highLayout=(FrameLayout)view.findViewById(R.id.highLayout);
            mediumLayout=(FrameLayout)view.findViewById(R.id.mediumLayout);
            lowLayout=(FrameLayout)view.findViewById(R.id.lowLayout);
            dayTextView=(TextView)view.findViewById(R.id.dayTextView);
            ((MainActivity)context).setType("demibold",dayTextView);
        }
        private void setInfo(final GraphicDateForm form){
            if(form.isToday()){
                backLayout.setVisibility(View.VISIBLE);
                dayTextView.setTextColor(context.getResources().getColor(R.color.black));
                backLayout.setBackgroundResource(R.drawable.greencircletodaycolor);
            }
            if(form.isClicked()){
                backLayout.setVisibility(View.VISIBLE);
                dayTextView.setTextColor(context.getResources().getColor(R.color.black));
                backLayout.setBackgroundResource(R.drawable.greencircle_gdate);
            }
            else {
                if(!form.isToday())
                backLayout.setVisibility(View.GONE);
                dayTextView.setTextColor(context.getResources().getColor(R.color.greyy));
            }

            int height=((MainActivity)context).getPixels(form.getHigh()*2),backH=((MainActivity)context).getPixels(form.getHigh()*2+80);
            ViewGroup.LayoutParams params=backLayout.getLayoutParams();
            params.height=backH;
            backLayout.setLayoutParams(params);
            ViewGroup.LayoutParams params2= highLayout.getLayoutParams();
            params2.height=height;
            highLayout.setLayoutParams(params2);
            int height1=((MainActivity)context).getPixels(form.getMedium()*2);
            ViewGroup.LayoutParams params11= mediumLayout.getLayoutParams();
            params11.height=height1;
            mediumLayout.setLayoutParams(params11);
            int height2=((MainActivity)context).getPixels(form.getMedium()*2);
            ViewGroup.LayoutParams params21= mediumLayout.getLayoutParams();
            params21.height=height2;
            mediumLayout.setLayoutParams(params21);
            dayTextView.setText(form.getDay()+"\n"+form.getWeek());
        }
    }
    Context context;
    List<GraphicDateForm> list;
    private  List<View.OnClickListener> listeners=new ArrayList<>();

    public void setListeners(List<View.OnClickListener> listeners) {
        this.listeners = listeners;
    }

    private void clearFOrms(){
        for (GraphicDateForm form:list){
            form.setClicked(false);
        }
    }
    public GraphicDateAdapter(List<GraphicDateForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.grapicdate_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
            myHolder holder=(myHolder)holder1;
            holder.setInfo(list.get(position));
            holder.setPosition(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
