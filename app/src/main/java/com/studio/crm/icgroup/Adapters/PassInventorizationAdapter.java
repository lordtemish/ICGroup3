package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.PassInventorizationForm;
import com.studio.crm.icgroup.ObjectFragments.InventoryPassInventorizationSetFragment;
import com.studio.crm.icgroup.R;

import java.util.List;

public class PassInventorizationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ImageView statusImageView;
        TextView textView, PercentageTextView;
        Context context;
        ProgressBar progressBar;
        ConstraintLayout wholeLayout;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            statusImageView=(ImageView) view.findViewById(R.id.statusImageView);
            textView=(TextView) view.findViewById(R.id.textTextView);
            PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
            progressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
        }
        private void setInfo(final PassInventorizationForm form){
            textView.setText(form.getText());
            progressBar.setProgress(form.getPerc());
            PercentageTextView.setText(form.getPerc()+"%");
            if(!form.isStatus()){
                statusImageView.setBackgroundResource(R.drawable.grey_circle);
                textView.setTextColor(context.getResources().getColor(R.color.darkgrey));
            }
            else{
                statusImageView.setBackgroundResource(R.drawable.green_circle);
                textView.setTextColor(context.getResources().getColor(R.color.black));
            }

            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(list.indexOf(form)).setStatus(true);
                    InventoryPassInventorizationSetFragment setFragment=new InventoryPassInventorizationSetFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("point",point);
                    bundle.putString("id",id);
                    bundle.putString("kind",form.getKind());
                    bundle.putString("name",form.getText());
                    setFragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(R.id.content_frame, setFragment);
                }
            });
        }
    }
    Context context;
    List<PassInventorizationForm> list;
    private String point="", id="";

    public void setPoint(String point) {
        this.point = point;
    }

    public void setId(String id) {
        this.id = id;
    }

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
