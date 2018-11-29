package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.InventoryStatusForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ImageView statusImageView;
        RecyclerView commentaryRecyclerView, remontRecyclerView;
        Context context;
        LinearLayout extraLayout;
        TextView nameTextView, idTextView, numberTextView;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            statusImageView=(ImageView) view.findViewById(R.id.statusImageView);
            commentaryRecyclerView=(RecyclerView) view.findViewById(R.id.commentaryRecyclerView);
            remontRecyclerView=(RecyclerView) view.findViewById(R.id.remontRecycler);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            idTextView=(TextView) view.findViewById(R.id.idTextView);
            numberTextView=(TextView) view.findViewById(R.id.numberTextView);
            ((MainActivity)context).setType("demibold",nameTextView);
            ((MainActivity)context).setType("light",numberTextView);
            ((MainActivity)context).setType("medium",idTextView);
        }
        private void setInfo(InventoryStatusForm form){
            idTextView.setText(form.getVendor());
            nameTextView.setText(form.getName());
            numberTextView.setText(form.getNumber()+" "+form.getUnit());
            if(!form.isStatus()){
                statusImageView.setBackgroundResource(R.drawable.yellow_circle);

            }
            if(form.getRemontForms().size()>0){
                ((MainActivity)context).setRecyclerViewOrientation(remontRecyclerView, LinearLayout.VERTICAL);
                RemontAdapter remontAdapter=new RemontAdapter(form.getRemontForms(), false);
                remontRecyclerView.setAdapter(remontAdapter);extraLayout.setVisibility(View.VISIBLE);
            }
            if(form.getCommentaryForms().size()>0){
                ((MainActivity)context).setRecyclerViewOrientation(commentaryRecyclerView,LinearLayout.VERTICAL);
                CommentaryAdapter commentaryAdapter=new CommentaryAdapter(form.getCommentaryForms());
                commentaryRecyclerView.setAdapter(commentaryAdapter);extraLayout.setVisibility(View.VISIBLE);
            }
        }
    }
    Context context;
    List<InventoryStatusForm> list;
    public InventoryAdapter(List<InventoryStatusForm> list){
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.inventory_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
