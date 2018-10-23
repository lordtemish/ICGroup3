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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.CheckListForm;
import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.Forms.OlkForm;
import com.studio.dynamica.icgroup.Forms.PhonesRowForm;
import com.studio.dynamica.icgroup.Forms.svodkaRateForm;
import com.studio.dynamica.icgroup.ObjectFragments.CheckListInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class ClientControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class OlkHolder extends RecyclerView.ViewHolder{
        ConstraintLayout markLayout;
        RecyclerView commentsRecyclerView, acceptRecycler, phonesRecyclerView;
        TextView wrapTextView, dateTextView, nameTextView, positionTextView, infoLabel, averageMarkTextView, quality, qualityMark, looking, lookingMark, inventory, inventoryMark,olkTook;
        LinearLayout extraLayout;
        Context context;
        private OlkHolder(View v){
            super(v);
            context=v.getContext();
            acceptRecycler=(RecyclerView) v.findViewById(R.id.acceptRecyclerView);
            phonesRecyclerView=(RecyclerView) v.findViewById(R.id.phonesRecyclerView);
            extraLayout=(LinearLayout) v.findViewById(R.id.extraLayout);
            wrapTextView=(TextView) v.findViewById(R.id.wrapTextView);
            dateTextView=(TextView) v.findViewById(R.id.dateTextView);
            nameTextView=(TextView) v.findViewById(R.id.nameTextView);
            infoLabel=(TextView) v.findViewById(R.id.infoLabel);
            positionTextView=(TextView) v.findViewById(R.id.positionTextView);
            quality=(TextView) v.findViewById(R.id.quality);
            qualityMark=(TextView) v.findViewById(R.id.qualityMark);
            looking=(TextView) v.findViewById(R.id.looking);
            lookingMark=(TextView) v.findViewById(R.id.lookingMark);
            inventory=(TextView) v.findViewById(R.id.inventory);
            inventoryMark=(TextView) v.findViewById(R.id.inventoryMark);
            averageMarkTextView=(TextView) v.findViewById(R.id.averageMarkTextView);
            olkTook=(TextView) v.findViewById(R.id.olkTook);
            markLayout=(ConstraintLayout) v.findViewById(R.id.markLayout);
            commentsRecyclerView=(RecyclerView) v.findViewById(R.id.commentsRecyclerView);
            wrapTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicked();
                }
            });
            setFontType();
        }
        private void setFontType(){
            setTypeFace("demibold", nameTextView, infoLabel, averageMarkTextView, qualityMark, lookingMark, inventoryMark,olkTook);
            setTypeFace("light", wrapTextView, dateTextView, positionTextView, quality, looking, inventory);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }
        private void setInfo(OlkForm form){

        }
        private void clicked(){
            if(markLayout.getVisibility()==View.VISIBLE){
                markLayout.setVisibility(View.GONE);
                wrapTextView.setText("Свернуть");
                extraLayout.setVisibility(View.VISIBLE);
            }
            else{
                markLayout.setVisibility(View.VISIBLE);
                wrapTextView.setText("Развернуть");
                extraLayout.setVisibility(View.GONE);
            }
        }
    }
    private class checkListHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout;
        TextView dateTextView,revisorTextView, nameTextView, rateTextView;
        private checkListHolder(View v){
            super(v);
            wholeLayout=(LinearLayout) v.findViewById(R.id.wholeLayout);
            dateTextView=(TextView) v.findViewById(R.id.dateTextView);
            revisorTextView=(TextView) v.findViewById(R.id.revisorTextView);
            nameTextView=(TextView) v.findViewById(R.id.nameTextView);
            rateTextView=(TextView) v.findViewById(R.id.rateTextView);
            setFontType();
        }
        private void setInfo(CheckListForm form){
            dateTextView.setText(form.getDate());
            nameTextView.setText(form.getFIO());
            rateTextView.setText(form.getMark()+"");
        }
        private void setFontType(){
            setTypeFace("demibold", dateTextView, nameTextView, rateTextView);
            setTypeFace("light", revisorTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }
    }
    private class svodkaRateHolder extends RecyclerView.ViewHolder{
        RecyclerView rateStartRecycler, commentsRecyclerView, acceptRecyclerView;
        TextView dateTextView, positionTextView, nameTextView, placeNum, placeNumLabel, cityLabel, city, address, addressLabel, clientLabel, clientName, clientPosition,tookLabel;
        ImageView arrowImage;
        LinearLayout extraLayout;
        private svodkaRateHolder(View view){
            super(view);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            arrowImage=(ImageView) view.findViewById(R.id.arrowImageView);
            rateStartRecycler=(RecyclerView) view.findViewById(R.id.starsRecyclerView);
            commentsRecyclerView=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
            acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            placeNum=(TextView) view.findViewById(R.id.placeNum);
            placeNumLabel=(TextView) view.findViewById(R.id.placeNumLabel);
            cityLabel=(TextView) view.findViewById(R.id.cityLabel);
            city=(TextView) view.findViewById(R.id.city);
            address=(TextView) view.findViewById(R.id.address);
            addressLabel=(TextView) view.findViewById(R.id.addressLabel);
            clientLabel=(TextView) view.findViewById(R.id.clientLabel);
            clientName=(TextView) view.findViewById(R.id.clientName);
            clientPosition=(TextView) view.findViewById(R.id.clientPosition );
            tookLabel=(TextView) view.findViewById(R.id.tookLabel );

            arrowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClicked();
                }
            });
            setFontType();
        }
        private void setFontType(){
            setTypeFace("demibold", nameTextView, dateTextView, placeNum, city, address, clientName);
            setTypeFace("light",positionTextView, placeNumLabel, cityLabel, addressLabel, clientLabel, clientPosition, tookLabel);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }
        private void setArrowResource(int resource){
            arrowImage.setImageResource(resource);
        }
        private void setVisibility(int visibility){
            extraLayout.setVisibility(visibility);
        }

        private void buttonClicked(){
            if(extraLayout.getVisibility()==View.VISIBLE){
                setArrowResource(R.drawable.ic_arrowdown);
                setVisibility(View.GONE);
            }
            else {
                setArrowResource(R.drawable.ic_arrowup);
                setVisibility(View.VISIBLE);
            }
        }
    }
    Context context;
    private int page;
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
       if(page==1){ layout=R.layout.check_list_row; }
       if(page==2){ layout=R.layout.svodka_rate_row; }
        View view= LayoutInflater.from(context).inflate(layout,parent,false);
        RecyclerView.ViewHolder holder;
        switch (page){
            case 1:
                holder=new checkListHolder(view);
                break;
            case 2:
                holder=new svodkaRateHolder(view);
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
                OlkForm olkForm=(OlkForm) list.get(position);
                holder.setInfo(olkForm);
                RecyclerView commentsRecycle=holder.commentsRecyclerView;
                RecyclerView.LayoutManager manager0=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

                RecyclerView phonesRecycle=holder.phonesRecyclerView;
                RecyclerView.LayoutManager manager01=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

                RecyclerView accceptReycler=holder.acceptRecycler;
                RecyclerView.LayoutManager manager02=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);

                List<MessageForm> messageForm=new ArrayList<>();messageForm.add(new MessageForm(context.getResources().getString(R.string.bigtext)));
                List<PhonesRowForm> rowForms=new ArrayList<>();rowForms.add(new PhonesRowForm(false,"Темирлан Алмасов","ОПУ","87017000154"));
                List<AcceptForm> acceptForms=new ArrayList<>();acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));

                PhonesAdapter adapter01=new PhonesAdapter(rowForms, context);
                phonesRecycle.setLayoutManager(manager01);
                phonesRecycle.setItemAnimator(new DefaultItemAnimator());
                phonesRecycle.setAdapter(adapter01);

                AcceptAdapter acceptAdapter=new AcceptAdapter(acceptForms);
                accceptReycler.setItemAnimator(new DefaultItemAnimator());
                accceptReycler.setLayoutManager(manager02);
                accceptReycler.setAdapter(acceptAdapter);

                MessageAdapter adapter0=new MessageAdapter(messageForm);
                commentsRecycle.setLayoutManager(manager0);
                commentsRecycle.setItemAnimator(new DefaultItemAnimator());
                commentsRecycle.setAdapter(adapter0);

                break;
            case 1:
                checkListHolder holder1=(checkListHolder) holder3;
                CheckListForm form=(CheckListForm) list.get(position);
                holder1.setInfo(form);
                holder1.wholeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) context).setFragment(R.id.content_frame,new CheckListInfoFragment());
                    }
                });
                break;
            case 2:
                svodkaRateForm form2=(svodkaRateForm) list.get(position);

                svodkaRateHolder holder2=(svodkaRateHolder) holder3;
                RecyclerView recyclerView=holder2.rateStartRecycler;
                RecyclerView commentsRecycler=holder2.commentsRecyclerView;
                RecyclerView accceptReycler0=holder2.acceptRecyclerView;

                List<MessageForm> messageForms=new ArrayList<>();messageForms.add(new MessageForm("Fuck that shitasdlfhjkashfjkashfjhaskjfhaslfhj"));
                List<AcceptForm> acceptForms0=new ArrayList<>();acceptForms0.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));acceptForms0.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));acceptForms0.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));


                MessageAdapter adapter1=new MessageAdapter(messageForms);
                ((MainActivity) context).setRecyclerViewOrientation(commentsRecycler,LinearLayoutManager.VERTICAL);
                commentsRecycler.setAdapter(adapter1);

                RateStarsAdapter adapter=new RateStarsAdapter(form2.getRate());
                ((MainActivity) context).setRecyclerViewOrientation(recyclerView,LinearLayoutManager.VERTICAL);
                recyclerView.setAdapter(adapter);

                AcceptAdapter acceptAdapter0=new AcceptAdapter(acceptForms0);
                ((MainActivity) context).setRecyclerViewOrientation(accceptReycler0,LinearLayoutManager.HORIZONTAL);
                accceptReycler0.setAdapter(acceptAdapter0);


                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
