package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ClientsMainForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ClientsMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        TextView title,role,nameTextView, objectsLabel;
        ImageView settings,avatar;
        RecyclerView rateRecyclerView, recyclerView;
        private myHolder(View view){
            super(view);
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            title=(TextView)view.findViewById(R.id.title);
            role=(TextView)view.findViewById(R.id.role);
            objectsLabel=(TextView)view.findViewById(R.id.objectsLabel);
            settings=(ImageView) view.findViewById(R.id.settings);
            avatar=(ImageView) view.findViewById(R.id.avatar);
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
            rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
            ((MainActivity)context).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
            ((MainActivity)context).setRecyclerViewOrientation(rateRecyclerView, LinearLayoutManager.VERTICAL);
            ((MainActivity)context).setType("demibold", title,nameTextView);
            ((MainActivity)context).setType("light", role, objectsLabel);
        }
        private void setInfo(ClientsMainForm form){
                title.setText(form.getName());
                nameTextView.setText(form.getFullname());
                ClientsPointAdapter adapter=new ClientsPointAdapter(form.getPointForms());
                recyclerView.setAdapter(adapter);
                rateRecyclerView.setAdapter(new RateStarsAdapter(form.getRate()));
        }
    }
    Context context;
    List<ClientsMainForm> list;
    public ClientsMainAdapter(List<ClientsMainForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
            myHolder holder=(myHolder)holder1;
            holder.setInfo(list.get(position));
            if(position==list.size()-1){

            }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.clientsmain_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
