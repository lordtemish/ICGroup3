package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.MiniObjectForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class MiniObjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout extraLayout;
        ConstraintLayout upLayout;
        TextView name,plusrow;
        RecyclerView recyclerView;

        private myHolder(View view){
            super(view);
            upLayout=(ConstraintLayout)view.findViewById(R.id.upLayout);
            extraLayout=(LinearLayout)view.findViewById(R.id.extraLayout);
            recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
            name=(TextView)view.findViewById(R.id.name);
            plusrow=(TextView)view.findViewById(R.id.plusrow);
            ((MainActivity)context).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
            ((MainActivity)context).setType("demibold",name,plusrow);
        }
        private void setInfo(final MiniObjectForm form){
            name.setText(form.getName());
            if(form.isOpen()){
                extraLayout.setVisibility(View.VISIBLE);
                plusrow.setText("-");
            }
            else{
                extraLayout.setVisibility(View.GONE);
                plusrow.setText("+");
            }
            recyclerView.setAdapter(new MiniObjectTaskAdapter(form.getForms()));
            upLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(list.indexOf(form)).setOpen(!form.isOpen());
                    notifyItemChanged(list.indexOf(form));
                }
            });
        }
    }
    List<MiniObjectForm> list;
    Context context;
    public MiniObjectAdapter(List<MiniObjectForm> forms){
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
        View view= LayoutInflater.from(context).inflate(R.layout.miniobject_row,parent,false);
        return new myHolder(view);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
