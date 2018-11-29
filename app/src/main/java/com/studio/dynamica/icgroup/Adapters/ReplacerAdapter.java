package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.PhonesRowForm;
import com.studio.dynamica.icgroup.Forms.UserRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ReplacerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<UserRowForm> list;
    Context context;
    class MyHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        ImageView phonesRowPhoneImageView;
        TextView positionTextView, dateTextView;
        public MyHolder(View view){
            super(view);
            context=view.getContext();

            nameTextView=(TextView) view.findViewById(R.id.phonesRowNameTextView1);
            nameTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            dateTextView=(TextView) view.findViewById(R.id.phonesRowPhoneDateView);
            dateTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));

            positionTextView=(TextView) view.findViewById(R.id.phonesRowPositionTextView1);
            positionTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));
            phonesRowPhoneImageView=(ImageView)view.findViewById(R.id.phonesRowPhoneImageView);
            phonesRowPhoneImageView.setVisibility(View.GONE);
        }
        private void setInfo(final UserRowForm form){
            nameTextView.setText(form.getName());
            positionTextView.setText(form.getPosition());
            dateTextView.setText(form.getDate());
        }
    }

    public ReplacerAdapter(List<UserRowForm> list){
        this.list=list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position){
        MyHolder holder=(MyHolder)holder1;
        UserRowForm form=list.get(position);
        holder.setInfo(form);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        context=groupm.getContext();
        View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.phones_row, groupm, false);
        return new MyHolder(itemView);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }



}
