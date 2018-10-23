package com.studio.dynamica.icgroup.ExtraFragments;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.DaysAdapter;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DateChooseView extends FrameLayout{
    HashMap<Integer,DaysAdapter> adapters;
    int year;
    LinearLayout dateLayout, dateInfoLayout;
    ConstraintLayout fullDateLayout;
    NumberPicker numberPicker,yearPicker;
    TextView dateTextView;
    RecyclerView dayReycler;
    ImageView fullDateImage;
    int days;
    String[] data = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    Calendar cal;
    public DateChooseView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public DateChooseView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public DateChooseView(Context context){
        super(context);
        initView();
    }
    private void initView(){
        View view = inflate(getContext(), R.layout.datechoose_view, null);

        cal=Calendar.getInstance();
        cal.setTime(new Date());

        addView(view);
        createViews(view);
        setFonttypes();
        PickerSettings();

        fullDateLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dateClicked();
            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                checkDate();
            }
        });
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year=newVal;
                checkDate();
            }
        });

        ((MainActivity) getContext()).setRecyclerViewOrientation(dayReycler,LinearLayoutManager.HORIZONTAL);
        days=31;
         adapters=new HashMap<>();
         adapters.put(28,new DaysAdapter(28));
         adapters.put(29,new DaysAdapter(29));
         adapters.put(30,new DaysAdapter(30));
         adapters.put(31,new DaysAdapter(31));
        dayReycler.setAdapter(adapters.get(31));

        checkDate();
    }

    private void createViews(View view){
        dateLayout=(LinearLayout) view.findViewById(R.id.dateLayout);
        dateInfoLayout=(LinearLayout) view.findViewById(R.id.dateInfoLayout);
        fullDateLayout=(ConstraintLayout) view.findViewById(R.id.fullDateLayout);
        numberPicker=(com.shawnlin.numberpicker.NumberPicker) view.findViewById(R.id.numberPicker);
        yearPicker=(com.shawnlin.numberpicker.NumberPicker) view.findViewById(R.id.yearPicker);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);
        dayReycler=(RecyclerView) view.findViewById(R.id.dayRecycler);
        fullDateImage=(ImageView) view.findViewById(R.id.fullDateImageView);
    }
    private void setFonttypes(){
        dateTextView.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
        yearPicker.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
        numberPicker.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
    }
    private void PickerSettings(){
        numberPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        numberPicker.setDividerColorResource(android.R.color.transparent);
        yearPicker.setDividerColor(getContext().getResources().getColor( android.R.color.transparent));
        yearPicker.setDividerColorResource(android.R.color.transparent);


        numberPicker.setSelectedTextColor(getContext().getResources().getColor( R.color.colorPrimary));
        numberPicker.setSelectedTextColorResource(R.color.colorPrimary);
        yearPicker.setSelectedTextColor(getContext().getResources().getColor( R.color.darkgrey));
        yearPicker.setSelectedTextColorResource(R.color.darkgrey);


        numberPicker.setTextColor(getContext().getResources().getColor( R.color.greyy));
        numberPicker.setTextColorResource(R.color.greyy);
        yearPicker.setTextColor(getContext().getResources().getColor(R.color.light_grey));
        yearPicker.setTextColorResource(R.color.light_grey);


        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(cal.get(Calendar.MONTH)+1);
        year=cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year-2);
        yearPicker.setMaxValue(year+1);
        yearPicker.setValue(year);
    }
    public void dateClicked(){
        if(dateInfoLayout.getVisibility()==View.VISIBLE){
            dateInfoLayout.setVisibility(View.GONE);
            fullDateImage.setImageResource(R.drawable.ic_arrowdown_green);
            dateTextView.setVisibility(VISIBLE);
            yearPicker.setVisibility(GONE);
            dateTextView.setText(getDate());
        }
        else {
            dateInfoLayout.setVisibility(View.VISIBLE);
            fullDateImage.setImageResource(R.drawable.ic_arrowup_green);
            dateTextView.setVisibility(GONE);
            yearPicker.setVisibility(VISIBLE);
        }
    }
    private String getDate(){
        return (adapters.get(days).getClicked()+1)+" "+data[numberPicker.getValue()-1]+" "+yearPicker.getValue();
    }
    private void checkDate(){
        cal.set(year,numberPicker.getValue()-1,1);
        days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayReycler.setAdapter(adapters.get(days));
        Log.d("checkDate()",year+" "+(numberPicker.getValue()-1)+" "+days);
        dateTextView.setText(getDate());
    }
}
