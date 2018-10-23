package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.AVRForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class AVRAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private class myHolder extends RecyclerView.ViewHolder{
        TextView dateTextView,wholeMark,markTextView,workDone,wrapTextView, worksLabelTextView, markLabelTextView, workerTextView, nameTextView, positionTextView;
        RecyclerView tableRecyclerView, acceptRecyclerView;
        RadioButton radioButton;
        LinearLayout extraLayout;
        boolean visible=false;
        private myHolder(View view){
            super(view);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            wholeMark=(TextView) view.findViewById(R.id.wholeMark);
            markTextView=(TextView) view.findViewById(R.id.markTextView);
            workDone=(TextView) view.findViewById(R.id.workDone);
            wrapTextView=(TextView) view.findViewById(R.id.wrapTextView);
            worksLabelTextView=(TextView) view.findViewById(R.id.worksLabelTextView);
            markLabelTextView=(TextView) view.findViewById(R.id.markLabelTextView);
            workerTextView=(TextView) view.findViewById(R.id.workerTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            tableRecyclerView=(RecyclerView) view.findViewById(R.id.tableRecyclerView);
            acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
            radioButton=(RadioButton) view.findViewById(R.id.radioButton);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);

            setFonttypes();

            wrapTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setVisible();
                }
            });
        }
        private void setFonttypes(){
            setTypeFace("demibold", dateTextView, radioButton, markTextView,workerTextView, nameTextView  );
            setTypeFace("light", wholeMark, worksLabelTextView, workDone, wrapTextView, markLabelTextView, positionTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for (int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity) context).getTypeFace(s));
            }
        }
        private void setInfo(AVRForm form){
            dateTextView.setText(form.getDate());
            markTextView.setText(form.getMark()+"");
            radioButton.setText("  "+form.getRadio());
            nameTextView.setText(form.getWorkerName());
            positionTextView.setText(form.getWorkerPosition());

            ((MainActivity)context).setRecyclerViewOrientation(acceptRecyclerView, LinearLayoutManager.HORIZONTAL);
            ((MainActivity)context).setRecyclerViewOrientation(tableRecyclerView, LinearLayoutManager.VERTICAL);
            MessageWorkAdapter workAdapter=new MessageWorkAdapter(form.getMessageWorkForms());
            tableRecyclerView.setAdapter(workAdapter);
            AcceptAdapter acceptAdapter=new AcceptAdapter(form.getAcceptForms());
            acceptRecyclerView.setAdapter(acceptAdapter);
        }
        private void setVisible(){
            visible=!visible;
            if(visible){
                extraLayout.setVisibility(View.VISIBLE);
                wrapTextView.setText("Свернуть");
            }
            else{
                extraLayout.setVisibility(View.GONE);
                wrapTextView.setText("Развернуть");
            }
        }
    }
    List<AVRForm> list;
    public AVRAdapter(List<AVRForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.avr_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
