package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.TechnoMapForm;
import com.studio.dynamica.icgroup.ObjectFragments.TechnoMapInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class TechnoMapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TechnoMapForm> list;
    Context context;
    public TechnoMapAdapter(List<TechnoMapForm> forms){
        list=forms;
    }
    class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout;
        TextView time, place, category, status, methodlabel, method, period;
        Context context;
        myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            time=(TextView) view.findViewById(R.id.time);
            place=(TextView) view.findViewById(R.id.place);
            category=(TextView) view.findViewById(R.id.category);
            status=(TextView) view.findViewById(R.id.status);
            methodlabel=(TextView) view.findViewById(R.id.methodLabel);
            method=(TextView) view.findViewById(R.id.method);
            period=(TextView) view.findViewById(R.id.period);
        }
        private void setStatus(int a){
            switch (a){
                case 0:
                    setFinished();
                    break;
                case 1:
                    setFailed();
                    break;
                case 2:
                    setinProcess();
                    break;
                case 3:
                    setActual();
                    break;
            }
        }
        private void setFailed(){
            wholeLayout.setBackgroundResource(R.drawable.failed_lowdarkgreen);
            time.setTextColor(context.getResources().getColor(R.color.white));
            place.setTextColor(context.getResources().getColor(R.color.white));
            category.setTextColor(context.getResources().getColor(R.color.white));
            status.setBackgroundResource(R.drawable.failed_verydarkgreen);
            methodlabel.setTextColor(context.getResources().getColor(R.color.white));
            method.setTextColor(context.getResources().getColor(R.color.white));
            period.setTextColor(context.getResources().getColor(R.color.white));
        }
        private void setFinished(){
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            time.setTextColor(context.getResources().getColor(R.color.black));
            place.setTextColor(context.getResources().getColor(R.color.darkgrey));
            category.setTextColor(context.getResources().getColor(R.color.black));
            status.setBackgroundResource(R.drawable.greyrow_page);
            methodlabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            method.setTextColor(context.getResources().getColor(R.color.darkgrey));
            period.setTextColor(context.getResources().getColor(R.color.darkgrey));
        }
        public void setinProcess(){
            wholeLayout.setBackgroundResource(R.drawable.inprocess_green_page);
            time.setTextColor(context.getResources().getColor(R.color.white));
            place.setTextColor(context.getResources().getColor(R.color.white));
            category.setTextColor(context.getResources().getColor(R.color.white));
            status.setBackgroundResource(R.drawable.inwait_yellowpage);
            methodlabel.setTextColor(context.getResources().getColor(R.color.white));
            method.setTextColor(context.getResources().getColor(R.color.white));
            period.setTextColor(context.getResources().getColor(R.color.white));
        }
        public void setActual(){
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            time.setTextColor(context.getResources().getColor(R.color.black));
            place.setTextColor(context.getResources().getColor(R.color.black));
            category.setTextColor(context.getResources().getColor(R.color.black));
            status.setBackgroundResource(R.drawable.inprocess_green_page);
            methodlabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            method.setTextColor(context.getResources().getColor(R.color.darkgrey));
            period.setTextColor(context.getResources().getColor(R.color.darkgrey));
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.techno_map_row,parent,false);
        context=parent.getContext();
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        TechnoMapForm form=list.get(position);
        holder.setStatus(form.getStat());
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).setFragment(R.id.content_frame,new TechnoMapInfoFragment());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
