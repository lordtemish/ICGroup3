package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        TextView time, place,  status, methodlabel, method, period,   timelabel, placelabel;
        Context context;
        myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            time=(TextView) view.findViewById(R.id.time);
            timelabel=(TextView) view.findViewById(R.id.timelabel);
            place=(TextView) view.findViewById(R.id.place);
            placelabel=(TextView) view.findViewById(R.id.placelabel);

            status=(TextView) view.findViewById(R.id.status);
            methodlabel=(TextView) view.findViewById(R.id.methodLabel);
            method=(TextView) view.findViewById(R.id.method);
            period=(TextView) view.findViewById(R.id.period);
            setFonttype();
        }
        private void setFonttype(){
            setTypeFace("demibold", time, place, status, method);
            setTypeFace("light", methodlabel,  placelabel, timelabel);
            setTypeFace("medium", period);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity) context).getTypeFace(s));
            }
        }private void setInfo(TechnoMapForm form){
            setStatus(form.getStat());
            time.setText(form.getTime());

            place.setText(form.getPlace());
            method.setText(form.getMethod());
            period.setText(form.getPeriod());
        }
        private void setStatus(int a){
            Log.d("status TECHNOMAP",""+a);
            switch (a){
                case 0:
                    setFailed();
                    break;
                case 1:
                    setFinished();
                    break;
                case 2:
                    setinProcess();
                    break;
                case 3:
                    setActual();
                    break;
                case 4:
                    setRelate();
                    break;
                case 5:
                    waitForStart();
                    break;
            }
        }
        private void setFailed(){
            wholeLayout.setBackgroundResource(R.drawable.failed_lowdarkgreen);
            time.setTextColor(context.getResources().getColor(R.color.white));
            timelabel.setTextColor(context.getResources().getColor(R.color.white));
            place.setTextColor(context.getResources().getColor(R.color.white));
            placelabel.setTextColor(context.getResources().getColor(R.color.white));

            status.setBackgroundResource(R.drawable.failed_verydarkgreen);
            methodlabel.setTextColor(context.getResources().getColor(R.color.white));
            method.setTextColor(context.getResources().getColor(R.color.white));
            period.setTextColor(context.getResources().getColor(R.color.white));
            status.setText("Провалено");
        }
        private void setRelate(){
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            time.setTextColor(context.getResources().getColor(R.color.black));
            timelabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            place.setTextColor(context.getResources().getColor(R.color.darkgrey));
            placelabel.setTextColor(context.getResources().getColor(R.color.darkgrey));

            status.setBackgroundResource(R.drawable.related_darkgreen_page);
            methodlabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            method.setTextColor(context.getResources().getColor(R.color.black));
            period.setTextColor(context.getResources().getColor(R.color.darkgrey));
            status.setText("На просрочке");
        }
        private void setFinished(){
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            time.setTextColor(context.getResources().getColor(R.color.black));
            timelabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            place.setTextColor(context.getResources().getColor(R.color.darkgrey));
            placelabel.setTextColor(context.getResources().getColor(R.color.darkgrey));

            status.setBackgroundResource(R.drawable.greyrow_page);
            methodlabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            method.setTextColor(context.getResources().getColor(R.color.black));
            period.setTextColor(context.getResources().getColor(R.color.darkgrey));
            status.setText("Выполнено");
        }
        private void waitForStart(){
            wholeLayout.setBackgroundResource(R.drawable.inprocess_green_page);
            time.setTextColor(context.getResources().getColor(R.color.white));
            timelabel.setTextColor(context.getResources().getColor(R.color.white));
            place.setTextColor(context.getResources().getColor(R.color.white));
            placelabel.setTextColor(context.getResources().getColor(R.color.white));

            status.setBackgroundResource(R.drawable.inwait_yellowpage);
            methodlabel.setTextColor(context.getResources().getColor(R.color.white));
            method.setTextColor(context.getResources().getColor(R.color.white));
            period.setTextColor(context.getResources().getColor(R.color.white));
            status.setText("В ожидании");
        }
        public void setinProcess(){
            wholeLayout.setBackgroundResource(R.drawable.inprocess_green_page);
            time.setTextColor(context.getResources().getColor(R.color.white));
            timelabel.setTextColor(context.getResources().getColor(R.color.white));
            place.setTextColor(context.getResources().getColor(R.color.white));
            placelabel.setTextColor(context.getResources().getColor(R.color.white));

            status.setBackgroundResource(R.drawable.inwait_yellowpage);
            methodlabel.setTextColor(context.getResources().getColor(R.color.white));
            method.setTextColor(context.getResources().getColor(R.color.white));
            period.setTextColor(context.getResources().getColor(R.color.white));
            status.setText("В процессе");
        }
        public void setActual(){
            wholeLayout.setBackgroundResource(R.drawable.whiterow_page);
            time.setTextColor(context.getResources().getColor(R.color.black));
            timelabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            place.setTextColor(context.getResources().getColor(R.color.black));
            placelabel.setTextColor(context.getResources().getColor(R.color.darkgrey));

            status.setBackgroundResource(R.drawable.inprocess_green_page);
            methodlabel.setTextColor(context.getResources().getColor(R.color.darkgrey));
            method.setTextColor(context.getResources().getColor(R.color.black));
            period.setTextColor(context.getResources().getColor(R.color.darkgrey));
            status.setText("Актуально");
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder) holder1;
        final TechnoMapForm form=list.get(position);
        holder.setInfo(form);
        if(list.get(position).isResult())
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("status",list.get(position).getStat());
                bundle.putString("id",form.getId());
                bundle.putBoolean("is_result",true);
                TechnoMapInfoFragment infoFragment=new TechnoMapInfoFragment();
                infoFragment.setArguments(bundle);
                ((MainActivity)context).setFragment(R.id.content_frame,infoFragment);
            }
        });
        else{
            holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putInt("status",list.get(position).getStat());
                    bundle.putString("id",form.getId());
                    bundle.putBoolean("is_result",false);
                    TechnoMapInfoFragment infoFragment=new TechnoMapInfoFragment();
                    infoFragment.setArguments(bundle);
                    ((MainActivity)context).setFragment(R.id.content_frame,infoFragment);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
