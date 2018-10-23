package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class EquipmentReqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView text;
        FrameLayout clicked;
        ConstraintLayout wholeLayout;
        private myHolder(View view){
            super(view);
            clicked=(FrameLayout) view.findViewById(R.id.clicked);
            text=(TextView) view.findViewById(R.id.text);
            text.setTypeface(((MainActivity) context).getTypeFace("demibold"));
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
        }
        private void setInfo(String s, boolean a){
            text.setText(s);
            if(a){  clicked.setVisibility(View.VISIBLE);
            text.setTextColor(context.getResources().getColor(R.color.black));
            }else { clicked.setVisibility(View.GONE);
                text.setTextColor(context.getResources().getColor(R.color.darkgrey));
            }
        }
    }
    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.equipmentreq_row,parent,false);
        return new myHolder(view);
    }
    int clicked=0;
    List<String> list;
    private View.OnClickListener onClickListener;
    public EquipmentReqAdapter(List<String> strings){
        list=strings;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public int getClicked() {
        return clicked;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder) holder1;
        boolean aa=position==clicked;
        holder.setInfo(list.get(position),aa);

        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked=position;
                onClickListener.onClick(view);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
