package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ServiceForm;
import com.studio.dynamica.icgroup.ObjectFragments.ServiceInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    public class myHolder extends RecyclerView.ViewHolder{
        LinearLayout infoLayout, wholeLayout;
        TextView serviceTypeTextView, serviceTypeLabelTextView;
        TextView emplTextView, emplLabelTextView, emplPositionTextView;
        TextView statusLabelTextView, statusTextView;
        ImageView statusImageView;
        TextView dayLeftLabelTextView, dayLeftTextView, priorityLabelTextView, priorityTextView;
        ProgressBar progressBar;
        Context context;
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
            statusLabelTextView=(TextView) view.findViewById(R.id.statusLabelTextView);
            statusTextView=(TextView) view.findViewById(R.id.statusTextView);
            statusImageView=(ImageView) view.findViewById(R.id.statusImageView);
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
            statusLabelTextView.setTypeface(((MainActivity)context).getTypeFace("light"));
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
        }
        public void setReLated(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            priorityTextView.setTextColor(context.getResources().getColor(R.color.black));
            dayLeftTextView.setTextColor(context.getResources().getColor(R.color.black));
            statusTextView.setBackgroundResource((R.drawable.related_darkgreen_page));
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.wronged_progress_row));
            statusTextView.setText("На просрочке");
        }
        public void setFailed(){
            setAllColor(context.getResources().getColor(R.color.white));
            infoLayout.setBackgroundResource((R.drawable.failed_lowdarkgreen));
            wholeLayout.setBackgroundResource((R.drawable.failed_green_page));
            priorityTextView.setTextColor(context.getResources().getColor(R.color.black));
            statusTextView.setBackgroundResource((R.drawable.failed_verydarkgreen));
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.failedprogress_perc));
            progressBar.setProgress(100);
            statusImageView.setImageResource(R.drawable.ic_tiffanyarrow_right);
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
            progressBar.setProgress(0);
            statusTextView.setText("Ожидает подтверждения");
        }
        public void setClosed(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            statusTextView.setBackgroundResource(R.drawable.closed_page);
            statusTextView.setText("Закрыта");
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            progressBar.setProgress(0);
        }
        public void setAccepted(){
            setAllColor(context.getResources().getColor(R.color.greyy));
            serviceTypeTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplTextView.setTextColor(context.getResources().getColor(R.color.black));
            emplPositionTextView.setTextColor(context.getResources().getColor(R.color.black));
            progressBar.setProgress(0);

        }
        private void setAllColor(int color){
            serviceTypeLabelTextView.setTextColor(color);
            serviceTypeTextView.setTextColor(color);
            emplTextView.setTextColor(color);
            emplLabelTextView.setTextColor(color);
            emplPositionTextView.setTextColor(color);
            statusLabelTextView.setTextColor(color);
            dayLeftLabelTextView.setTextColor(color);
            dayLeftTextView.setTextColor(color);
            priorityLabelTextView.setTextColor(color);
            priorityTextView.setTextColor(color);

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
            holder.progressBar.setProgress((int)(Double.parseDouble(forms+"")/Double.parseDouble(form.getDay2()+"")*100));
            holder.dayLeftTextView.setText("Осталось дней: "+form.getDay1()+"/"+form.getDay2());
            switch (form.getStatus()){
                case "FINISHED":
                    holder.setAccepted();
                    break;
                case "TIMEOVER":
                    holder.setFailed();
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
                case "CLOSED":
                    holder.setClosed();
                    break;
            }
            holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServiceInfoFragment fragment=new ServiceInfoFragment();
                    Bundle bundle=new Bundle();bundle.putString("status",list.get(position).getStatus());
                    fragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(R.id.content_frame,fragment);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
