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

import com.studio.dynamica.icgroup.Activities.MainActivity;
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
    ImageView rightPage, leftPage;
    TextView pageInfo, mainObjectTitle,extraText, period, service, category, status;
    RecyclerView  techoMapRec;
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
        createViews(view);
        setFonttype();
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(true);
            }
        });
        leftPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(false);
            }
        });







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
    private void createViews(View view){
        rightPage=(ImageView) view.findViewById(R.id.rightPageImageView);
        leftPage=(ImageView) view.findViewById(R.id.leftPageImageView);
        pageInfo=(TextView) view.findViewById(R.id.pageInfoTextView);
        extraText=(TextView) view.findViewById(R.id.extraText);
        period=(TextView) view.findViewById(R.id.period);
        category=(TextView) view.findViewById(R.id.category);
        service=(TextView) view.findViewById(R.id.service);
        status=(TextView) view.findViewById(R.id.status);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        techoMapRec=(RecyclerView) view.findViewById(R.id.technoMapRecyclerView);
    }
    private void setFonttype(){
        setTypeFace("demibold", pageInfo);
        setTypeFace("light", extraText, period, status, category, service);
        setTypeFace("it", mainObjectTitle);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
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
