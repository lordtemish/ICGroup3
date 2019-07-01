package com.studio.crm.icgroup.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.RowFormPTN;
import com.studio.crm.icgroup.MainFragments.MainProfileFragment;
import com.studio.crm.icgroup.NotificationFragments.NotificationFragment;
import com.studio.crm.icgroup.R;

import java.util.ArrayList;

public class    DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<RowFormPTN> rowFormPTNS;
    Context context;
    private int clicked=-1;
    private String role="SUPERADMIN";
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
        private void setClient(boolean a){
            if(a){
                num.setBackgroundResource(R.drawable.lightgreen_circle);
                name.setTextColor(context.getResources().getColor(R.color.black));
                layout.setBackgroundResource(R.drawable.white_grey_click_background);
            }
            else{
                num.setBackgroundResource(R.drawable.green_circle);
                name.setTextColor(context.getResources().getColor(R.color.black));
                layout.setBackgroundResource(R.drawable.white_grey_click_background);
            }
        }
    }
    public class FirstHolder extends RecyclerView.ViewHolder{
        ImageView greenImage, settings;
        TextView name;
        TextView position1;
        TextView notifications;
        MainActivity a;
        LinearLayout notificationLayout;
        public FirstHolder(View view){
            super(view);
            name=(TextView) view.findViewById(R.id.drawerNameText);
            position1=(TextView) view.findViewById(R.id.drawerPositionText);
            notifications=(TextView) view.findViewById(R.id.notificationNum);
            greenImage=(ImageView) view.findViewById(R.id.greenImageDrawer);
            settings=(ImageView) view.findViewById(R.id.settings);
            notificationLayout=(LinearLayout) view.findViewById(R.id.notificationLayout);

            greenImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new MainProfileFragment();
                    if(clicked!=-1) {
                        rowFormPTNS.get(clicked).setClicked(false);
                        clicked = -1;
                    }
                    ((MainActivity)context).setFragment(R.id.content_frame,fragment);
                    ((MainActivity)context).closeDrawer();
                    notifyDataSetChanged();
                }
            });
            a=((MainActivity)context);
            a.setType("demibold",name, position1);
        }
        private void setInfo(){
            name.setText(a.name);
            position1.setText(a.positions.get(a.role));
            String photo=a.avatar;
            if(photo.length()>0){
                a.setPhoto(photo,greenImage);
            }
        }
    }
    private class exHolder extends RecyclerView.ViewHolder{
        ConstraintLayout exitLayout;
        TextView exitText;
        private exHolder(View view){
            super(view);
            exitLayout=(ConstraintLayout)view.findViewById(R.id.exitLayout);
            exitText=(TextView)view.findViewById(R.id.exitText);
            ((MainActivity)context).setType("demibold",exitText);
            exitLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)context).logOut();
                }
            });
        }
    }
    boolean client=false;
    public DrawerAdapter(ArrayList<RowFormPTN> rowFormPTNS,Context context){
        this.rowFormPTNS=rowFormPTNS;
        this.context=context;
    }

    public void setClient(boolean client) {
        this.client = client;
    }

    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position){
        if(getItemViewType(position)==1){
           final MyViewHolder holder=(MyViewHolder)holder1;
        final RowFormPTN rowFormPTN=rowFormPTNS.get(position-1);
        holder.name.setText(rowFormPTN.getName());
        holder.setClient(client);
        if(rowFormPTN.isClicked()) {
            holder.iv.setImageResource(rowFormPTN.getDrawable());
            holder.layout.setBackgroundResource(R.drawable.grey_white_click_background);
        }
        else{
            holder.iv.setImageResource(rowFormPTN.getDrawable1());
            holder.layout.setBackgroundResource(R.drawable.white_grey_click_background);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked==-1){

                }
                else{
                    rowFormPTNS.get(clicked).setClicked(false);
                }
                clicked=position-1;
                rowFormPTNS.get(clicked).setClicked(true);
                ((MainActivity) context).setPage(position-1);
                notifyDataSetChanged();
            }
        });
        if(rowFormPTN.getNum()>=0) {
            holder.num.setText(rowFormPTN.getNum() + "");
            holder.num.setVisibility(View.VISIBLE);
        }
        else{
            holder.num.setVisibility(View.INVISIBLE);
        }
        }
        else if(getItemViewType(position)==0){
            FirstHolder holder=(FirstHolder)holder1;

            //((MainActivity) context).setPhoto("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/%D0%9D%D1%83%D1%80%D1%81%D1%83%D0%BB%D1%82%D0%B0%D0%BD_%D0%90%D0%B1%D0%B8%D1%88%D0%BE%D0%B2%D0%B8%D1%87_%D0%9D%D0%B0%D0%B7%D0%B0%D1%80%D0%B1%D0%B0%D0%B5%D0%B2.jpeg/267px-%D0%9D%D1%83%D1%80%D1%81%D1%83%D0%BB%D1%82%D0%B0%D0%BD_%D0%90%D0%B1%D0%B8%D1%88%D0%BE%D0%B2%D0%B8%D1%87_%D0%9D%D0%B0%D0%B7%D0%B0%D1%80%D0%B1%D0%B0%D0%B5%D0%B2.jpeg",holder.greenImage);
            if(client){
                holder.notificationLayout.setVisibility(View.GONE);
                holder.settings.setVisibility(View.GONE);
            }
            else {
                holder.notificationLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) context).setFragment(R.id.content_frame, new NotificationFragment());
                        if (clicked > -1) {
                            rowFormPTNS.get(clicked).setClicked(false);
                            clicked = -1;
                        }
                        ((MainActivity) context).closeDrawer();
                        notifyDataSetChanged();
                    }
                });
            }
            holder.setInfo();
        }
        else{
            exHolder holder=(exHolder)holder1;
            if(client){
                holder.exitLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.exitText.setTextColor(context.getResources().getColor(R.color.black));
            }
            else{
                holder.exitLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.exitText.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        if(viewType==1) {
            View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.drawer_raw, groupm, false);
            return new DrawerAdapter.MyViewHolder(itemView);
        }
        else if(viewType==2){
            View view = LayoutInflater.from(context).inflate(R.layout.exit_row,groupm,false);
            return new exHolder(view);
        }
        else {
            View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.drawer_start_row, groupm, false);
            return new DrawerAdapter.FirstHolder(itemView);
        }
    }
        @Override
        public int getItemCount() {
            return rowFormPTNS.size()+2;
        }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }
        else if(position==rowFormPTNS.size()+1){
            return 2;
        }
        else {
            return 1;
        }
    }

    public void setRole(String role) {
        this.role = role;
    }
}
