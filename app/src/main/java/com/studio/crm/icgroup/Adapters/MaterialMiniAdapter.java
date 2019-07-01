package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.MaterialMiniForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class MaterialMiniAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView wholeLabelTextView, nameTextView, todayLabelTextView, totalNumTextView, totalUnitTextView, todayNumTextView, todayUnitTextView, numberLabelTextView;
        ProgressBar ProgressBar;
        String id="";
        private myHolder(View view)
        {
                super(view);
            wholeLabelTextView=(TextView) view.findViewById(R.id.wholeLabelTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            todayLabelTextView=(TextView) view.findViewById(R.id.todayLabelTextView);
            totalNumTextView=(TextView) view.findViewById(R.id.totalNumTextView);
            totalUnitTextView=(TextView) view.findViewById(R.id.totalUnitTextView);
            todayNumTextView=(TextView) view.findViewById(R.id.todayNumTextView);
            numberLabelTextView=(TextView) view.findViewById(R.id.numberLabelTextView);
            todayUnitTextView=(TextView) view.findViewById(R.id.todayUnitTextView);
            ProgressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
            ((MainActivity)context).setType("demibold", nameTextView,todayLabelTextView, totalNumTextView ,totalUnitTextView, todayNumTextView, todayUnitTextView );
            ((MainActivity)context).setType("light", wholeLabelTextView, numberLabelTextView);
        }
        private void setInfo(MaterialMiniForm form){
            id=form.getId();
            nameTextView.setText(form.getName());
            ProgressBar.setProgress(form.getProgress());
            totalUnitTextView.setText(form.getwUnit());
            totalNumTextView.setText(form.getWhole()+"");
            todayNumTextView.setText(form.getToday()+"");
            todayUnitTextView.setText(form.gettUnit());
        }
    }
    List<MaterialMiniForm> list;
    Context context;
    public MaterialMiniAdapter(List<MaterialMiniForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.materialmini_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
