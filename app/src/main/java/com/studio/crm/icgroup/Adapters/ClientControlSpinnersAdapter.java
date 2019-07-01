package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.SpinnerForm;
import com.studio.crm.icgroup.R;

import java.util.List;

public class ClientControlSpinnersAdapter extends RecyclerView.Adapter {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView rateTextView0;
        Spinner rateSpinner0;
        EditText commentEditText, nameEditText;
        FrameLayout nameEditTextLayout;
        int index=0;
        private myHolder(View view){
            super(view);
            rateTextView0=(TextView) view.findViewById(R.id.rateTextView0);
            rateSpinner0=(Spinner) view.findViewById(R.id.rateSpinner0);
            commentEditText=(EditText) view.findViewById(R.id.commentEditText);
            nameEditText=(EditText) view.findViewById(R.id.nameEditText);
            nameEditTextLayout=(FrameLayout) view.findViewById(R.id.nameEditTextLayout);
            nameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d("EDITED","EDITED");
                    list.get(index).setName(nameEditText.getText()+"");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            commentEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d("EDITED","EDITED");
                    list.get(index).setText(commentEditText.getText()+"");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            setTypeface("light",rateTextView0);
        }
        private void setInfo(final SpinnerForm form){
            index=list.indexOf(form);
            if(changeable){
                rateTextView0.setVisibility(View.GONE);
                nameEditTextLayout.setVisibility(View.VISIBLE);
            }
            else {
                rateTextView0.setVisibility(View.VISIBLE);
                nameEditTextLayout.setVisibility(View.GONE);
                rateTextView0.setText(form.getName());
            }

            String[] numbers={"1","2","3","4","5"};
            final ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(context,R.layout.rate_spinner_item,numbers){
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view=null;
                    view=super.getDropDownView(position, convertView, parent);
                    try {
                        setTypeface("demibold", ((TextView) view));
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    return view;
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view= super.getView(position, convertView, parent);
                    view.setBackgroundResource(R.drawable.green_circle_line);
                    setTypeface("demibold",((TextView)view));
                    list.get(list.indexOf(form)).setNum(position+1);
                    return view;
                }
            };
            rateSpinner0.setAdapter(spinnerAdapter);
            rateSpinner0.setSelection(form.getNum()-1);

        }
        private void setTypeface(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }
    }
    List<SpinnerForm> list;
    Context context;
    boolean changeable=false;
    public ClientControlSpinnersAdapter(List<SpinnerForm> forms){
        list=forms;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        final myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
        holder.rateSpinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.spinner_row,parent,false);
        return new myHolder(view);
    }
    public  void itemChanged(int position){
        notifyItemChanged(position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
