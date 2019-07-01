package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.ServiceForm;
import com.studio.crm.icgroup.ObjectFragments.ServiceInfoFragment;
import com.studio.crm.icgroup.R;

import java.util.List;

public class    ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    public class myHolder extends RecyclerView.ViewHolder{
        LinearLayout infoLayout, wholeLayout;
        TextView serviceTypeTextView, serviceTypeLabelTextView;
        TextView emplTextView, emplLabelTextView, emplPositionTextView;
        TextView statusTextView;
        TextView dayLeftLabelTextView, dayLeftTextView, priorityLabelTextView, priorityTextView;
        ProgressBar progressBar;
        Context context;
        int pos;
        public myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            infoLayout=(LinearLayout) view.findViewById(R.id.infoLayout);
            serviceTypeTextView=(TextView) view.findViewById(R.id.serviceTypeTextView);
            serviceTypeLabelTextView=(TextView) view.findViewById(R.id.serviceTypeLabelTextView);
            emplTextView=(TextView) view.findViewById(R.id.employeeTextView);
            emplLabelTextView=(TextView) view.findViewById(R.id.employeeLabelTextView);
            emplPositionTextView=(TextView) view.findViewById(R.id.employeePositionTextView);

            statusTextView=(TextView) view.findViewById(R.id.statusTextView);

            dayLeftLabelTextView=(TextView) view.findViewById(R.id.dayLeftLabelTextView);
            dayLeftTextView=(TextView) view.findViewById(R.id.dayLeftTextView);
            priorityLabelTextView=(TextView) view.findViewById(R.id.priorityLabelTextView);
            priorityTextView=(TextView) view.findViewById(R.id.priorityTextView);
            progressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);

            setTypeFace();
        }
        private void setTypeFace(){
            serviceTypeLabelTextView.setTypeface(((MainActivity)context).getTypeFace("light"));
            emplLabelTextView.setTypeface(((MainActivity)context).getTypeFace("light"));
            dayLeftLabelTextView.setTypeface(((MainActivity)context).getTypeFace("light"));
            priorityLabelTextView.setTypeface(((MainActivity)context).getTypeFace("light"));

            serviceTypeTextView.setTypeface(((MainActivity)context).getTypeFace("demibold"));
            emplTextView.setTypeface(((MainActivity)context).getTypeFace("demibold"));
            emplPositionTextView.setTypeface(((MainActivity)context).getTypeFace("demibold"));
            statusTextView.setTypeface(((MainActivity)context).getTypeFace("demibold"));
            dayLeftTextView.setTypeface(((MainActivity)context).getTypeFace("demibold"));
            priorityTextView.setTypeface(((MainActivity)context).getTypeFace("demibold"));
        }
        public void setInProcess(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            priorityTextView.setTextColor(context.getResources().getColor(R.color.black));
            dayLeftTextView.setTextColor(context.getResources().getColor(R.color.black));
            statusTextView.setBackgroundResource((R.drawable.inprocess_green_page));
            statusTextView.setText("В процессе");
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            //infoLayout.setBackgroundResource(R.drawable.grey_corners_line);
            setNormProgress();
        }
        public void setReLated(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            wholeLayout.setBackgroundResource((R.drawable.whiterow_page));
            infoLayout.setBackgroundResource(R.drawable.grey_corners_line);
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            priorityTextView.setTextColor(context.getResources().getColor(R.color.black));
            dayLeftTextView.setTextColor(context.getResources().getColor(R.color.black));
            statusTextView.setBackgroundResource((R.drawable.related_darkgreen_page));
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.wronged_progress_row));
            statusTextView.setText("На просрочке");
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            //infoLayout.setBackgroundResource(R.drawable.grey_corners_line);
        }
        public void setFailed(){
            setAllColor(context.getResources().getColor(R.color.white));
            //infoLayout.setBackgroundResource((R.drawable.failed_lowdarkgreen));
            wholeLayout.setBackgroundResource((R.drawable.failed_green_page));
            priorityTextView.setTextColor(context.getResources().getColor(R.color.black));
            statusTextView.setBackgroundResource((R.drawable.failed_verydarkgreen));
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.failedprogress_perc));
            progressBar.setProgress(100);
            statusTextView.setText("Провалено");
        }
        public void setInWait(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            priorityTextView.setTextColor(context.getResources().getColor(R.color.black));
            dayLeftTextView.setTextColor(context.getResources().getColor(R.color.black));
            statusTextView.setBackgroundResource((R.drawable.inwait_yellowpage));
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            //infoLayout.setBackgroundResource(R.drawable.grey_corners_line);
            progressBar.setProgress(0);
            statusTextView.setText("Ожидает подтверждения");
            setNormProgress();
        }
        public void setClosed(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            statusTextView.setBackgroundResource(R.drawable.closed_page);
            statusTextView.setText("Закрыта");
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            progressBar.setProgress(0);
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            //infoLayout.setBackgroundResource(R.drawable.grey_corners_line);
            setNormProgress();
        }
        public void setAccepted(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            progressBar.setProgress(0);
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            statusTextView.setText("Выполнено");
            statusTextView.setBackgroundResource(R.drawable.greyrow_page);
            //infoLayout.setBackgroundResource(R.drawable.grey_corners_line);
            setNormProgress();
        }
        private void setNormProgress(){
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.horizontal_progress_percentage));
        }
        private void setAllColor(int color){
            serviceTypeLabelTextView.setTextColor(color);
            serviceTypeTextView.setTextColor(color);
            emplTextView.setTextColor(color);
            emplLabelTextView.setTextColor(color);
            emplPositionTextView.setTextColor(color);
            dayLeftLabelTextView.setTextColor(color);
            dayLeftTextView.setTextColor(color);
            priorityLabelTextView.setTextColor(color);
            priorityTextView.setTextColor(color);

        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getPos() {
            return pos;
        }
    }
    List<ServiceForm> list;
    public ServicesAdapter(List<ServiceForm> serviceForms){
        list=serviceForms;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_row,parent,false);
        context=parent.getContext();
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
            myHolder holder=(myHolder) holder1;
            ServiceForm form=list.get(position);
            holder.serviceTypeTextView.setText(form.getServiceType());
            holder.emplTextView.setText(form.getEmpl());
            holder.emplPositionTextView.setText(form.getPosition());
            holder.priorityTextView.setText(list.get(position).getPriority());
            int forms=form.getDay2()-form.getDay1();
            if(forms==0){
                forms=form.getDay2();
            }
            holder.dayLeftTextView.setText("Осталось дней: "+form.getDay1()+"/"+form.getDay2());
            int make=1;
        Log.d("Status",form.getStatus()+" "+getItemCount()+" "+position);
            switch (form.getStatus()){
                case "FINISHED":
                    holder.setAccepted();
                    make=0;
                    break;
                case "TIMEOVER":
                    holder.setFailed();
                    make=-1;
                    break;
                case "PROLONGING":
                    holder.setReLated();
                    break;
                case "PROLONGED":
                    holder.setReLated();
                    break;
                case "PROCESSING":
                    holder.setInProcess();
                    break;
                case "WAITING":
                    holder.setInWait();
                    break;
                case "FAILED":
                    holder.setFailed();
                    break;
                case "CLOSED":
                    holder.setClosed();
                    break;
            }
            if (make==1) {
                holder.progressBar.setProgress((int) (Double.parseDouble(forms + "") / Double.parseDouble(form.getDay2() + "") * 100));
            }
            else{
                if(make==0){
                    holder.progressBar.setProgress(0);
                }
                else
                    holder.progressBar.setProgress(100);
            }
            holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServiceInfoFragment fragment=new ServiceInfoFragment();
                    Bundle bundle=new Bundle();bundle.putString("status",list.get(position).getStatus());
                    bundle.putString("id",list.get(position).getId());
                    bundle.putString("startend",list.get(position).getDay1()+" "+list.get(position).getDay2());
                    fragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(R.id.content_frame,fragment);
                }
            });
            holder.setPos(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
