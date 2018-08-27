package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Forms.PassInventorizationForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class PassInventorizationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ImageView statusImageView;
        TextView textView;
        Context context;
        ProgressBar progressBar;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            statusImageView=(ImageView) view.findViewById(R.id.statusImageView);
            textView=(TextView) view.findViewById(R.id.textTextView);
            progressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
        }
        private void setInfo(PassInventorizationForm form){
            if(!form.isStatus()){
                statusImageView.setBackgroundResource(R.drawable.grey_circle);
                textView.setTextColor(context.getResources().getColor(R.color.darkgrey));
                progressBar.setProgress(0);
            }
        }
    }
    Context context;
    List<PassInventorizationForm> list;
    public PassInventorizationAdapter(List<PassInventorizationForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.pass_inventorization_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
