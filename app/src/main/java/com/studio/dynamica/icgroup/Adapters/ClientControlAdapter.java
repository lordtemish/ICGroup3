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
        TextView wrapTextView;
        LinearLayout extraLayout;
        private OlkHolder(View v){
            super(v);
            acceptRecycler=(RecyclerView) v.findViewById(R.id.acceptRecyclerView);
            phonesRecyclerView=(RecyclerView) v.findViewById(R.id.phonesRecyclerView);
            extraLayout=(LinearLayout) v.findViewById(R.id.extraLayout);
            wrapTextView=(TextView) v.findViewById(R.id.wrapTextView);
            markLayout=(ConstraintLayout) v.findViewById(R.id.markLayout);
            commentsRecyclerView=(RecyclerView) v.findViewById(R.id.commentsRecyclerView);
            wrapTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicked();
                }
            });
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
        private checkListHolder(View v){
            super(v);
            wholeLayout=(LinearLayout) v.findViewById(R.id.wholeLayout);
        }
    }
    private class svodkaRateHolder extends RecyclerView.ViewHolder{
        RecyclerView rateStartRecycler, commentsRecyclerView, acceptRecyclerView;
        ImageView arrowImage;
        LinearLayout extraLayout;
        private svodkaRateHolder(View view){
            super(view);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            arrowImage=(ImageView) view.findViewById(R.id.arrowImageView);
            rateStartRecycler=(RecyclerView) view.findViewById(R.id.starsRecyclerView);
            commentsRecyclerView=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
            acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);

            arrowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClicked();
                }
            });
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

                RecyclerView commentsRecycle=holder.commentsRecyclerView;
                RecyclerView.LayoutManager manager0=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

                RecyclerView phonesRecycle=holder.phonesRecyclerView;
                RecyclerView.LayoutManager manager01=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

                RecyclerView accceptReycler=holder.acceptRecycler;
                RecyclerView.LayoutManager manager02=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);

                List<MessageForm> messageForm=new ArrayList<>();messageForm.add(new MessageForm("Fuck that shitasdlfhjkashfjkashfjhaskjfhaslfhj"));
                List<PhonesRowForm> rowForms=new ArrayList<>();rowForms.add(new PhonesRowForm(false,"qweqwe","123123123123"));
                List<AcceptForm> acceptForms=new ArrayList<>();acceptForms.add(new AcceptForm("","","","",true));acceptForms.add(new AcceptForm("","","","",true));acceptForms.add(new AcceptForm("","","","",true));

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
                List<AcceptForm> acceptForms0=new ArrayList<>();acceptForms0.add(new AcceptForm("","","","",true));acceptForms0.add(new AcceptForm("","","","",true));acceptForms0.add(new AcceptForm("","","","",true));


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
