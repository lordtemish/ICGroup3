package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Forms.CommentaryForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class CommentaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, dateTextView, textTextView;
        private myHolder(View view){
            super(view);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            textTextView=(TextView) view.findViewById(R.id.textTextView);
        }
        private void setInfo(CommentaryForm form){
            nameTextView.setText(form.getName());
            dateTextView.setText(form.getDate());
            textTextView.setText(form.getText());
        }
    }
    List<CommentaryForm> list;
    Context context;
    public CommentaryAdapter(List<CommentaryForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.commentary_row,parent,false);
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
