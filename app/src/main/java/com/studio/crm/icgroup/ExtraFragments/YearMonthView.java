package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.studio.crm.icgroup.Forms.OneCallBack;
import com.studio.crm.icgroup.R;

import java.util.Calendar;
import java.util.Date;

public class YearMonthView extends FrameLayout {
    public NumberPicker yearPicker, monthPicker;
    String[] months={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    int yearMid=2019;
    TextView primBut;
    OneCallBack callBack;
    public YearMonthView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public YearMonthView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public YearMonthView(Context context){
        super(context);
        initView();
    }
    private void initView() {
        View view = inflate(getContext(), R.layout.yearmonth_view, null);
        addView(view);
        createViews(view);
    }
    private void createViews(View view){
        yearPicker=(NumberPicker)view.findViewById(R.id.yearPicker);
        monthPicker=(NumberPicker)view.findViewById(R.id.monthPicker);
        primBut=(TextView)view.findViewById(R.id.primBut);

        primBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.callBackCal();
            }
        });
    }
    public void getInfo(){
        Log.d("INGOFOOFO",monthPicker.getValue()+" "+yearPicker.getValue());
    }
    public void setPickers(Calendar cal){
        monthPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        monthPicker.setDividerColorResource(android.R.color.transparent);
        yearPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        yearPicker.setDividerColorResource(android.R.color.transparent);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(months.length-1);
        monthPicker.setDisplayedValues(months);
        monthPicker.setValue(cal.get(Calendar.MONTH));
        yearPicker.setMinValue(cal.get(Calendar.YEAR)-6);
        yearPicker.setMaxValue(cal.get(Calendar.YEAR)+2);
        yearPicker.setValue(cal.get(Calendar.YEAR));
    }
    public void setCallBack(OneCallBack call){
        callBack=call;
    }
}
