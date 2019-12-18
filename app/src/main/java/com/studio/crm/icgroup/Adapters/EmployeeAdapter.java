package com.studio.crm.icgroup.Adapters;

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

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.EmployeesFragment.FactoryDepFragment;
import com.studio.crm.icgroup.Forms.EmployeeForm;
import com.studio.crm.icgroup.Forms.MainEmployeeForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter {
    private class myHolder extends RecyclerView.ViewHolder{
        android.widget.ProgressBar ProgressBar;
        TextView PercentageTextView, nameTextView, resultRL, employeeLabelTextView, employeeTextView;
        LinearLayout wholeLayout;
        private myHolder(View view){
            super(view);
            ProgressBar=(android.widget.ProgressBar)view.findViewById(R.id.ProgressBar);
            PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
            employeeLabelTextView=(TextView) view.findViewById(R.id.employeeLabelTextView);
            employeeTextView=(TextView) view.findViewById(R.id.employeeTextView);
            resultRL=(TextView) view.findViewById(R.id.resultRL);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);

            ((MainActivity)context).setType("demibold", PercentageTextView, nameTextView, employeeTextView);
            ((MainActivity)context).setType("light", resultRL, employeeLabelTextView);
        }
        private void setInfo(final EmployeeForm form){
            nameTextView.setText(form.getName());
            employeeTextView.setText(form.getEmpls()+"");
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new FactoryDepFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",form.getId());
                    bundle.putString("kind",form.getKind());
                    bundle.putString("name",form.getName());
                    bundle.putBoolean("adm", form.isAdm());
                    fragment.setArguments(bundle);
                    ((MainActivity)context).setFragment(R.id.content_frame,fragment);
                }
            });
        }
    }
    Context context;
    List<EmployeeForm> list;
    public  EmployeeAdapter(List<EmployeeForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.employees_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
        if(position==list.size()-1){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 40, 30, 60);
            holder.wholeLayout.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
