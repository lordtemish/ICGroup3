package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Forms.MainObjectRowForm;
import com.studio.dynamica.icgroup.ObjectFragments.MainObjectFragment;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;

public class MainObjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MainObjectRowForm> list;
    public class FirstHolder extends RecyclerView.ViewHolder{
        TextView text;
        TextView percentage;
        TextView employees;
        TextView coms;
        TextView category;
        public FirstHolder(View view){
            super(view);
            text=(TextView) view.findViewById(R.id.mainObjectRowTextView);
            percentage=(TextView) view.findViewById(R.id.mainObjectRowPercentageTextView);
            employees=(TextView) view.findViewById(R.id.mainObjectRowEmployeesTextView);
            coms=(TextView) view.findViewById(R.id.mainObjectRowCommsTextView);
            category=(TextView) view.findViewById(R.id.mainObjectRowCategoryTextView);
        }
    }
    public MainObjectAdapter(ArrayList<MainObjectRowForm> a, Context context){
        list=a;
    }
    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
             FirstHolder holder=(FirstHolder)   holder1;
             holder.text.setText(list.get(position).getName());
             holder.percentage.setText(list.get(position).getPercentage());
             holder.employees.setText(list.get(position).getPeople());
             holder.coms.setText(list.get(position).getFalses());
             holder.category.setText(list.get(position).getCategories());
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
