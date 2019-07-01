package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.ChooseAcceptForm;
import com.studio.crm.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseAcceptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        List<ConstraintLayout> wholeLayout=new ArrayList<>();
        List<ImageView> galochkaImageView=new ArrayList<>();
        List<TextView> nameTextView=new ArrayList<>(),placeTextView=new ArrayList<>(), positionTextView=new ArrayList<>();
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout.add((ConstraintLayout) view.findViewById(R.id.firstWholeLayout));wholeLayout.add((ConstraintLayout) view.findViewById(R.id.secondWholeLayout));
            galochkaImageView.add((ImageView) view.findViewById(R.id.firstGalochkaImageView));galochkaImageView.add((ImageView) view.findViewById(R.id.secondGalochkaImageView));
            nameTextView.add((TextView) view.findViewById(R.id.firstNameTextView));nameTextView.add((TextView) view.findViewById(R.id.secondNameTextView));
            placeTextView.add((TextView) view.findViewById(R.id.firstPlaceTextView));placeTextView.add((TextView) view.findViewById(R.id.secondPlaceTextView));
            positionTextView.add((TextView) view.findViewById(R.id.firstPositionTextView));positionTextView.add((TextView) view.findViewById(R.id.secondPositionTextView));
            setFonttype();
        }
        private void setFonttype(){
            setTypeface("demibold",nameTextView.get(0),nameTextView.get(1));
            setTypeface("light",positionTextView.get(0),positionTextView.get(1),placeTextView.get(0),placeTextView.get(1));
        }
        private void setTypeface(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }
        private void setClickListener(int b,boolean a){
                    if(!a){
                        setWhite(b);
                    }
                    else{
                        setGreen(b);
                    }
        }
        private void setGreen(int a){
            wholeLayout.get(a).setBackgroundResource(R.drawable.greenbutton_rec);
            galochkaImageView.get(a).setBackgroundResource(R.drawable.lightgreen_circle);
            nameTextView.get(a).setTextColor(context.getResources().getColor(R.color.white));
            placeTextView.get(a).setTextColor(context.getResources().getColor(R.color.white));
            positionTextView.get(a).setTextColor(context.getResources().getColor(R.color.white));
        }
        private void setWhite(int a){
            wholeLayout.get(a).setBackgroundResource(R.drawable.grey_corners_line);
            galochkaImageView.get(a).setBackgroundResource(R.drawable.grey_circle);
            nameTextView.get(a).setTextColor(context.getResources().getColor(R.color.darkgrey));
            placeTextView.get(a).setTextColor(context.getResources().getColor(R.color.darkgrey));
            positionTextView.get(a).setTextColor(context.getResources().getColor(R.color.darkgrey));
        }
        private void setSecondDelete(){
            wholeLayout.get(1).setVisibility(View.INVISIBLE);
            wholeLayout.get(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        private void setSecondShow(){
            wholeLayout.get(1).setVisibility(View.VISIBLE);
        }
    }
    List<ChooseAcceptForm> list;
    Context context;
    public ChooseAcceptAdapter(List<ChooseAcceptForm> formList){
        list=formList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.chooseaccept_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        final myHolder holder=(myHolder)holder1;
        for(int i=0;i<2;i++){
            final int j=(position*2)+i;
            //Log.d("ChooseAcceptAdapter"," p="+position+", j="+j);
            if(j==list.size()){
                holder.setSecondDelete();
                Log.d("ChooseAcceptAdapter",(holder.wholeLayout.get(1).getVisibility()== View.VISIBLE )+ " p="+position+", j="+j);
                break;
            }
            else{
                holder.setSecondShow();
            }
            holder.setClickListener(i,list.get(j).isChose());
            holder.wholeLayout.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(j).setChose(!list.get(j).isChose());
                    //notifyItemChanged(position);
                    list.get(j).getListener().onClick(view);
                    notifyDataSetChanged();
                }
            });
            holder.nameTextView.get(i).setText(list.get(j).getName());
            holder.placeTextView.get(i).setText(list.get(j).getPlace());
            holder.positionTextView.get(i).setText(list.get(j).getPosition());
        }
    }

    @Override
    public int getItemCount() {
        return (list.size()+1)/2;
    }
}
