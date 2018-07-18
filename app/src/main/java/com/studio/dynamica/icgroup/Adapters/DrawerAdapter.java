package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.RowFormPTN;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;

public class    DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<RowFormPTN> rowFormPTNS;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView name;
        TextView num;
        ConstraintLayout layout;
        public MyViewHolder(View view){
            super(view);
            iv=(ImageView) view.findViewById(R.id.drawerRawImage);
            name=(TextView) view.findViewById(R.id.drawerRawName);
            num=(TextView) view.findViewById(R.id.drawerNumber);
            layout=(ConstraintLayout) view.findViewById(R.id.recyclerConstraintRaw);
            name.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNext-Medium.ttf"));
        }
    }
    public class FirstHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView position1;
        TextView notifications;
        public FirstHolder(View view){
            super(view);
            name=(TextView) view.findViewById(R.id.drawerNameText);
            position1=(TextView) view.findViewById(R.id.drawerPositionText);
            notifications=(TextView) view.findViewById(R.id.notificationNum);
        }
    }
    public DrawerAdapter(ArrayList<RowFormPTN> rowFormPTNS,Context context){
        this.rowFormPTNS=rowFormPTNS;
        this.context=context;
    }
    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position){
        if(getItemViewType(position)>0){
           MyViewHolder holder=(MyViewHolder)holder1;
        RowFormPTN rowFormPTN=rowFormPTNS.get(position-1);
        holder.name.setText(rowFormPTN.getName());
        holder.iv.setImageResource(rowFormPTN.getDrawable());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).setPage(position-1);
            }
        });
        if(rowFormPTN.getNum()>=0)
        holder.num.setText(rowFormPTN.getNum()+"");
        else{
            holder.num.setVisibility(View.INVISIBLE);
        }
        }
        else{
            FirstHolder holder=(FirstHolder)holder1;
            holder.name.setText("Ерасыл\nМухамеди");
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        if(viewType>0) {
            View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.drawer_raw, groupm, false);
            return new DrawerAdapter.MyViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.drawer_start_row, groupm, false);
            return new DrawerAdapter.FirstHolder(itemView);
        }
    }
        @Override
        public int getItemCount() {
            return rowFormPTNS.size()+1;
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
