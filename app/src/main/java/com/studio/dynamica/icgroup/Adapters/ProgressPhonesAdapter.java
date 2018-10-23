package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ProgressPhoneForm;
import com.studio.dynamica.icgroup.ObjectFragments.PassportObjectInfoListOPUFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ProgressPhonesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ProgressPhoneForm> list;
    Context context;
    PassportObjectInfoListOPUFragment fragment;
    private class myHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView positionTextView;
        ProgressBar progressBar;
        TextView PercentageTextView;
        LinearLayout wholeLayout;
        TextView nameChangeText;
        TextView PositionText;
        TextView changeText;
        LinearLayout linearLayout;
        private myHolder(View view){
            super(view);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            linearLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            nameTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            positionTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));
            progressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
            PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
            PercentageTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            nameChangeText=(TextView) view.findViewById(R.id.nameChangeTextView);
            nameChangeText.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            PositionText=(TextView) view.findViewById(R.id.positionChangeTextView);
            PositionText.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));
            changeText=(TextView) view.findViewById(R.id.changeText);
            changeText.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));

        }
    }
    public ProgressPhonesAdapter(List<ProgressPhoneForm> forms,Context context){
        this.context=context;
        list=forms;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup group,int viewType){
        View itemView = LayoutInflater.from(group.getContext()).inflate(R.layout.progress_rowphone_form, group, false);
        return new ProgressPhonesAdapter.myHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position){
        myHolder holder=(myHolder) holder1;
        ProgressPhoneForm form=list.get(position);
        holder.nameTextView.setText(form.getForm().getName());
        holder.positionTextView.setText(form.getForm().getPosition());
        holder.progressBar.setProgress(form.getProgress());
        holder.PercentageTextView.setText(form.getProgress()+"%");
        fragment=new PassportObjectInfoListOPUFragment();
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).setFragment(R.id.content_frame,fragment);
            }
        });

        if(list.get(position).isChange()){
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.changeText.setText(form.getText());
                holder.nameChangeText.setText(form.getChangeForm().getName());
                holder.PositionText.setText(form.getChangeForm().getPosition());
        }
        else{
            holder.linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}
