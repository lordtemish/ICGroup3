package com.studio.dynamica.icgroup.ExtraFragments;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarView extends FrameLayout{
    int[][] layouts={{R.id.m00Layout,R.id.m01Layout,R.id.m02Layout,R.id.m03Layout,R.id.m04Layout,R.id.m05Layout,R.id.m06Layout}, {R.id.m10Layout,R.id.m11Layout,R.id.m12Layout,R.id.m13Layout,R.id.m14Layout,R.id.m15Layout,R.id.m16Layout}, {R.id.m20Layout,R.id.m21Layout,R.id.m22Layout,R.id.m23Layout,R.id.m24Layout,R.id.m25Layout,R.id.m26Layout}, {R.id.m30Layout,R.id.m31Layout,R.id.m32Layout,R.id.m33Layout,R.id.m34Layout,R.id.m35Layout,R.id.m36Layout}, {R.id.m40Layout,R.id.m41Layout,R.id.m42Layout,R.id.m43Layout,R.id.m44Layout,R.id.m45Layout,R.id.m46Layout}, {R.id.m50Layout,R.id.m51Layout,R.id.m52Layout,R.id.m53Layout,R.id.m54Layout,R.id.m55Layout,R.id.m56Layout}};
    int[] linearLayouts={R.id.m0Layout,R.id.m1Layout,R.id.m2Layout,R.id.m3Layout,R.id.m4Layout,R.id.m5Layout};
    int[] weekDaysIds={R.id.weekDay1TextView,R.id.weekDay2TextView,R.id.weekDay3TextView,R.id.weekDay4TextView,R.id.weekDay5TextView,R.id.weekDay6TextView,R.id.weekDay7TextView};
    int[][] texts={{R.id.m00TextView,R.id.m01TextView,R.id.m02TextView,R.id.m03TextView,R.id.m04TextView,R.id.m05TextView,R.id.m06TextView}, {R.id.m10TextView,R.id.m11TextView,R.id.m12TextView,R.id.m13TextView,R.id.m14TextView,R.id.m15TextView,R.id.m16TextView}, {R.id.m20TextView,R.id.m21TextView,R.id.m22TextView,R.id.m23TextView,R.id.m24TextView,R.id.m25TextView,R.id.m26TextView}, {R.id.m30TextView,R.id.m31TextView,R.id.m32TextView,R.id.m33TextView,R.id.m34TextView,R.id.m35TextView,R.id.m36TextView}, {R.id.m40TextView,R.id.m41TextView,R.id.m42TextView,R.id.m43TextView,R.id.m44TextView,R.id.m45TextView,R.id.m46TextView}, {R.id.m50TextView,R.id.m51TextView,R.id.m52TextView,R.id.m53TextView,R.id.m54TextView,R.id.m55TextView,R.id.m56TextView}};
    int[][] images={{R.id.m00ImageView,R.id.m01ImageView,R.id.m02ImageView,R.id.m03ImageView,R.id.m04ImageView,R.id.m05ImageView,R.id.m06ImageView}, {R.id.m10ImageView,R.id.m11ImageView,R.id.m12ImageView,R.id.m13ImageView,R.id.m14ImageView,R.id.m15ImageView,R.id.m16ImageView}, {R.id.m20ImageView,R.id.m21ImageView,R.id.m22ImageView,R.id.m23ImageView,R.id.m24ImageView,R.id.m25ImageView,R.id.m26ImageView}, {R.id.m30ImageView,R.id.m31ImageView,R.id.m32ImageView,R.id.m33ImageView,R.id.m34ImageView,R.id.m35ImageView,R.id.m36ImageView}, {R.id.m40ImageView,R.id.m41ImageView,R.id.m42ImageView,R.id.m43ImageView,R.id.m44ImageView,R.id.m45ImageView,R.id.m46ImageView}, {R.id.m50ImageView,R.id.m51ImageView,R.id.m52ImageView,R.id.m53ImageView,R.id.m54ImageView,R.id.m55ImageView,R.id.m56ImageView}};
    String[] months={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    List<List<ConstraintLayout>> calendarLayouts;
    List<LinearLayout> calendarMainLayouts;
    List<List<TextView>> calendarTexts;
    List<List<ImageView>> calendarImages;
    List<List<Calendar>> days;
    List<TextView> weekDays;
    ImageView monthImageView;
    TextView monthTextView;
    ConstraintLayout monthLayout;
    int i0=0,i1=0;
    NumberPicker monthPicker, yearPicker;
    boolean dateChange=false;

    int[][] choseindexes={{0,0},{0,0}};
    int click=0, clickx=0, clicky=0, xmax=0, ymax=0;
    Calendar cal, cal2;

    public int getClick() {
        return click;
    }

    public Calendar getChose(){
        if(click==2){
            return days.get(choseindexes[1][0]).get(choseindexes[1][1]);
        }
        return null;
    }
    public CalendarView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public CalendarView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public CalendarView(Context context){
        super(context);
        initView();
    }
    private void initView(){
        View view = inflate(getContext(), R.layout.calendar_view, null);
        addView(view);
        createViews(view);
        setFonttypes();
        timeSettings();
        PickerSettings();

        changeMonth();
    }
    private void createViews(View view){
        calendarImages=new ArrayList<>();
        calendarLayouts=new ArrayList<>();
        calendarMainLayouts=new ArrayList<>();
        calendarTexts=new ArrayList<>();
        weekDays=new ArrayList<>();
        days=new ArrayList<>();
        monthImageView=(ImageView) view.findViewById(R.id.monthImageView);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        monthLayout=(ConstraintLayout) view.findViewById(R.id.monthLayout);
        monthLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChangeClick();
            }
        });
        monthPicker=(NumberPicker) view.findViewById(R.id.monthPicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        for(int i=0;i<7;i++){
            weekDays.add((TextView) view.findViewById(weekDaysIds[i]));
        }
        for (int i=0;i<6;i++){
            calendarImages.add(new ArrayList<ImageView>());
            calendarTexts.add(new ArrayList<TextView>());
            calendarLayouts.add(new ArrayList<ConstraintLayout>());
            calendarMainLayouts.add((LinearLayout) view.findViewById(linearLayouts[i]));
            for(int j=0;j<7;j++){
                calendarImages.get(i).add((ImageView) view.findViewById(images[i][j]));
                calendarTexts.get(i).add((TextView) view.findViewById(texts[i][j]));
                calendarLayouts.get(i).add((ConstraintLayout) view.findViewById(layouts[i][j]));

            }
        }
    }
    private void setFonttypes(){
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                calendarTexts.get(i).get(j).setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
            }
        }
        for(int i=0;i<7;i++){
            if(i>=5){
                weekDays.get(i).setTypeface(((MainActivity)getContext()).getTypeFace("light"));
            }
            else{
                weekDays.get(i).setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
            }
        }
        monthTextView.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
        monthPicker.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
        yearPicker.setTypeface(((MainActivity)getContext()).getTypeFace("demibold"));
    }
    private void PickerSettings(){
        monthPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        monthPicker.setDividerColorResource(android.R.color.transparent);
        yearPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        yearPicker.setDividerColorResource(android.R.color.transparent);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(months.length-1);
        monthPicker.setDisplayedValues(months);
        monthPicker.setValue(cal.get(Calendar.MONTH));
        yearPicker.setMinValue(2017);
        yearPicker.setMaxValue(2020);
        yearPicker.setValue(cal.get(Calendar.YEAR));
    }

    private void dateChangeClick(){
        dateChange=!dateChange;
        dateClick();
    }
    private void dateClick(){
        if(dateChange){
            monthLayout.setMinHeight(90);
            monthImageView.setImageResource(R.drawable.ic_arrowup_green);
            monthTextView.setVisibility(GONE);
            monthPicker.setVisibility(VISIBLE);
            yearPicker.setVisibility(VISIBLE);
        }
        else{
            monthLayout.setMinHeight(50);
            monthImageView.setImageResource(R.drawable.ic_arrowdown_green);
            monthTextView.setVisibility(VISIBLE);
            monthPicker.setVisibility(GONE);
            yearPicker.setVisibility(GONE);
            dateCheck();
        }
    }
    private void dateCheck(){
        changeDate(monthPicker.getValue(),yearPicker.getValue());
    }

    private void timeSettings(){
        click=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        this.cal=new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1);
        cal2=Calendar.getInstance();cal2.setTime(new Date());
        setMonth(this.cal.get(Calendar.MONTH));
    }
    public void setMonth(int m){
        monthTextView.setText(months[m]+" "+cal.get(Calendar.YEAR));
    }
    private void changeMonth(int m){
        cal.set(Calendar.MONTH,m);

        setMonth(m);
        clearAllDays();
        setDay();
        setEmptyDays();
        Log.d("Month check",cal.get(Calendar.MONTH)+"");
    }

    private void changeMonth(){

        clearAllDays();
        setDay();
        setEmptyDays();
        Log.d("Month check",cal.get(Calendar.MONTH)+"");
    }
    private void changeDate(int m, int year){
        cal.set(year,m,1);

        setMonth(m);
        clearAllDays();
        setDay();
        setEmptyDays();
        Log.d("Month check",cal.get(Calendar.MONTH)+"");
    }

    public void clicked(View view){
        int x=0, y=0;
        for(int i=0;i<6;i++){
            if(calendarLayouts.get(i).contains(view)){
                x=i;
                y=calendarLayouts.get(i).indexOf(view);
            }
        }
        clicked(x,y);
    }
    public void clicked(int x, int y){
        switch (click){
            case 0:
                setGreenActive(calendarLayouts.get(x).get(y),calendarTexts.get(x).get(y),calendarImages.get(x).get(y));
                clickx=x;
                clicky=y;
                choseindexes[0][0]=x;
                choseindexes[0][1]=y;
                break;
            case 1:
                setGreenActive(calendarLayouts.get(x).get(y),calendarTexts.get(x).get(y),calendarImages.get(x).get(y));
                setGreenInside(clickx, clicky, x, y);
                choseindexes[1][0]=x;
                choseindexes[1][1]=y;
                break;
            case 2:
                int x1=choseindexes[0][0],y1=choseindexes[0][1],x2=choseindexes[1][0],y2=choseindexes[1][1];
                if((x1>x2) || (x1==x2 && y1>=y2)){
                    setSimple(x1,y1);
                    setSimple(x2,y2);
                }
                else {
                    setSimple(choseindexes[0][0], choseindexes[0][1], choseindexes[1][0], choseindexes[1][1]);
                }
                break;
        }
        click++;
        click=click%3;
        if(click==0){
            clicked(i0,i1);
        }
    }
    public void clearAllDays(){
        click=0;
        for(int i=0;i<6;i++){
            days.add(new ArrayList<Calendar>());
            for(int j=0;j<7;j++){
                setGrey(calendarLayouts.get(i).get(j),calendarTexts.get(i).get(j), calendarImages.get(i).get(j));
                days.get(i).add(Calendar.getInstance());
            }
        }
    }
    public void setEmptyDays(){
        int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weekday=(cal.get(Calendar.DAY_OF_WEEK)+5)%7;
        Calendar cal=Calendar.getInstance();
        cal.setTime(this.cal.getTime());
        for(int i=weekday-1;i>=0;i--){
            cal.add(Calendar.DATE,-1);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            TextView t=calendarTexts.get(0).get(i);
            t.setText(day+"");
            ConstraintLayout l=calendarLayouts.get(0).get(i);
            Log.d("Date BChecker", day+"");
            l.setOnClickListener(null);
        }
        cal.setTime(this.cal.getTime());
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),max);
        for(int i=ymax+1;i<7;i++){
            cal.add(Calendar.DATE,+1);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            TextView t=calendarTexts.get(xmax).get(i);
            t.setText(day+"");
            ConstraintLayout l=calendarLayouts.get(xmax).get(i);
            Log.d("Date Checker", day+" "+cal.get(Calendar.MONTH));
            l.setOnClickListener(null);
        }
    }
    public void setDay(){
        int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weekday=(cal.get(Calendar.DAY_OF_WEEK)+5)%7;
        if(cal.get(Calendar.MONTH)!=cal2.get(Calendar.MONTH)){
            i1=0;i0=0;
        }
        for(int i=0;i<6;i++){
            xmax=i;
            if(i==5){
                calendarMainLayouts.get(i).setVisibility(View.VISIBLE);
            }
            else if(i==4){
                calendarMainLayouts.get(5).setVisibility(View.GONE);
            }
            for(int j=weekday;j<7;j++){
                calendarLayouts.get(i).get(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicked(v);
                    }
                });

                ymax=j;
                int day=cal.get(Calendar.DAY_OF_MONTH);
                if(cal.get(Calendar.DAY_OF_YEAR)==cal2.get(Calendar.DAY_OF_YEAR) && cal.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)){
                    click=0;
                    i0=i;i1=j;
                }
                days.get(i).get(j).setTime(cal.getTime());
                ConstraintLayout l=calendarLayouts.get(i).get(j);
                TextView t=calendarTexts.get(i).get(j);
                t.setText(day+"");
                ImageView iv=calendarImages.get(i).get(j);
                setSimple(l,t,iv);
                max--;
                Log.d("setDay",day+"");
                if(max==0){
                    break;
                }
                cal.add(Calendar.DATE,+1);
            }
            if(max==0){
                break;
            }
            weekday=0;
        }
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1);
        clicked(i0,i1);
    }
    public void setSimple(int x1, int y1, int x2, int y2){
        int x=x1;
        int y=y1;

        for(int i=x;i<=x2;i++){
            int last;
            if(i==x2)
                last=y2;
            else{
                last=6;
            }
            for(int j=y;j<=last;j++){
                ConstraintLayout layout=calendarLayouts.get(i).get(j);
                ImageView imageView=calendarImages.get(i).get(j);
                TextView textView=calendarTexts.get(i).get(j);
                setSimple(layout,textView,imageView);
            }
            y=0;
        }
    }
    public void setSimple(int x, int y){
        setSimple(calendarLayouts.get(x).get(y),calendarTexts.get(x).get(y),calendarImages.get(x).get(y));
    }
    public void setSimple(ConstraintLayout layout, TextView textView,ImageView iv){
        layout.setBackgroundResource((R.drawable.calendar_simple_dr));

        textView.setTextColor(getResources().getColor(R.color.black));
        iv.setVisibility(View.GONE);
    }
    public void setGrey(ConstraintLayout layout,TextView textView,ImageView iv){
        layout.setBackgroundResource((R.drawable.calendar_grey_dr));
        textView.setTextColor(getResources().getColor(R.color.greyy));
        iv.setVisibility(View.GONE);
    }
    public void setGreenActive(ConstraintLayout layout,TextView textView, ImageView iv){
        layout.setBackgroundResource((R.drawable.calendar_greenactive_dr));
        textView.setTextColor(getResources().getColor(R.color.white));
        iv.setVisibility(View.VISIBLE);
    }
    public void setGreenInside(ConstraintLayout layout,TextView textView,ImageView iv){
        layout.setBackgroundResource((R.drawable.calendar_greeninside_dr));
        textView.setTextColor(getResources().getColor(R.color.white));
        iv.setVisibility(View.GONE);
    }
    public void setGreenInside(int x1, int y1, int x2, int y2){
        int x=x1;
        int y=y1+1;

        for(int i=x;i<=x2;i++){
            int last;
            if(i==x2)
                last=y2;
            else{
                last=7;
            }
            for(int j=y;j<last;j++){
                ConstraintLayout layout=calendarLayouts.get(i).get(j);
                ImageView imageView=calendarImages.get(i).get(j);
                TextView textView=calendarTexts.get(i).get(j);
                setGreenInside(layout,textView,imageView);
            }
            y=0;
        }
    }
}
