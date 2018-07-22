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
    private class checkListHolder extends RecyclerView.ViewHolder{
        private checkListHolder(View v){
            super(v);
        }
    }
    Context context;
    int page;
    List<Object> list;
    public ClientControlAdapter(List<Object> list, int page){
        this.list=list;
        this.page=page;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       context= parent.getContext();
       int layout=R.layout.olk_row;

       if(page==1){
           layout=R.layout.check_list_row;
       }
       if(page==2){
           layout=R.layout.olk_row;
       }
        View view= LayoutInflater.from(context).inflate(layout,parent,false);
        RecyclerView.ViewHolder holder;
        switch (page){
            case 1:
                holder=new checkListHolder(view);
                break;
                default:
                    holder=new OlkHolder(view);
                    break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder3, int position) {
        switch (page){
            case 0:
                OlkHolder holder=(OlkHolder)holder3;
                break;
            case 1:
                checkListHolder holder1=(checkListHolder) holder3;
                break;
            case 2:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
