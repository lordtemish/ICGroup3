package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.MiniObjectTaskForm;
import com.studio.dynamica.icgroup.ObjectFragments.MainObjectMainFrament;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class MiniObjectTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        RecyclerView rateRecyclerView;
        TextView perfomanceTextView,PercentageTextView;
        ProgressBar ProgressBar;
        LinearLayout perfomanceLayout;
        private myHolder(View view){
            super(view);
            rateRecyclerView=(RecyclerView)view.findViewById(R.id.rateRecyclerView);
            perfomanceTextView=(TextView)view.findViewById(R.id.perfomanceTextView);
            PercentageTextView=(TextView)view.findViewById(R.id.PercentageTextView);
            ProgressBar=(ProgressBar)view.findViewById(R.id.ProgressBar);
            perfomanceLayout=(LinearLayout) view.findViewById(R.id.perfomanceLayout);
            ((MainActivity)context).setRecyclerViewOrientation(rateRecyclerView, LinearLayoutManager.VERTICAL);
            ((MainActivity)context).setType("demibold", perfomanceTextView, PercentageTextView);
        }
        private void setInfo(final MiniObjectTaskForm form){
            perfomanceTextView.setText(form.getName());
            rateRecyclerView.setAdapter(new RateStarsAdapter(form.getRate()));
            PercentageTextView.setText(form.getPerfomance_rate()+"%");
            ProgressBar.setProgress(form.getPerfomance_rate());
            perfomanceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainObjectMainFrament frament=new MainObjectMainFrament();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",form.getId());
                    bundle.putString("name",form.getName());
                    bundle.putInt("location",1);
                    bundle.putString("city","1");
                    frament.setArguments(bundle);
                    ((MainActivity)context).setFragment(R.id.content_frame,frament);
                }
            });

        }
    }
    List<MiniObjectTaskForm> list;
    Context context;
    public MiniObjectTaskAdapter(List<MiniObjectTaskForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.miniobjecttask_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

