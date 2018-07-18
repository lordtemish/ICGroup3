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
import android.widget.NumberPicker;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Adapters.DaysAdapter;
import com.studio.dynamica.icgroup.Adapters.TechnoMapAdapter;
import com.studio.dynamica.icgroup.Forms.TechnoMapForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechnoMapFragment extends Fragment {
    List<String> pages;
    ConstraintLayout fullDateLayout;
    LinearLayout infoDateLayout;
    ImageView fullDateImage, rightPage, leftPage;
    TextView pageInfo;
    com.shawnlin.numberpicker.NumberPicker numberPicker, yearPicker;
    RecyclerView dayReycler, techoMapRec;
    int page;
    public TechnoMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        page=0;
        pages=new ArrayList<>();
        pages.add("Коридоры лифтовые зоны и все такое");
        pages.add("Подоконники и разные у оконныз зоны");
        pages.add("Полы в коридорах");
        View view= inflater.inflate(R.layout.fragment_techno_map_fragment, container, false);
        rightPage=(ImageView) view.findViewById(R.id.rightPageImageView);
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(true);
            }
        });
        leftPage=(ImageView) view.findViewById(R.id.leftPageImageView);
        leftPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(false);
            }
        });
        pageInfo=(TextView) view.findViewById(R.id.pageInfoTextView);

        numberPicker=(com.shawnlin.numberpicker.NumberPicker) view.findViewById(R.id.numberPicker);
        yearPicker=(com.shawnlin.numberpicker.NumberPicker) view.findViewById(R.id.yearPicker);

        techoMapRec=(RecyclerView) view.findViewById(R.id.technoMapRecyclerView);
        dayReycler=(RecyclerView) view.findViewById(R.id.dayRecycler);
        fullDateLayout=(ConstraintLayout) view.findViewById(R.id.fullDateLayout);
        fullDateImage=(ImageView) view.findViewById(R.id.fullDateImageView);
        infoDateLayout=(LinearLayout) view.findViewById(R.id.dateInfoLayout);

        infoDateLayout.setVisibility(View.GONE);
        fullDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateClicked();
            }
        });

        numberPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        numberPicker.setDividerColorResource(android.R.color.transparent);
        yearPicker.setDividerColor(getActivity().getResources().getColor( R.color.white));
        yearPicker.setDividerColorResource(R.color.white);

// Set formatter


// Set selected text color
        numberPicker.setSelectedTextColor(getActivity().getResources().getColor( R.color.colorPrimary));
        numberPicker.setSelectedTextColorResource(R.color.colorPrimary);
        yearPicker.setSelectedTextColor(getActivity().getResources().getColor( R.color.darkgrey));
        yearPicker.setSelectedTextColorResource(R.color.darkgrey);

// Set selected text size


// Set text color
        numberPicker.setTextColor(getActivity().getResources().getColor( R.color.darkgrey));
        numberPicker.setTextColorResource(R.color.darkgrey);
        yearPicker.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        yearPicker.setTextColorResource(R.color.greyy);

// Set text size

// Set typeface


// Set value
        String[] data = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(7);
        yearPicker.setMinValue(2015);
        yearPicker.setMaxValue(2019);
        yearPicker.setValue(2018);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL,false);
        DaysAdapter adapter=new DaysAdapter(30);
        dayReycler.setLayoutManager(layoutManager);
        dayReycler.setItemAnimator(new DefaultItemAnimator());
        dayReycler.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(getActivity());
        List<TechnoMapForm> forms=new ArrayList<>();
        forms.add(new TechnoMapForm("13:00-16:00","Подоконник", "1","Полы мытб","50 мин",0));
        forms.add(new TechnoMapForm("13:00-16:00","Подоконник", "1","Полы мытб","50 мин",1));
        forms.add(new TechnoMapForm("13:00-16:00","Подоконник", "1","Полы мытб","50 мин",2));
        forms.add(new TechnoMapForm("13:00-16:00","Подоконник", "1","Полы мытб","50 мин",3));
        forms.add(new TechnoMapForm("13:00-16:00","Подоконник", "1","Полы мытб","50 мин",0));
        TechnoMapAdapter adapter1=new TechnoMapAdapter(forms);
        techoMapRec.setLayoutManager(layoutManager1);
        techoMapRec.setItemAnimator(new DefaultItemAnimator());
        techoMapRec.setAdapter(adapter1);
        return view;
    }
    public void dateClicked(){
        if(infoDateLayout.getVisibility()==View.VISIBLE){
            infoDateLayout.setVisibility(View.GONE);
            fullDateImage.setImageResource(R.drawable.ic_arrowdown_green);
        }
        else {
            infoDateLayout.setVisibility(View.VISIBLE);
            fullDateImage.setImageResource(R.drawable.ic_arrowup_green);
        }
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

    }
}
