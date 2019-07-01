package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Activities.StartActivity;
import com.studio.crm.icgroup.Forms.MainObjectRowForm;
import com.studio.crm.icgroup.Listeners.MainObjectRowOnClickListener;
import com.studio.crm.icgroup.ObjectFragments.MainObjectFragment;
import com.studio.crm.icgroup.R;

import java.util.ArrayList;

public class MainObjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MainObjectRowForm> list;
    Context context;
    String cityid, city;

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public String getCityid() {
            return cityid;
    }

    class FirstHolder extends RecyclerView.ViewHolder{
        TextView text;
        TextView percentage;
        TextView employees;
        TextView coms;
        TextView category, resulLabel;
        ProgressBar progressBar;
        ConstraintLayout topLayout,progressLayout;
        LinearLayout mainObjectRowLinearLayout;
         FirstHolder(View view){
            super(view);
            text=(TextView) view.findViewById(R.id.mainObjectRowTextView);
            text.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            percentage=(TextView) view.findViewById(R.id.mainObjectRowPercentageTextView);
            percentage.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            employees=(TextView) view.findViewById(R.id.mainObjectRowEmployeesTextView);
            employees.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));
            coms=(TextView) view.findViewById(R.id.mainObjectRowCommsTextView);
            coms.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));
            category=(TextView) view.findViewById(R.id.mainObjectRowCategoryTextView);
             resulLabel=(TextView) view.findViewById(R.id.resulLabel);
             ((MainActivity)context).setType("light",resulLabel);
            category.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));
            progressBar=(ProgressBar) view.findViewById(R.id.mainOjectPercentProgress);
            topLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectRowTopLayout);
             progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);
             mainObjectRowLinearLayout=(LinearLayout) view.findViewById(R.id.mainObjectRowLinearLayout);
        }
    }
    private class clientHolder extends RecyclerView.ViewHolder{
         ConstraintLayout wholeLayout;
         TextView text;
         private clientHolder(View view){
             super(view);
             wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
             text=(TextView) view.findViewById(R.id.text);
             text.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
         }
         private void setInfo(MainObjectRowForm form){
             text.setText(form.getName());

         }
    }
    boolean client=false;
    public MainObjectAdapter(ArrayList<MainObjectRowForm> a, Context context){
        list=a;
        this.context=context;
    }

    public void setClient(boolean client) {
        this.client = client;
    }

    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        if (client){
            clientHolder holder=(clientHolder) holder1;
            holder.setInfo(list.get(position));
            MainObjectRowOnClickListener listener=new MainObjectRowOnClickListener(list.get(position),context);
            holder.wholeLayout.setOnClickListener(listener);
        }
        else {
            FirstHolder holder = (FirstHolder) holder1;
            holder.text.setText(list.get(position).getName() + "");
            holder.percentage.setText(list.get(position).getPercentage() + "%");
            holder.progressBar.setProgress(list.get(position).getPercentage());
            holder.employees.setText(list.get(position).getPeople() + "");
            holder.coms.setText(list.get(position).getFalses() + "");
            holder.category.setText(list.get(position).getCategories() + "");
            View.OnClickListener objectDrowningListener = new MainObjectRowOnClickListener(list.get(position), context);
            holder.mainObjectRowLinearLayout.setOnClickListener(objectDrowningListener);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        if(client){
            View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.mainobjectclient_row, groupm, false);
            return new clientHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.mainobject_row, groupm, false);
            return new MainObjectAdapter.FirstHolder(itemView);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
