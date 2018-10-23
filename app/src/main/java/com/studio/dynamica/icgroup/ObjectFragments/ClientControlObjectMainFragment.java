package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AVRAdapter;
import com.studio.dynamica.icgroup.Adapters.ClientControlAdapter;
import com.studio.dynamica.icgroup.Adapters.RateStarsAdapter;
import com.studio.dynamica.icgroup.Forms.AVRForm;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.Forms.CheckListForm;
import com.studio.dynamica.icgroup.Forms.MessageWorkForm;
import com.studio.dynamica.icgroup.Forms.OlkForm;
import com.studio.dynamica.icgroup.Forms.svodkaRateForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientControlObjectMainFragment extends Fragment {
    int page=0;
    List<String> pages;
    View view;
    NumberPicker numberPicker, yearPicker;
    TextView pageInfo, addNewTextView,mainObjectTitle, clientControlInfoTextView, yearTextView, monthTextView,PercentageTextView;
    LinearLayout progressLayout, createNewLayout;
    ConstraintLayout rateLayout;
    ImageView left, right, yearImageView, monthImageView;
    RecyclerView recyclerView, rateRecyclerView;
    Calendar cal;
    String[] data = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    ClientControlAdapter OlkAdapter, svodkaAdapter, checkListAdapter;
    AVRAdapter avrAdapter;
    View.OnClickListener createOlkListener, createCheckListListener, svodkaListener;
    public ClientControlObjectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cal=Calendar.getInstance();
        cal.setTime(new Date());
        view=inflater.inflate(R.layout.fragment_client_control_object_main, container, false);

        createAllViews();
        setFonttypes();
        setAllListeners();
        PickerSettings();

        ((MainActivity)getActivity()).setRecyclerViewOrientation(rateRecyclerView,LinearLayoutManager.HORIZONTAL);
        rateRecyclerView.setAdapter(new RateStarsAdapter(true));

        List<Object> olkForms=new ArrayList<>();
        List<Object> checkListForms=new ArrayList<>();
        List<Object> svodkaRateForms=new ArrayList<>();

        List<AVRForm> avrForms=new ArrayList<>();
        List<MessageWorkForm> workForms=new ArrayList<>();
        List<AcceptForm> acceptForms=new ArrayList<>();

        olkForms.add(new OlkForm("","","",1));
        olkForms.add(new OlkForm("","","",1));
        olkForms.add(new OlkForm("","","",1));
        checkListForms.add(new CheckListForm("24 января 2018","Темирлан Алмасов",1));
        checkListForms.add(new CheckListForm("24 января 2018","Даурен Копбай",1));
        checkListForms.add(new CheckListForm("24 января 2018","Аян Тилигенов",1));
        svodkaRateForms.add(new svodkaRateForm("","","",1));
        svodkaRateForms.add(new svodkaRateForm("","","",1));
        svodkaRateForms.add(new svodkaRateForm("","","",1));

        acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","Куратор","Выполнено",true));acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","Куратор","Провалено",false));acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","Куратор","Выполнено",true));acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","Куратор","Выполнено",true));
        workForms.add(new MessageWorkForm("Тимур Сыздыков",false,5,"Ruby’s bundler, it is similar in spirit to those tools. While pip can install Python packages, Pipenv is recommended as it’s a higher"));workForms.add(new MessageWorkForm("Тимур Сыздыков",true,5,""));workForms.add(new MessageWorkForm("Тимур Сыздыков",false,5,""));workForms.add(new MessageWorkForm("Тимур Сыздыков",false,5,""));
        AVRForm avrForm=new AVRForm("02 Августа 2018",5,"ОПУ данного объекта","Тимур Сыздыков","куратор");avrForm.setAcceptForms(acceptForms);avrForm.setMessageWorkForms(workForms);
        AVRForm avrForm1=new AVRForm("02 Августа 2018",5,"ОПУ данного объекта","Тимур Сыздыков","куратор");avrForm1.setAcceptForms(acceptForms);avrForm1.setMessageWorkForms(workForms);
        AVRForm avrForm2=new AVRForm("02 Августа 2018",5,"ОПУ данного объекта","Тимур Сыздыков","куратор");avrForm2.setAcceptForms(acceptForms);avrForm2.setMessageWorkForms(workForms);
        avrForms.add(avrForm);avrForms.add(avrForm1);avrForms.add(avrForm2);

        OlkAdapter=new ClientControlAdapter(olkForms, 0);
        avrAdapter=new AVRAdapter(avrForms);
        checkListAdapter=new ClientControlAdapter(checkListForms,1);
        svodkaAdapter=new ClientControlAdapter(svodkaRateForms,2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(OlkAdapter);


        pages=new ArrayList<>();
        pages.add("Опросной лист клиента");
        pages.add("Акт выполненых работ");
        pages.add("Чек-лист проверки качества работы");
        pages.add("Сводка по результатам обзвона");

        View.OnClickListener click=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        };
        yearImageView.setOnClickListener(click);
        monthImageView.setOnClickListener(click);
        setDate();

        checkPage();

        return view;
    }
    private void PickerSettings(){
        numberPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        numberPicker.setDividerColorResource(android.R.color.transparent);
        yearPicker.setDividerColor(getContext().getResources().getColor( android.R.color.transparent));
        yearPicker.setDividerColorResource(android.R.color.transparent);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(cal.get(Calendar.MONTH)+1);
        int year=cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year-2);
        yearPicker.setMaxValue(year+1);
        yearPicker.setValue(year);
    }
    private void setDate(){
        monthTextView.setText(data[cal.get(Calendar.MONTH)]);
        yearTextView.setText(cal.get(Calendar.YEAR)+"");
    }
    private void click(){
        if(yearPicker.getVisibility()==View.GONE){
            yearImageView.setImageResource(R.drawable.ic_arrowup_green);
            monthImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearPicker.setVisibility(View.VISIBLE);
            numberPicker.setVisibility(View.VISIBLE);
            yearTextView.setVisibility(View.GONE);
            monthTextView.setVisibility(View.GONE);
        }
        else{
            yearImageView.setImageResource(R.drawable.ic_arrowdown_green);
            monthImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearPicker.setVisibility(View.GONE);
            numberPicker.setVisibility(View.GONE);
            yearTextView.setVisibility(View.VISIBLE);
            monthTextView.setVisibility(View.VISIBLE);
            cal.set(yearPicker.getValue(),numberPicker.getValue()-1,1);
            setDate();
        }
    }
    private void setFonttypes(){
        setTypeFace("demibold", pageInfo, addNewTextView, yearTextView, monthTextView,PercentageTextView);
        setTypeFace("light", clientControlInfoTextView);
        setTypeFace("it", mainObjectTitle);
        numberPicker.setTypeface(((MainActivity) getActivity()).getTypeFace("demibold"));
        yearPicker.setTypeface(((MainActivity) getActivity()).getTypeFace("demibold"));
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
    }
    private void createAllViews(){
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        clientControlInfoTextView=(TextView) view.findViewById(R.id.clientControlInfoTextView);
        addNewTextView=(TextView) view.findViewById(R.id.createNewTextView);
        pageInfo=(TextView) view.findViewById(R.id.pageInfoTextView);
        yearTextView=(TextView) view.findViewById(R.id.yearTextView);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        left=(ImageView) view.findViewById(R.id.ImageLeftView);
        right=(ImageView) view.findViewById(R.id.ImageRightView);
        yearImageView=(ImageView) view.findViewById(R.id.yearImageView);
        monthImageView=(ImageView) view.findViewById(R.id.monthImageView);
        progressLayout=(LinearLayout) view.findViewById(R.id.progressLayout);
        createNewLayout=(LinearLayout) view.findViewById(R.id.createNewLayout);
        rateLayout=(ConstraintLayout) view.findViewById(R.id.rateLayout);
        recyclerView=(RecyclerView) view.findViewById(R.id.CCRecyclerView);
        rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);

        numberPicker=(NumberPicker) view.findViewById(R.id.numberPicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
    }

    private void  setAllListeners(){
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(false);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(true);
            }
        });

        createOlkListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).setFragment(R.id.content_frame,new AddNewOlkFragment());
            }
        };
        createNewLayout.setOnClickListener(createOlkListener);

        createCheckListListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFragment(R.id.content_frame,new AddNewCheckListFragment());
            }
        };
        svodkaListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFragment(R.id.content_frame,new AddNewSvodkaFragment());
            }
        };
    }


    public void setPagePlus(boolean plus){
        if(plus)
            page=(page+1)%pages.size();
        else
            page=(page-1)%pages.size();
        if(page==-1){
            page=pages.size()-1;
        }
        checkPage();
    }
    public void checkPage(){
        pageInfo.setText(pages.get(page));
        String cr="Создать ";
        String s="Чек лист";
        switch (page){
            case 0:
                s="ОЛК";
                setProgressLayout();
                recyclerView.setAdapter(OlkAdapter);
                createNewLayout.setOnClickListener(createOlkListener);
                break;
            case 1:
                s="АВР";
                setProgressLayout();
                recyclerView.setAdapter(avrAdapter);
                createNewLayout.setOnClickListener(null);
                break;
            case 2:
                s="Чек лист";
                setProgressLayout();
                recyclerView.setAdapter(checkListAdapter);
                createNewLayout.setOnClickListener(createCheckListListener);
                break;
            case 3:
                s="сводку";
                setSvodkaRate();
                recyclerView.setAdapter(svodkaAdapter);
                createNewLayout.setOnClickListener(svodkaListener);
                break;
        }
        addNewSetText(cr+s);
    }
    public void addNewSetText(String s){
        addNewTextView.setText(s);
    }
    public void setSvodkaRate(){
        progressLayout.setVisibility(View.GONE);
        rateLayout.setVisibility(View.VISIBLE);
    }
    public void setProgressLayout(){
        progressLayout.setVisibility(View.VISIBLE);
        rateLayout.setVisibility(View.GONE);
    }
}
