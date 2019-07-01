package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.studio.crm.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class RateStarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int rate;
    boolean touchable=false;
    Context context;
    private class myHolder extends RecyclerView.ViewHolder{
        List<ImageView> favs;
        private myHolder(View view){
            super(view);
            favs=new ArrayList<>();
            favs.add((ImageView) view.findViewById(R.id.Fav0));
            favs.add((ImageView) view.findViewById(R.id.Fav1));
            favs.add((ImageView) view.findViewById(R.id.Fav2));
            favs.add((ImageView) view.findViewById(R.id.Fav3));
            favs.add((ImageView) view.findViewById(R.id.Fav4));
            setTouch();
        }
        private void setTouch(){
            if(touchable){
                for(int i=0;i<5;i++) {
                    favs.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rate = favs.indexOf(view) + 1;
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        }
        private void setGrey(int a){
            favs.get(a).setImageResource(R.drawable.ic_stargrey);
        }
        private void setYellow(int a){
            favs.get(a).setImageResource(R.drawable.ic_staryellow);
        }
    }
public RateStarsAdapter(int rate){
        this.rate=rate;
}
public RateStarsAdapter(boolean to){
        touchable=to;
        rate=1;
}

    public void setRate(int rate) {
        this.rate = rate;
        if(rate<1 || rate>5){
            this.rate=0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.rate_stars_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        final myHolder holder=(myHolder) holder1;
        for(int i=0;i<rate;i++){
            holder.setYellow(i);
        }
        for(int i=rate;i<5;i++){
            holder.setGrey(i);
        }
    }
    @Override
    public int getItemCount() {
        return 1;
    }

    public int getRate() {
        return rate;
    }
}
