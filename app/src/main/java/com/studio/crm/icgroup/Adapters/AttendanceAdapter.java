package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Forms.AttendanceRowForm;
import com.studio.crm.icgroup.Forms.AttendanceRowItemForm;
import com.studio.crm.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        FrameLayout todayBack, todayBackTop, textToday;
        TextView tex, nameTextView, firstTextView, secondTextView, thirdTextView, fourthTextView, fifthTextView, overTextView, name;
        LinearLayout rowLayout, textLayout;
        List<TextView> textViews;
        List<FrameLayout> frameLayouts;
        private myHolder(View view){
            super(view);
            todayBack=(FrameLayout) view.findViewById(R.id.todayBack);
            todayBackTop=(FrameLayout) view.findViewById(R.id.todayBackTop);
            textToday=(FrameLayout) view.findViewById(R.id.textToday);

            frameLayouts=new ArrayList<>();
            textViews=new ArrayList<>();
            textLayout=(LinearLayout) view.findViewById(R.id.textLayout);
            rowLayout=(LinearLayout) view.findViewById(R.id.rowLayout);
            name=(TextView)view.findViewById(R.id.name);
            tex=(TextView)view.findViewById(R.id.tex);
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            overTextView=(TextView)view.findViewById(R.id.overTextView);
            firstTextView=(TextView)view.findViewById(R.id.firstTextView);
            secondTextView=(TextView)view.findViewById(R.id.secondTextView);
            thirdTextView=(TextView)view.findViewById(R.id.thirdTextView);
            fourthTextView=(TextView)view.findViewById(R.id.fourthTextView);
            fifthTextView=(TextView)view.findViewById(R.id.fifthTextView);
            textViews.add(firstTextView);textViews.add(secondTextView);textViews.add(thirdTextView);textViews.add(fourthTextView);textViews.add(fifthTextView);
            frameLayouts.add((FrameLayout) view.findViewById(R.id.firstLayout));frameLayouts.add((FrameLayout) view.findViewById(R.id.secondLayout));frameLayouts.add((FrameLayout) view.findViewById(R.id.thirdLayout));frameLayouts.add((FrameLayout) view.findViewById(R.id.fourthLayout));frameLayouts.add((FrameLayout) view.findViewById(R.id.fifthLayout));


        }
        private void setText(String s){
            rowLayout.setVisibility(View.GONE);
            textLayout.setVisibility(View.VISIBLE);
            tex.setText(s);
        }
        private void setInfo(AttendanceRowForm form){
            rowLayout.setVisibility(View.VISIBLE);
            textLayout.setVisibility(View.GONE);
            List<AttendanceRowItemForm> forms=form.getRowForms();
            nameTextView.setText(form.getName());
            overTextView.setText(form.getN1()+"/"+form.getN2());
            for(int i=0;i<5;i++){
                String s="";
                AttendanceRowItemForm itemForm=forms.get(i);

                if(itemForm.isPlus()){
                    s="+";
                }
                else if(itemForm.isHoliday()){
                    s="holiday";
                }
                else if(itemForm.isAbsent()){
                    s="-";
                }
                else if(itemForm.isIll()){
                    s="ill";
                }
                else if(itemForm.isReplace()){
                    s="repl";
                }
                else if(itemForm.isHalf()){
                    s="half";
                }
                else if(itemForm.isThird()){
                    s="third";
                }

                setStatus(textViews.get(i),s);
            }
            if(today){
                Log.d("afasf",form.getId()+"");
                try {
                    frameLayouts.get(3).setOnClickListener(form.getListener());
                    if(form.isFuck()){
                        textViews.get(3).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                frameLayouts.get(3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        }
        private void setStatus(TextView view,String status){
            switch (status){
                case "+":
                    view.setBackgroundResource(R.drawable.inprocess_green_circle);
                    view.setText("+");
                    break;
                case "-":
                    view.setBackgroundResource(R.drawable.related_darkgreen_circle);
                    view.setText("-");
                    break;
                case "ill":
                    view.setBackgroundResource(R.drawable.inwait_yellow_circle);
                    view.setText("-");
                    break;
                case "repl":
                    view.setBackgroundResource(R.drawable.replace_circle);
                    view.setText("-");
                    break;
                case "half":
                    view.setBackgroundResource(R.drawable.lightgreen_circle);
                    view.setText("+");
                    break;
                case "third":
                    view.setBackgroundResource(R.drawable.grey_circle);
                    view.setText("+");
                    break;
                case "holiday":
                    view.setBackgroundResource(R.drawable.holiday_circle);
                    view.setText("-");
                    break;
                    default:
                        view.setBackgroundResource(R.drawable.green_circle_line);
                        view.setText("");

            }
        }
        private void setToday(boolean today){
            if(today) {
                todayBack.setVisibility(View.VISIBLE);
                todayBackTop.setVisibility(View.VISIBLE);
                textToday.setVisibility(View.VISIBLE);
            }
            else {   todayBack.setVisibility(View.GONE);
                todayBackTop.setVisibility(View.GONE);
                textToday.setVisibility(View.GONE);

            }
        }
    }
    List<AttendanceRowForm> list;
    Context context;
    boolean today=false;
    public AttendanceAdapter(List<AttendanceRowForm> forms){
        list=forms;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.attendance_row,parent,false);
        return new myHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        AttendanceRowForm form=list.get(position);
        holder.setToday(today);
        if(form.isText()){
            holder.setText(form.getName());
        }
        else {
            holder.setInfo(form);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
