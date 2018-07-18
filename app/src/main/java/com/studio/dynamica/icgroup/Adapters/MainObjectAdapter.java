package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Activities.StartActivity;
import com.studio.dynamica.icgroup.Forms.MainObjectRowForm;
import com.studio.dynamica.icgroup.Listeners.MainObjectRowOnClickListener;
import com.studio.dynamica.icgroup.ObjectFragments.MainObjectFragment;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;

public class MainObjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MainObjectRowForm> list;
    Context context;
     class FirstHolder extends RecyclerView.ViewHolder{
        TextView text;
        TextView percentage;
        TextView employees;
        TextView coms;
        TextView category;
        ProgressBar progressBar;
        ConstraintLayout topLayout;
         FirstHolder(View view){
            super(view);
            text=(TextView) view.findViewById(R.id.mainObjectRowTextView);
            text.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            percentage=(TextView) view.findViewById(R.id.mainObjectRowPercentageTextView);
            percentage.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            employees=(TextView) view.findViewById(R.id.mainObjectRowEmployeesTextView);
            employees.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            coms=(TextView) view.findViewById(R.id.mainObjectRowCommsTextView);
            coms.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            category=(TextView) view.findViewById(R.id.mainObjectRowCategoryTextView);
            category.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            progressBar=(ProgressBar) view.findViewById(R.id.mainOjectPercentProgress);
            topLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectRowTopLayout);
        }
    }
    public MainObjectAdapter(ArrayList<MainObjectRowForm> a, Context context){
        list=a;
        this.context=context;
    }
    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
             FirstHolder holder=(FirstHolder)   holder1;
             holder.text.setText(list.get(position).getName()+"");
             holder.percentage.setText(list.get(position).getPercentage()+"%");
             holder.progressBar.setProgress(list.get(position).getPercentage());
             holder.employees.setText(list.get(position).getPeople()+"");
             holder.coms.setText(list.get(position).getFalses()+"");
             holder.category.setText(list.get(position).getCategories()+"");
             View.OnClickListener objectDrowningListener=new MainObjectRowOnClickListener(list.get(position),context);
             holder.topLayout.setOnClickListener(objectDrowningListener);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.mainobject_row, groupm, false);
        return new MainObjectAdapter.FirstHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
