package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout extraLayout;
        TextView name, date,textTextView ;
        RecyclerView rateView;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            name=(TextView) view.findViewById(R.id.name);
            date=(TextView) view.findViewById(R.id.date);
            textTextView=(TextView) view.findViewById(R.id.textTextView);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            rateView=(RecyclerView) view.findViewById(R.id.rateView);
            ((MainActivity) context).setRecyclerViewOrientation(rateView, LinearLayoutManager.VERTICAL);
            setFonttype();
        }
        private void setFonttype(){
            name.setTypeface(((MainActivity) context).getTypeFace("demibold"));
            date.setTypeface(((MainActivity) context).getTypeFace("demibold"));
            textTextView.setTypeface(((MainActivity) context).getTypeFace("light"));
        }
        private void setInfo(MessageForm form){
            if(form.isFull()){
                extraLayout.setVisibility(View.VISIBLE);
                rateView.setAdapter(new RateStarsAdapter(form.getRate()));
                name.setText(form.getFIO());
                date.setText(form.getDate());
                textTextView.setText(form.getText());
            }
            else{
                extraLayout.setVisibility(View.GONE);
                textTextView.setText(form.getText());
            }
        }
    }
    Context context;
    List<MessageForm> list;
    public MessageAdapter(List<MessageForm> forms){
        list=forms;
    }
    public MessageAdapter(MessageForm form){
        list=new ArrayList<>();
        list.add(form);
    }

    public void setList(List<MessageForm> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderq, int position) {
        myHolder holder=(myHolder) holderq;
        holder.setInfo(list.get(position));
    }

    public List<MessageForm> getList() {
        return list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        context=parent.getContext();
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
