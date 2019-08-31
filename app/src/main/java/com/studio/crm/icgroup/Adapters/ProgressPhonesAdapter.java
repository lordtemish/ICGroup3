package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.ProgressPhoneForm;
import com.studio.crm.icgroup.ObjectFragments.PassportObjectInfoListOPUFragment;
import com.studio.crm.icgroup.R;

import java.util.List;

public class ProgressPhonesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ProgressPhoneForm> list;
    Context context;
    PassportObjectInfoListOPUFragment fragment;
    int shifts=0;

    public void setShifts(int shifts) {
        this.shifts = shifts;
    }

    private class myHolder extends RecyclerView.ViewHolder{
        RadioButton chechRadio;
        TextView nameTextView;
        TextView positionTextView;
        ProgressBar progressBar;
        TextView PercentageTextView, salaryTextView;
        LinearLayout wholeLayout;
        TextView nameChangeText;
        ImageView circlePhoneImageView;
        TextView PositionText;
        TextView changeText;
        LinearLayout linearLayout;
        private myHolder(View view){
            super(view);
            chechRadio=(RadioButton) view.findViewById(R.id.chechRadio);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            linearLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            salaryTextView=(TextView) view.findViewById(R.id.salaryTextView);
            circlePhoneImageView=(ImageView) view.findViewById(R.id.circlePhoneImageView);
            nameTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            positionTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));
            progressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
            PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
            PercentageTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            nameChangeText=(TextView) view.findViewById(R.id.nameChangeTextView);
            nameChangeText.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            PositionText=(TextView) view.findViewById(R.id.positionChangeTextView);
            PositionText.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));
            changeText=(TextView) view.findViewById(R.id.changeText);
            changeText.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));

        }
        private void setPhoneListener(final String s){
            circlePhoneImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)view.getContext()).callPhone(s);
                }
            });
        }
    }
    public ProgressPhonesAdapter(List<ProgressPhoneForm> forms,Context context){
        this.context=context;
        list=forms;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup group,int viewType){
        View itemView = LayoutInflater.from(group.getContext()).inflate(R.layout.progress_rowphone_form, group, false);
        return new ProgressPhonesAdapter.myHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position){
        myHolder holder=(myHolder) holder1;
        final ProgressPhoneForm form=list.get(position);
        holder.nameTextView.setText(form.getForm().getName());
        holder.positionTextView.setText(form.getForm().getPosition());
        holder.progressBar.setProgress(form.getProgress());
        holder.PercentageTextView.setText(form.getProgress()+"%");
        fragment=new PassportObjectInfoListOPUFragment();
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("id",form.getId());
                bundle.putInt("shifts",shifts);
                bundle.putString("userid",form.getUserid());
                bundle.putString("name",form.getForm().getName());
                bundle.putString("phone",form.getForm().getPhone());
                fragment.setArguments(bundle);
                ((MainActivity) context).setFragment(R.id.content_frame,fragment);
            }
        });
        holder.setPhoneListener(form.getForm().getPhone());
        if(list.get(position).isContract()){
            holder.chechRadio.setChecked(true);
            holder.salaryTextView.setTextColor(context.getResources().getColor(R.color.black));
        }
        else{
            holder.chechRadio.setChecked(false);
            holder.salaryTextView.setTextColor(context.getResources().getColor(R.color.greyy));
        }
        holder.salaryTextView.setText(form.getSalary()+" тг");
        if(list.get(position).isChange()){
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.changeText.setText(form.getText());
                holder.nameChangeText.setText(form.getChangeForm().getName());
                holder.PositionText.setText(form.getChangeForm().getPosition());
        }
        else{
            holder.linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}
