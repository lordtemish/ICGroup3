package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AvrPositionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        List<TextView> infoTexts;
        RecyclerView rate;
        RateStarsAdapter adapter;
        private myHolder(View view){
            super(view);

            int[] infoIds={R.id.workerName,R.id.workerPosition,R.id.clientName,R.id.clientPosition,R.id.serType, R.id.serProcessType , R.id.areaTextView, R.id.priceTextView, R.id.summTextView };
            infoTexts=new ArrayList<>();

            for(int i=0;i<infoIds.length;i++){
                infoTexts.add((TextView)view.findViewById(infoIds[i]));
            }

            rate=(RecyclerView)view.findViewById(R.id.rate);
            ((MainActivity)context).setRecyclerViewOrientation(rate, LinearLayoutManager.VERTICAL);
            adapter=new RateStarsAdapter(5);
            rate.setAdapter(adapter);
        }
        private void setInfo(JSONObject object){
            try {

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    Context context;
    List<JSONObject> list;

    public AvrPositionsAdapter(List<JSONObject> objects){
        list=objects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return null;
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
