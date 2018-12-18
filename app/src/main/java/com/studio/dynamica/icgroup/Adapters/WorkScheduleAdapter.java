package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ShiftForm;
import com.studio.dynamica.icgroup.Forms.WorkScheduleForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class WorkScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        TextView days, schInfo, firstSmenaTextView, firstSmenaTimeTextView, secondSmenaTextView, secondSmenaTimeTextView, thirdSmenaTextView,thirdSmenaTimeTextView;
        ConstraintLayout firstSmena, secondSmena, thirdSmena;
        List<TextView> smenasTexts, smenasTimes;
        List<ConstraintLayout> smenasLayouts;
        Context context;
        public myHolder(View view){
            super(view);
            context=view.getContext();
            smenasLayouts=new ArrayList<>();
            smenasTexts=new ArrayList<>();
            smenasTimes=new ArrayList<>();
            firstSmena=(ConstraintLayout) view.findViewById(R.id.firstSmena);
            secondSmena=(ConstraintLayout) view.findViewById(R.id.secondSmena);
            thirdSmena=(ConstraintLayout) view.findViewById(R.id.thirdSmena);
            smenasLayouts.add(firstSmena);smenasLayouts.add(secondSmena);smenasLayouts.add(thirdSmena);
            days=(TextView) view.findViewById(R.id.days);
            days.setTypeface(((MainActivity)context).getTypeFace("bold"));
            schInfo=(TextView) view.findViewById(R.id.schInfo);
            schInfo.setTypeface(((MainActivity)context).getTypeFace("light"));
            firstSmenaTextView=(TextView) view.findViewById(R.id.firstSmenaTextView);
            firstSmenaTextView.setTypeface(((MainActivity)context).getTypeFace("medium"));
            firstSmenaTimeTextView=(TextView) view.findViewById(R.id.firstSmenaTimeTextView);
            firstSmenaTimeTextView.setTypeface(((MainActivity)context).getTypeFace("medium"));
            secondSmenaTextView=(TextView) view.findViewById(R.id.secondSmenaTextView);
            secondSmenaTextView.setTypeface(((MainActivity)context).getTypeFace("medium"));
            secondSmenaTimeTextView=(TextView) view.findViewById(R.id.secondSmenaTimeTextView);
            secondSmenaTimeTextView.setTypeface(((MainActivity)context).getTypeFace("medium"));
            thirdSmenaTextView=(TextView) view.findViewById(R.id.thirdSmenaTextView);
            thirdSmenaTextView.setTypeface(((MainActivity)context).getTypeFace("medium"));
            thirdSmenaTimeTextView=(TextView) view.findViewById(R.id.thirdSmenaTimeTextView);
            thirdSmenaTimeTextView.setTypeface(((MainActivity)context).getTypeFace("medium"));
            smenasTexts.add(firstSmenaTextView);smenasTexts.add(secondSmenaTextView);smenasTexts.add(thirdSmenaTextView);
            smenasTimes.add(firstSmenaTimeTextView);smenasTimes.add(secondSmenaTimeTextView);smenasTimes.add(thirdSmenaTimeTextView);
        }
        private void setInfo(WorkScheduleForm form){
            List<ShiftForm> shiftForms=form.getShiftForms();
            int shifts=form.getShifts();
            days.setText(form.getDayType());
            schInfo.setText(form.getDayInfo());
            for(int i=0;i<shifts;i++){
                smenasLayouts.get(i).setVisibility(View.VISIBLE);
                String ending="-ая";
                if(i==2){
                    ending="-яя";
                }
                if(shiftForms.get(i).isWeekend()){
                    String sub="Суббота";
                    if(shiftForms.get(i).getShift()>6){
                        sub="Воскресенье";
                    }
                    smenasTexts.get(i).setText(sub);
                    smenasTimes.get(i).setText("с " + shiftForms.get(i).getBegin() + " до " + shiftForms.get(i).getEnd());
                }
                else {
                    smenasTexts.get(i).setText(shiftForms.get(i).getShift() + ending + " смена");
                    smenasTimes.get(i).setText("с " + shiftForms.get(i).getBegin() + " до " + shiftForms.get(i).getEnd());
                }
            }
        }

    }
    List<WorkScheduleForm> list;
    public WorkScheduleAdapter(List<WorkScheduleForm> list){
        this.list=list;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.work_schedule_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
