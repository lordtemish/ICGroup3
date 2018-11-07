package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class CheckListBoxRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CheckListBoxRowForm> list;
    boolean text;
    Context context;
    private class myHolder extends RecyclerView.ViewHolder{
        ConstraintLayout wholeLayout;
        TextView rateTextView, nameTextView, infoTextView;
        Spinner spinner;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
            spinner=(Spinner) view.findViewById(R.id.rateSpinner);
            rateTextView=(TextView) view.findViewById(R.id.rateTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            infoTextView=(TextView) view.findViewById(R.id.infoTextView);

            setFonttype();
        }
        private void setFonttype(){
            setTypeFace("it");
            setTypeFace("demibold", rateTextView, nameTextView);
            setTypeFace("light", infoTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface((((MainActivity)context).getTypeFace(s)));
            }
        }
        private void setInfo(final CheckListBoxRowForm form){
            Log.d("textBool",text+"");
            nameTextView.setText(form.getName());
            infoTextView.setText(form.getInfo());
            if(text){
                rateTextView.setText(form.getRate()+"");
                spinner.setVisibility(View.GONE);
                rateTextView.setVisibility(View.VISIBLE);
            }
            else {
                spinnerSet();
                spinner.setVisibility(View.VISIBLE);
                rateTextView.setVisibility(View.GONE);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        list.get(list.indexOf(form)).setRate(i+1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
        private void spinnerSet(){
            String[] numbers={"1","2","3","4","5"};
            ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(context,R.layout.rate_spinner_item,numbers){
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view=null;
                    view=super.getDropDownView(position, convertView, parent);
                    setTypeFace("demibold",(TextView)view);
                    return view;
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view= super.getView(position, convertView, parent);
                    view.setBackgroundResource(R.drawable.green_circle_line);
                    setTypeFace("demibold",(TextView)view);
                    return view;
                }
            };
            spinner.setAdapter(spinnerAdapter);

        }
    }
    public CheckListBoxRowAdapter(List<CheckListBoxRowForm> forms){
        list=forms;
        text=false;
    }
    public CheckListBoxRowAdapter(List<CheckListBoxRowForm> forms, boolean t){
        list=forms;
        text=t;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.checklistboxrow_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        final myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                list.get(position).setRate(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (position==getItemCount()-1){
            holder.wholeLayout.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
