package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    class myHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public myHolder(View view){
            super(view);
            textView=(TextView) view.findViewById(R.id.dayText);
            textView.setTypeface(((MainActivity)view.getContext()).getTypeFace("light"));
        }
    }
    int n;
    int clicked;
    Context context;List<myHolder> tview;
    public DaysAdapter(int n){
        this.n=n;
        clicked=0;
    }

    public int getClicked() {
        return clicked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.days_row,parent,false);
        context=parent.getContext();
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
                myHolder holder=(myHolder)holder1;
                holder.textView.setText((position+1)+"");
                if(position==clicked){
                  //  Log.d("DaysAdapter",position+" "+clicked);
                    setGreen(holder.textView);
                }
                else{
                    setWhite(holder.textView);
                }
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int c=clicked;
                        clicked=position;
                        notifyItemChanged(c);
                        notifyItemChanged(position);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return n;
    }
    private void setGreen(TextView t){
        t.setBackgroundResource((R.drawable.green_circle));
        t.setTextColor(context.getResources().getColor(R.color.white));
    }
    public void setWhite(TextView t){
        t.setBackgroundColor(context.getResources().getColor(R.color.white));
        t.setTextColor(context.getResources().getColor(R.color.black));
    }
}
