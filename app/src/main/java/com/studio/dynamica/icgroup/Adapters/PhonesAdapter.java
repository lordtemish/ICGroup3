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

import com.studio.dynamica.icgroup.Forms.PhonesRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class PhonesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<PhonesRowForm> list;
    Context context;
    class MyHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layout1;
        ConstraintLayout layout;
        TextView nameTextView1;
        TextView positionTextView1;
        TextView nameTextView;
        TextView positionTextView;
        ImageView imageView;
        ImageView imageView1;
        public MyHolder(View view){
            super(view);
            layout=(ConstraintLayout) view.findViewById(R.id.phonesRowLayout);
            layout1=(ConstraintLayout) view.findViewById(R.id.phonesRowLayout1);

            nameTextView=(TextView) view.findViewById(R.id.phonesRowNameTextView);
            nameTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            nameTextView1=(TextView) view.findViewById(R.id.phonesRowNameTextView1);
            nameTextView1.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));

            positionTextView=(TextView) view.findViewById(R.id.phonesRowPositionTextView);
            positionTextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));
            positionTextView1=(TextView) view.findViewById(R.id.phonesRowPositionTextView1);
            positionTextView1.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.ttf"));
        }
    }

    public PhonesAdapter(List<PhonesRowForm> list, Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position){
            MyHolder holder=(MyHolder)holder1;
            PhonesRowForm form=list.get(position);
            if(form.isType()){
                holder.positionTextView1.setText(form.getPosition());
                holder.nameTextView1.setText(form.getName());
            }
            else {
                holder.layout1.setVisibility(View.GONE);
                holder.positionTextView.setText(form.getPosition());
                holder.nameTextView.setText(form.getName());
            }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.phones_row, groupm, false);
        return new PhonesAdapter.MyHolder(itemView);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }
        else {
            return 1;
        }
    }

}
