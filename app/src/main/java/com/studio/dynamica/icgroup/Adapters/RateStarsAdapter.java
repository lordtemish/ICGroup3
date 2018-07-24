package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class RateStarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int rate;
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
        }
        private void setGrey(int a){
            favs.get(a).setImageResource(R.drawable.ic_stargrey);
        }
    }
public RateStarsAdapter(int rate){
        this.rate=rate;

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
        myHolder holder=(myHolder) holder1;
        for(int i=rate;i<5;i++){
            holder.setGrey(i);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
