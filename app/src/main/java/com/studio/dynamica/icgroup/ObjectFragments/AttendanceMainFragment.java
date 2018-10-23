package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AttendanceAdapter;
import com.studio.dynamica.icgroup.Forms.AttendanceRowForm;
import com.studio.dynamica.icgroup.Forms.AttendanceRowItemForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceMainFragment extends Fragment {
    FrameLayout todayFrame;
    RecyclerView recyclerView;
    FrameLayout chooseLayout;
    NumberPicker datePicker, yearPicker;
    TextView mainObjectTitle, yearTextView, monthTextView;
    ImageView dateArrowImageView, yearArrowImageView, leftCalArrow, rightCalArrow;
    Calendar cal, cal2;
    List<TextView> calTextViews;
    boolean swiped=false;
    String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"}
        , weeks={"Пн","Вт","Ср","Чт","Пт","Сб","Вс"}
    ;
    AttendanceAdapter attendanceAdapter;
    public AttendanceMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cal=Calendar.getInstance();
        cal2=Calendar.getInstance();
        cal.setTime(new Date());
        View view = inflater.inflate(R.layout.fragment_attendance_main, container, false);
        createViews(view);
        chooseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLayout.setVisibility(View.GONE);
            }
        });

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        List<AttendanceRowForm> rowForms = new ArrayList<>();
        List<AttendanceRowItemForm> itemForms = new ArrayList<>();
        final int d=25;
        AttendanceRowItemForm itemForm=new AttendanceRowItemForm(d,true, false, false);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseLayout(d);
            }
        };
        itemForms.add(itemForm);
        itemForms.add(new AttendanceRowItemForm(26,true, false, false));
        itemForms.add(new AttendanceRowItemForm(27,false, true, true));
        itemForms.add(new AttendanceRowItemForm(28,false, true, false));
        itemForms.add(new AttendanceRowItemForm(29));
        AttendanceRowForm attendanceRowForm=new AttendanceRowForm("1","Темирлан", itemForms, 5, 7);
        attendanceRowForm.setListener(listener);
        rowForms.add(attendanceRowForm);
        rowForms.add(new AttendanceRowForm("2","Темирлан Алмасов", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("3","Темирлан Алмасов Даулетович", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("4","Надира Рыскулова", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("5","Рыскулова Надира", itemForms, 5, 7));


        attendanceAdapter = new AttendanceAdapter(rowForms);
        recyclerView.setAdapter(attendanceAdapter);

        pickerSettings();
        setDate();
        setFonttypes();
        setListeners();

        chooseLayout.setVisibility(View.GONE);

        return view;
    }
    public void setChooseLayout(int day){
        chooseLayout.setVisibility(View.VISIBLE);

    }
    private void onSwipe(){
        swiped=true;
    }
    private void pickerSettings() {
        datePicker.setMinValue(1);
        datePicker.setMaxValue(months.length);
        datePicker.setDisplayedValues(months);
        datePicker.setValue(9);

        yearPicker.setMinValue(cal.get(Calendar.YEAR)-2);
        yearPicker.setMaxValue(cal.get(Calendar.YEAR)+1);
        yearPicker.setValue(2018);
    }
    private void dateChange(boolean a){
        if(a){
            cal.add(Calendar.DAY_OF_YEAR,+5);
        }
        else {
            cal.add(Calendar.DAY_OF_YEAR,-5);
        }
        setDate();
    }
    private void setDate(){

        cal2.setTime(new Date());
        if(cal.getTimeInMillis()>cal2.getTimeInMillis()){
            cal.setTime(cal2.getTime());
        }
        if(cal2.get(Calendar.DAY_OF_YEAR)==cal.get((Calendar.DAY_OF_YEAR))){
            setToday(true);
            attendanceAdapter.setToday(true);
            attendanceAdapter.notifyDataSetChanged();
        }
        else{
            setToday(false);
            attendanceAdapter.setToday(false);
            attendanceAdapter.notifyDataSetChanged();
        }
        datePicker.setValue(cal.get(Calendar.MONTH)+1);
        yearPicker.setValue(cal.get(Calendar.YEAR));
        monthTextView.setText(months[datePicker.getValue()-1]);
        yearTextView.setText(yearPicker.getValue()+"");

        cal.add(Calendar.DAY_OF_YEAR,1);
        for(int i=4;i>=0;i--){
            int day=cal.get(Calendar.DAY_OF_WEEK)-2;
            if(day==-1){
                day=6;
            }
            calTextViews.get(i).setText(cal.get(Calendar.DAY_OF_MONTH)+"\n"+(weeks[(day)%7]));
//            Log.d("day"+i,((cal.get(Calendar.DAY_OF_WEEK)-2)%7)+" ");
            cal.add(Calendar.DAY_OF_YEAR,-1);
        }
        cal.add(Calendar.DAY_OF_YEAR,+4);
    }
    private void dateClick(){
        if(datePicker.getVisibility()!=View.VISIBLE){
            dateArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearPicker.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
                monthTextView.setVisibility(View.GONE);
                yearTextView.setVisibility(View.GONE);
        }
        else {
            dateArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearPicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            monthTextView.setVisibility(View.VISIBLE);
            yearTextView.setVisibility(View.VISIBLE);
            if(swiped){
                cal.set(yearPicker.getValue(),datePicker.getValue(),1);
                cal.set(yearPicker.getValue(),datePicker.getValue(),cal.getMaximum(Calendar.DAY_OF_MONTH));
                setDate();
                swiped=false;
            }
        }
    }

    private void setListeners(){
        View.OnClickListener dateLi=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dateClick();
            }
        };
        yearArrowImageView.setOnClickListener(dateLi);
        dateArrowImageView.setOnClickListener(dateLi);
        monthTextView.setOnClickListener(dateLi);
        yearTextView.setOnClickListener(dateLi);

        leftCalArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange(false);
            }
        });
        rightCalArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange(true);
            }
        });

        NumberPicker.OnValueChangeListener swipe=new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                onSwipe();
            }
        };
        datePicker.setOnValueChangedListener(swipe);
        yearPicker.setOnValueChangedListener(swipe);
    }
    private void setToday(boolean f){
        if(f){
            calTextViews.get(3).setBackgroundResource(R.drawable.green_circle);
            calTextViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.white));
            todayFrame.setVisibility(View.VISIBLE);
        }
        else {
            calTextViews.get(3).setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
            calTextViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.black));
            todayFrame.setVisibility(View.GONE);
        }
    }
    private void createViews(View view) {
        chooseLayout=(FrameLayout) view.findViewById(R.id.chooseLayout);
        datePicker = (NumberPicker) view.findViewById(R.id.datePicker);
        yearPicker = (NumberPicker) view.findViewById(R.id.yearPicker);
        recyclerView = (RecyclerView) view.findViewById(R.id.mainRecyclerView);
        mainObjectTitle = (TextView) view.findViewById(R.id.mainObjectTitle);
        dateArrowImageView = (ImageView) view.findViewById(R.id.dateArrowImageView);
        yearArrowImageView = (ImageView) view.findViewById(R.id.yearArrowImageView);
        leftCalArrow = (ImageView) view.findViewById(R.id.leftCalArrow);
        rightCalArrow = (ImageView) view.findViewById(R.id.rightCalArrow);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        yearTextView=(TextView) view.findViewById(R.id.yearTextView);

        calTextViews=new ArrayList<>();
        calTextViews.add((TextView) view.findViewById(R.id.f1CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f2CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f3CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f4CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f5CalTextView));
        todayFrame=(FrameLayout) view.findViewById(R.id.todayFrame);

    }
    private void setFonttypes(){
        setTypeFace("demibold");
        setTypeFace("light");
        setTypeFace("it", mainObjectTitle);
    }
    private void setTypeFace(String s, TextView... textViews) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
    }
}