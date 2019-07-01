package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.MessageWorkForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class MessageWorkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, markTextView, textTextView;
        LinearLayout extraLayout, wholeLayout;
        ImageView messageImageView;
        private myHolder(View view){
            super(view);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            textTextView=(TextView) view.findViewById(R.id.textTextView);
            markTextView=(TextView) view.findViewById(R.id.markTextView);
            messageImageView=(ImageView) view.findViewById(R.id.messageImageView);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            setFonttypes();
        }
        private void setFonttypes(){
            setTypeFace("demibold",markTextView, nameTextView);
            setTypeFace("light", textTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for (int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity) context).getTypeFace(s));
            }
        }
        private void setInfo(MessageWorkForm form){
            nameTextView.setText(form.getName());
            int a=View.INVISIBLE;

            markTextView.setText(form.getNum()+"");
            if(form.getInfo().length()>0){
                textTextView.setText(form.getInfo());
                a=View.VISIBLE;
                wholeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(extraLayout.getVisibility()==View.VISIBLE){
                            extraLayout.setVisibility(View.GONE);
                        }
                        else{
                            extraLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
            messageImageView.setVisibility(a);
        }
    }
    Context context;
    List<MessageWorkForm> list;
    public MessageWorkAdapter(List<MessageWorkForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.messagework_row,parent,false);
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
