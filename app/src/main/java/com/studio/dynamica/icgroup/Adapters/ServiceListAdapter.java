package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ServiceListForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ServiceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public class myHolder extends RecyclerView.ViewHolder{
        boolean open;
        TextView textView;
        ImageView imageView;
        RecyclerView recyclerView;
        ConstraintLayout constraintLayout;
        Context context;
        public myHolder(View view){
            super(view);
            context=view.getContext();
            textView=(TextView) view.findViewById(R.id.text);
            textView.setTypeface(((MainActivity)context).getTypeFace("bold"));
            imageView=(ImageView) view.findViewById(R.id.arrowImageView);
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager manager=new LinearLayoutManager(view.getContext());
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(manager);

            constraintLayout=(ConstraintLayout) view.findViewById(R.id.topLayout);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    open();
                }
            });
        }
        public void open(){
            if(open){
                open=false;
                recyclerView.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.ic_arrowdown);
            }
            else{
                open=true;
                recyclerView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.ic_arrowup);
            }
        }
    }
    List<ServiceListForm> list;
    public ServiceListAdapter(List<ServiceListForm> listForms){
        list=listForms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_mainrow,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        final myHolder holder=(myHolder)holder1;
        holder.textView.setText(list.get(position).getName());
        ServicePeriodAdapter adapter=new ServicePeriodAdapter(list.get(position).getForms());
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
