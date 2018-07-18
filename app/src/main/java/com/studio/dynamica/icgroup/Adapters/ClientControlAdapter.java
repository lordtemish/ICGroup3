package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Forms.OlkForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ClientControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class OlkHolder extends RecyclerView.ViewHolder{
        private OlkHolder(View v){
            super(v);
        }
    }
    Context context;
    int page;
    List<OlkForm> list;
    public ClientControlAdapter(List<OlkForm> list){
        this.list=list;
        page=0;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       context= parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.olk_row,parent,false);
        return new OlkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
