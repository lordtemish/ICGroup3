package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewServiceFragment extends Fragment {
    int[][] layouts={{R.id.m00Layout,R.id.m01Layout,R.id.m02Layout,R.id.m03Layout,R.id.m04Layout,R.id.m05Layout,R.id.m06Layout}, {R.id.m10Layout,R.id.m11Layout,R.id.m12Layout,R.id.m13Layout,R.id.m14Layout,R.id.m15Layout,R.id.m16Layout}, {R.id.m20Layout,R.id.m21Layout,R.id.m22Layout,R.id.m23Layout,R.id.m24Layout,R.id.m25Layout,R.id.m26Layout}, {R.id.m30Layout,R.id.m31Layout,R.id.m32Layout,R.id.m33Layout,R.id.m34Layout,R.id.m35Layout,R.id.m36Layout}, {R.id.m40Layout,R.id.m41Layout,R.id.m42Layout,R.id.m43Layout,R.id.m44Layout,R.id.m45Layout,R.id.m46Layout}, {R.id.m50Layout,R.id.m51Layout,R.id.m52Layout,R.id.m53Layout,R.id.m54Layout,R.id.m55Layout,R.id.m56Layout}};
    int[] linearLayouts={R.id.m0Layout,R.id.m1Layout,R.id.m2Layout,R.id.m3Layout,R.id.m4Layout,R.id.m5Layout};
    int[][] texts={{R.id.m00TextView,R.id.m01TextView,R.id.m02TextView,R.id.m03TextView,R.id.m04TextView,R.id.m05TextView,R.id.m06TextView}, {R.id.m10TextView,R.id.m11TextView,R.id.m12TextView,R.id.m13TextView,R.id.m14TextView,R.id.m15TextView,R.id.m16TextView}, {R.id.m20TextView,R.id.m21TextView,R.id.m22TextView,R.id.m23TextView,R.id.m24TextView,R.id.m25TextView,R.id.m26TextView}, {R.id.m30TextView,R.id.m31TextView,R.id.m32TextView,R.id.m33TextView,R.id.m34TextView,R.id.m35TextView,R.id.m36TextView}, {R.id.m40TextView,R.id.m41TextView,R.id.m42TextView,R.id.m43TextView,R.id.m44TextView,R.id.m45TextView,R.id.m46TextView}, {R.id.m50TextView,R.id.m51TextView,R.id.m52TextView,R.id.m53TextView,R.id.m54TextView,R.id.m55TextView,R.id.m56TextView}};
    int[][] images={{R.id.m00ImageView,R.id.m01ImageView,R.id.m02ImageView,R.id.m03ImageView,R.id.m04ImageView,R.id.m05ImageView,R.id.m06ImageView}, {R.id.m10ImageView,R.id.m11ImageView,R.id.m12ImageView,R.id.m13ImageView,R.id.m14ImageView,R.id.m15ImageView,R.id.m16ImageView}, {R.id.m20ImageView,R.id.m21ImageView,R.id.m22ImageView,R.id.m23ImageView,R.id.m24ImageView,R.id.m25ImageView,R.id.m26ImageView}, {R.id.m30ImageView,R.id.m31ImageView,R.id.m32ImageView,R.id.m33ImageView,R.id.m34ImageView,R.id.m35ImageView,R.id.m36ImageView}, {R.id.m40ImageView,R.id.m41ImageView,R.id.m42ImageView,R.id.m43ImageView,R.id.m44ImageView,R.id.m45ImageView,R.id.m46ImageView}, {R.id.m50ImageView,R.id.m51ImageView,R.id.m52ImageView,R.id.m53ImageView,R.id.m54ImageView,R.id.m55ImageView,R.id.m56ImageView}};
    String[] months={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    List<List<ConstraintLayout>> calendarLayouts;
    List<LinearLayout> calendarMainLayouts;
    List<List<TextView>> calendarTexts;
    List<List<ImageView>> calendarImages;
    List<List<Calendar>> days;
    TextView monthTextView;
    ConstraintLayout monthLayout;
    FrameLayout commentLayout;
    EditText nameEditText, commentEditText;
    RadioGroup radioGroup;
    RadioButton todayRadio, dateRadio, mediaRadio;
    boolean media=false;
    int[][] choseindexes={{0,0},{0,0}};
     int click=0, clickx=0, clicky=0, xmax=0, ymax=0;
    Calendar cal;
    public AddNewServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_new_service, container, false);
        click=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        this.cal=new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1);
        commentEditText=(EditText) view.findViewById(R.id.commentEditText);
        commentLayout=(FrameLayout) view.findViewById(R.id.commentLayout);
        nameEditText=(EditText) view.findViewById(R.id.nameEditText);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        monthLayout=(ConstraintLayout) view.findViewById(R.id.monthLayout);
        setMonth(cal.get(Calendar.MONTH));
        radioGroup=(RadioGroup) view.findViewById(R.id.dateRadioGroup);
        mediaRadio=(RadioButton) view.findViewById(R.id.mediaRadioButton);
        mediaRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaClicked();
            }
        });
        todayRadio=(RadioButton) view.findViewById(R.id.radioButton);
        todayRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtons();
                todayRadio.setChecked(true);
            }
        });
        dateRadio=(RadioButton) view.findViewById(R.id.radioButton1);
        dateRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtons();
                dateRadio.setChecked(true);
            }
        });

        calendarImages=new ArrayList<>();calendarLayouts=new ArrayList<>(); calendarTexts=new ArrayList<>();calendarMainLayouts=new ArrayList<>();days=new ArrayList<>();
        for (int i=0;i<6;i++){
            calendarImages.add(new ArrayList<ImageView>());
            calendarTexts.add(new ArrayList<TextView>());
            calendarLayouts.add(new ArrayList<ConstraintLayout>());
            calendarMainLayouts.add((LinearLayout) view.findViewById(linearLayouts[i]));
            for(int j=0;j<7;j++){
                calendarImages.get(i).add((ImageView) view.findViewById(images[i][j]));
                calendarTexts.get(i).add((TextView) view.findViewById(texts[i][j]));
                calendarLayouts.get(i).add((ConstraintLayout) view.findViewById(layouts[i][j]));
                calendarLayouts.get(i).get(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicked(v);
                    }
                });
            }
        }
        clearAllDays();
        setDay();
        setEmptyDays();
        return view;
    }
    public void setMonth(int m){
        monthTextView.setText(months[m]+" "+cal.get(Calendar.YEAR));
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
    }
    public void clearAllDays(){
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
        Calendar cal=this.cal;
        for(int i=weekday-1;i>=0;i--){
            cal.add(Calendar.DATE,-1);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            TextView t=calendarTexts.get(0).get(i);
            t.setText(day+"");
            ConstraintLayout l=calendarLayouts.get(0).get(i);
            l.setOnClickListener(null);
        }
        cal=days.get(xmax).get(ymax);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),max);
        for(int i=ymax+1;i<7;i++){
            int day=cal.get(Calendar.DAY_OF_MONTH);
            TextView t=calendarTexts.get(xmax).get(i);
            t.setText(day+"");
            ConstraintLayout l=calendarLayouts.get(xmax).get(i);
            l.setOnClickListener(null);
            cal.add(Calendar.DATE,+1);
        }
    }
    public void setDay(){
        int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weekday=(cal.get(Calendar.DAY_OF_WEEK)+5)%7;
        for(int i=0;i<6;i++){
            xmax=i;
            if(i==5){
                calendarMainLayouts.get(i).setVisibility(View.VISIBLE);
            }
            for(int j=weekday;j<7;j++){
                ymax=j;
                int day=cal.get(Calendar.DAY_OF_MONTH);
                days.get(i).set(j,cal);
                ConstraintLayout l=calendarLayouts.get(i).get(j);
                TextView t=calendarTexts.get(i).get(j);
                t.setText(day+"");
                ImageView iv=calendarImages.get(i).get(j);
                setSimple(l,t,iv);
                max--;
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
        layout.setBackground(getActivity().getResources().getDrawable(R.drawable.calendar_simple_dr));

        textView.setTextColor(getActivity().getResources().getColor(R.color.black));
        iv.setVisibility(View.GONE);
    }
    public void setGrey(ConstraintLayout layout,TextView textView,ImageView iv){
        layout.setBackground(getActivity().getResources().getDrawable(R.drawable.calendar_grey_dr));
        textView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        iv.setVisibility(View.GONE);
    }
    public void setGreenActive(ConstraintLayout layout,TextView textView, ImageView iv){
        layout.setBackground(getActivity().getResources().getDrawable(R.drawable.calendar_greenactive_dr));
        textView.setTextColor(getActivity().getResources().getColor(R.color.white));
        iv.setVisibility(View.VISIBLE);
    }
    public void setGreenInside(ConstraintLayout layout,TextView textView,ImageView iv){
        layout.setBackground(getActivity().getResources().getDrawable(R.drawable.calendar_greeninside_dr));
        textView.setTextColor(getActivity().getResources().getColor(R.color.white));
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
    public void clearRadioButtons(){
        dateRadio.setChecked(false);
        todayRadio.setChecked(false);
    }
    public void mediaClicked(){
        if(media){
            mediaRadio.setChecked(false);
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.greyy));
            commentEditText.setFocusableInTouchMode(false);
            commentEditText.setFocusable(false);
            commentLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_line));
            media=false;
        }
        else{
            mediaRadio.setChecked(true);
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.black));
            commentEditText.setFocusableInTouchMode(true);
            commentEditText.setFocusable(true);
            commentLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.black_line));
            media=true;
        }

    }
}
