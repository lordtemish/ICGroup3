package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.AcceptForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class AcceptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, placeTextView, positionTextView, statusTextView;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            statusTextView=(TextView) view.findViewById(R.id.statusTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            placeTextView=(TextView) view.findViewById(R.id.placeTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            setFonts();
        }
        private void setFonts(){
            positionTextView.setTypeface(((MainActivity) context).getTypeFace("light"));
            placeTextView.setTypeface(((MainActivity) context).getTypeFace("light"));
            nameTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
            statusTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        }
        private void setInfo(AcceptForm form){
            setStatus(form.isGreen());
            nameTextView.setText(form.getName());
            statusTextView.setText(form.getStatus());
            placeTextView.setText(form.getPlace());
            positionTextView.setText(form.getPosition());
        }
        private void setStatus(boolean a){
            if(a){
                statusTextView.setBackgroundResource(R.drawable.greenbutton_rec);
            }
            else{
                statusTextView.setBackgroundResource(R.drawable.greybutton_rec);
            }
        }
    }
    List<AcceptForm> list;
    Context context;
    public AcceptAdapter(List<AcceptForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.accept_row,parent,false);
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
