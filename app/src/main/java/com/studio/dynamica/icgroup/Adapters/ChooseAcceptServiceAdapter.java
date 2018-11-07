package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseAcceptServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder {
        List<ConstraintLayout> wholeLayouts=new ArrayList<>();
        List<ImageView> galochkaImageView=new ArrayList<>();
        List<TextView> nameTextView=new ArrayList<>(),placeTextView=new ArrayList<>(), positionTextView=new ArrayList<>();
        private myHolder(View view){
            super(view);
            wholeLayouts.add((ConstraintLayout) view.findViewById(R.id.firstWholeLayout));wholeLayouts.add((ConstraintLayout) view.findViewById(R.id.secondWholeLayout));
            galochkaImageView.add((ImageView) view.findViewById(R.id.firstGalochkaImageView));galochkaImageView.add((ImageView) view.findViewById(R.id.secondGalochkaImageView));
            nameTextView.add((TextView) view.findViewById(R.id.firstNameTextView));nameTextView.add((TextView) view.findViewById(R.id.secondNameTextView));
            placeTextView.add((TextView) view.findViewById(R.id.firstPlaceTextView));placeTextView.add((TextView) view.findViewById(R.id.secondPlaceTextView));
            positionTextView.add((TextView) view.findViewById(R.id.firstPositionTextView));positionTextView.add((TextView) view.findViewById(R.id.secondPositionTextView));

            ((MainActivity)context).setType("demibold",placeTextView.get(0),nameTextView.get(0),placeTextView.get(1),nameTextView.get(1));
            ((MainActivity)context).setType("light",positionTextView.get(0),positionTextView.get(1));
        }
        private void setInfo(ChooseAcceptForm form, int num){
            nameTextView.get(num).setText(form.getName());
            placeTextView.get(num).setText(form.getPlace());
            positionTextView.get(num).setText(form.getPosition());
            if(form.isChose()){
                galochkaImageView.get(num).setBackgroundResource(R.drawable.green_circle);
            }
            else galochkaImageView.get(num).setBackgroundResource(R.drawable.grey_circle);

            if(form.isClient()){
                wholeLayouts.get(num).setBackgroundResource(R.drawable.grey_corners_line);
            }
            else{
                wholeLayouts.get(num).setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            }
        }
        private void setSecondDelete(){
            wholeLayouts.get(1).setVisibility(View.INVISIBLE);
            wholeLayouts.get(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        private void setSecondSHow(){
            wholeLayouts.get(1).setVisibility(View.VISIBLE);
        }
    }
    List<ChooseAcceptForm> list;
    Context context;
    public ChooseAcceptServiceAdapter(List<ChooseAcceptForm> formList){
        list=formList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.choose_accept_service_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        for(int i=0;i<2;i++){
            final int j=(position*2)+i;
            Log.d("ChooseAcceptAdapter"," p="+position+", j="+j);
            if(j==list.size()){
                holder.setSecondDelete();
                break;
            }
            else{
                holder.setSecondSHow();
            }
            holder.setInfo(list.get(j),i);
            holder.wholeLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(j).setChose(!list.get(j).isChose());
                    notifyDataSetChanged();
                    if(list.get(j).isListen()){
                        list.get(j).getListener().onClick(view);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return (list.size()+1)/2;
    }
}
