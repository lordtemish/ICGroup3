package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Adapters.RateStarsAdapter;
import com.studio.dynamica.icgroup.Adapters.WashAdapter;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.Forms.WashForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechnoMapInfoFragment extends Fragment {
    RecyclerView technicView,washView, statusComment, commentsRecycler, rateRecyclerView, rateLateRecyclerView;
    TextView mainObjectTitle, period, service, category, status, timeInside, placeInside, categoryInside, statusInside, methodLabel, method, periodInside, worksLabel, worksInfo,
            technicLabel, washLabel, statusLate, statusExtraInfo, employeeLabel, employeeName, employeePosition, commentsLabel,extraText, commentButtonTextView, statusButtonFailTextView, statusButtonCloseTextView;
    LinearLayout statusButtonsLayout,commentLayout, wholeLayout;
    RadioButton goodJobRadio,answerRadioButton0, answerRadioButton1, answerRadioButton2;
    RadioGroup answerRadioGroup;
    public TechnoMapInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_techno_map_info, container, false);
        createViews(view);
        setFonttype();

        List<WashForm> washForms=new ArrayList<>();
        washForms.add(new WashForm("","",6));
        washForms.add(new WashForm("","",6));
        washForms.add(new WashForm("","",6));
        washForms.add(new WashForm("","",6));

        WashAdapter washAdapter=new WashAdapter(washForms);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        technicView.setLayoutManager(layoutManager);
        technicView.setItemAnimator(new DefaultItemAnimator());
        technicView.setAdapter(washAdapter);


        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        washView.setLayoutManager(layoutManager1);
        washView.setItemAnimator(new DefaultItemAnimator());
        washView.setAdapter(washAdapter);

        List<MessageForm> forms=new ArrayList<>();
        List<MessageForm> forms1=new ArrayList<>();
        forms.add(new MessageForm("some text i am just writing some tex cause i need some big text for my programm, so wgile i was writing whis programm, i was thinking bout a lot of things"));
        forms1.add(new MessageForm(getActivity().getResources().getString(R.string.bigtext),"13 января | 13:55","Алмасов Красавчик",5));
        MessageAdapter messageAdapter=new MessageAdapter(forms);
        MessageAdapter messageAdapter1=new MessageAdapter(forms1);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        statusComment.setLayoutManager(layoutManager2);
        statusComment.setItemAnimator(new DefaultItemAnimator());
        statusComment.setAdapter(messageAdapter);

        RecyclerView.LayoutManager layoutManager3=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        commentsRecycler.setLayoutManager(layoutManager3);
        commentsRecycler.setItemAnimator(new DefaultItemAnimator());
        commentsRecycler.setAdapter(messageAdapter1);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(rateRecyclerView,LinearLayoutManager.HORIZONTAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(rateLateRecyclerView,LinearLayoutManager.HORIZONTAL);
        rateRecyclerView.setAdapter(new RateStarsAdapter(true));
        rateLateRecyclerView.setAdapter(new RateStarsAdapter(4));

        int stat=getArguments().getInt("status",0);
        setStatus(stat);
        return view;
    }
    private void setFonttype(){
        setTypeFace("demibold", timeInside, categoryInside, placeInside, statusInside, method, worksLabel, statusExtraInfo, statusLate, employeeName, employeePosition,goodJobRadio,answerRadioButton0,answerRadioButton1,answerRadioButton2);
        setTypeFace("light", period, service, category ,status, methodLabel, worksInfo, washLabel, technicLabel, employeeLabel,commentsLabel,extraText, commentButtonTextView, statusButtonFailTextView, statusButtonCloseTextView);
        setTypeFace("it",mainObjectTitle);
        setTypeFace("medium",periodInside);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
    }
    private void createViews(View view){
        technicView=(RecyclerView) view.findViewById(R.id.technicRecyclerView);
        washView=(RecyclerView) view.findViewById(R.id.washRecyclerView);
        statusComment=(RecyclerView) view.findViewById(R.id.statusCommentsRecyclerView);
        commentsRecycler=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
        rateLateRecyclerView=(RecyclerView) view.findViewById(R.id.rateLateRecyclerView);

        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        period=(TextView) view.findViewById(R.id.period);
        category=(TextView) view.findViewById(R.id.category);
        service=(TextView) view.findViewById(R.id.service);
        status=(TextView) view.findViewById(R.id.status);
        timeInside=(TextView) view.findViewById(R.id.time);
        placeInside=(TextView) view.findViewById(R.id.place);
        categoryInside=(TextView) view.findViewById(R.id.categoryInside);
        statusInside=(TextView) view.findViewById(R.id.statusInside);
        periodInside=(TextView) view.findViewById(R.id.periodInside);
        methodLabel=(TextView) view.findViewById(R.id.methodLabel);
        method=(TextView) view.findViewById(R.id.method);
        worksLabel=(TextView) view.findViewById(R.id.worksLabel);
        worksInfo=(TextView) view.findViewById(R.id.worksInfo);
        technicLabel=(TextView) view.findViewById(R.id.technicLabel);
        washLabel=(TextView) view.findViewById(R.id.washLabel);
        statusExtraInfo=(TextView) view.findViewById(R.id.statusExtraInfo);
        statusLate=(TextView) view.findViewById(R.id.statusLate);
        employeePosition=(TextView) view.findViewById(R.id.employeePosition);
        employeeName=(TextView) view.findViewById(R.id.employeeName);
        employeeLabel=(TextView) view.findViewById(R.id.employeeLabel);
        commentsLabel=(TextView) view.findViewById(R.id.commentsLabel);
        extraText=(TextView) view.findViewById(R.id.extraText);
        commentButtonTextView=(TextView) view.findViewById(R.id.commentButtonTextView);
        statusButtonFailTextView=(TextView) view.findViewById(R.id.statusButtonFailTextView);
        statusButtonCloseTextView=(TextView) view.findViewById(R.id.statusButtonCloseTextView);

        goodJobRadio=(RadioButton) view.findViewById(R.id.goodJobRadio);
        answerRadioButton0=(RadioButton) view.findViewById(R.id.answerRadioButton0);
        answerRadioButton1=(RadioButton) view.findViewById(R.id.answerRadioButton1);
        answerRadioButton2=(RadioButton) view.findViewById(R.id.answerRadioButton2);
        answerRadioGroup=(RadioGroup) view.findViewById(R.id.answerRadioGroup);

        wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);

    }

    private void setFailed(){
        wholeLayout.setBackgroundResource(R.drawable.failed_lowdarkgreen);
        timeInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        placeInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        categoryInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        statusInside.setBackgroundResource(R.drawable.failed_verydarkgreen);
        methodLabel.setTextColor(getActivity().getResources().getColor(R.color.white));
        method.setTextColor(getActivity().getResources().getColor(R.color.white));
        periodInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        statusInside.setText("Провалено");
    }
    private void setFinished(){
        statusInside.setBackgroundResource(R.drawable.greyrow_page);
        statusInside.setText("Выполнено");
    }
    public void setinProcess(){
        statusInside.setBackgroundResource(R.drawable.inwait_yellowpage);
        statusInside.setText("В процессе");
    }
    public void setActual(){
        statusInside.setBackgroundResource(R.drawable.inprocess_green_page);
        statusInside.setText("Актуально");
    }
    private void setStatus(int a){
        switch (a){
            case 0:
                setFinished();
                break;
            case 1:
                setFailed();
                break;
            case 2:
                setinProcess();
                break;
            case 3:
                setActual();
                break;
        }
    }
}
