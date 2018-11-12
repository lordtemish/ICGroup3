package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.EmployeesFragment.EmployeeProfileFragment;
import com.studio.dynamica.icgroup.EmployeesFragment.MiniProfileFragment;
import com.studio.dynamica.icgroup.Forms.MainEmployeeForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class MainEmployeesAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        ProgressBar ProgressBar;
        TextView PercentageTextView,rateLabelTextView, rateTextView, nameTextView, resultRL, positionTextView;
        ImageView avatar;
        LinearLayout wholeLayout;
        private myHolder(View view){
            super(view);
            ProgressBar=(android.widget.ProgressBar)view.findViewById(R.id.ProgressBar);
            PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
            rateLabelTextView=(TextView) view.findViewById(R.id.rateLabelTextView);
            rateTextView=(TextView) view.findViewById(R.id.rateTextView);
            resultRL=(TextView) view.findViewById(R.id.resultRL);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            avatar=(ImageView) view.findViewById(R.id.avatar);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);

            ((MainActivity)context).setType("demibold", PercentageTextView, nameTextView, rateTextView);
            ((MainActivity)context).setType("light", resultRL, rateLabelTextView, positionTextView);
        }
        private void setInfo(final MainEmployeeForm form){
            PercentageTextView.setText(form.getResult_rate()+"");
            ProgressBar.setProgress(form.getResult_rate());
            nameTextView.setText(form.getName());
            rateTextView.setText(form.getRate()+"");
            positionTextView.setText(form.getPosition());
            if(form.getUrl().length()>0){
                ((MainActivity)context).setPhoto(form.getUrl(),avatar);
            }
            else{
                avatar.setImageResource(R.drawable.light_grey_circle);
            }
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment;
                    if(adm){
                        fragment=new MiniProfileFragment();
                    }
                    else{
                        fragment=new EmployeeProfileFragment();
                    }
                    Bundle bundle=new Bundle();
                    bundle.putString("id",form.getId());
                    fragment.setArguments(bundle);
                    ((MainActivity)context).setFragment(R.id.content_frame,fragment);
                }
            });
        }
    }
    boolean adm=false;
    Context context;
    List<MainEmployeeForm> list;

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    public  MainEmployeesAdapter(List<MainEmployeeForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.main_employeees_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
        if(position==list.size()-1){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 60, 30, 60);
            holder.wholeLayout.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
