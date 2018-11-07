package com.studio.dynamica.icgroup.ExtraFragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

import java.util.Calendar;
import java.util.Date;

public class TimePickView extends FrameLayout {
    TextView timeTextView,  makeTextView;
    ImageView plusImageView, minusImageView;
    public int time=0;
    Calendar calendar;
    public TimePickView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public TimePickView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public TimePickView(Context context){
        super(context);
        initView();
    }
    private void initView(){
        View view = inflate(getContext(), R.layout.timepick_view, null);
        addView(view);
        createViews(view);
        setFonttypes();
        minusImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked(-1);
            }
        });
        plusImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked(+1);
            }
        });
        setTime();
    }
    private void clicked(int a){
        time+=a;
        if(time<0){
            time=23;
        }
        else if(time>23){
            time=0;
        }
        setTime();
    }
    private void setTime(){
        String ti="";
        if(time<10){
            ti="0"+time;
        }
        else
            ti+=time;
        timeTextView.setText(ti+":00");
    }
    public Calendar getChose(){
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,time);
        return calendar;
    }
    private void setFonttypes(){
        makeTextView.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
        timeTextView.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
    }
    private void createViews(View view) {
        calendar=Calendar.getInstance();calendar.setTime(new Date());
        timeTextView=(TextView) view.findViewById(R.id.timeTextView);
        makeTextView=(TextView) view.findViewById(R.id.makeTextView);
        plusImageView=(ImageView) view.findViewById(R.id.plusImageView);
        minusImageView=(ImageView) view.findViewById(R.id.minusImageView);
    }
}
