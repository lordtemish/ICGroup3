package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ServicePeriodForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ServicePeriodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public class myHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout textConstratint;
        RecyclerView recyclerView;
        Context context;
        public myHolder(View view){
            super(view);
            context=view.getContext();
            textView=(TextView) view.findViewById(R.id.text);
            textView.setTypeface(((MainActivity)context).getTypeFace("medium"));
            textConstratint=(ConstraintLayout) view.findViewById(R.id.textConstraintLayout);
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager manager=new LinearLayoutManager(view.getContext());
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(manager);
        }
        public void setColor(int a){
            if(a%2==0){
                textConstratint.setBackground(context.getDrawable(R.drawable.lightgreen_corners_page));
                textConstratint.setBackgroundResource((R.drawable.lightgreen_corners_page));
            }
            else{
                textConstratint.setBackground(context.getDrawable(R.drawable.green_corners_page));
                textConstratint.setBackgroundResource((R.drawable.green_corners_page));
            }
        }
    }
    List<ServicePeriodForm> periodForms;
    public ServicePeriodAdapter(List<ServicePeriodForm> forms){
        periodForms=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
            myHolder holder=(myHolder) holder1;
            holder.textView.setText(periodForms.get(position).getName());
            holder.setColor(position);
            TextListAdapter adapter=new TextListAdapter(periodForms.get(position).getList());
            holder.recyclerView.setAdapter(adapter);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_period_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return periodForms.size();
    }
}
