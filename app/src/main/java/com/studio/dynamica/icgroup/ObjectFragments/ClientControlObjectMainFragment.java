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

import com.studio.dynamica.icgroup.Adapters.ClientControlAdapter;
import com.studio.dynamica.icgroup.Forms.CheckListForm;
import com.studio.dynamica.icgroup.Forms.OlkForm;
import com.studio.dynamica.icgroup.Forms.svodkaRateForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientControlObjectMainFragment extends Fragment {
    int page=0;
    List<String> pages;
    View view;
    TextView pageInfo, addNewTextView;
    LinearLayout progressLayout;
    ConstraintLayout rateLayout;
    ImageView left, right;
    RecyclerView recyclerView;
    ClientControlAdapter OlkAdapter, svodkaAdapter, checkListAdapter;
    public ClientControlObjectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_client_control_object_main, container, false);

        createAllViews();

        setAllListeners();

        List<Object> olkForms=new ArrayList<>();
        List<Object> checkListForms=new ArrayList<>();
        List<Object> svodkaRateForms=new ArrayList<>();

        olkForms.add(new OlkForm("","","",1));
        olkForms.add(new OlkForm("","","",1));
        olkForms.add(new OlkForm("","","",1));
        checkListForms.add(new CheckListForm("","",1));
        checkListForms.add(new CheckListForm("","",1));
        checkListForms.add(new CheckListForm("","",1));
        svodkaRateForms.add(new svodkaRateForm("","","",1));
        svodkaRateForms.add(new svodkaRateForm("","","",1));
        svodkaRateForms.add(new svodkaRateForm("","","",1));

        OlkAdapter=new ClientControlAdapter(olkForms, 0);
        checkListAdapter=new ClientControlAdapter(checkListForms,1);
        svodkaAdapter=new ClientControlAdapter(svodkaRateForms,2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(OlkAdapter);


        pages=new ArrayList<>();
        pages.add("Опросной лист клиента");
        pages.add("Чек-лист проверки качества работы");
        pages.add("Сводка по результатам обзвона");

        checkPage();
        return view;
    }


    private void createAllViews(){
        addNewTextView=(TextView) view.findViewById(R.id.createNewTextView);
        pageInfo=(TextView) view.findViewById(R.id.pageInfoTextView);
        left=(ImageView) view.findViewById(R.id.ImageLeftView);
        right=(ImageView) view.findViewById(R.id.ImageRightView);
        progressLayout=(LinearLayout) view.findViewById(R.id.progressLayout);
        rateLayout=(ConstraintLayout) view.findViewById(R.id.rateLayout);
        recyclerView=(RecyclerView) view.findViewById(R.id.CCRecyclerView);
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
                break;
            case 1:
                s="Чек лист";
                setProgressLayout();
                recyclerView.setAdapter(checkListAdapter);
                break;
            case 2:
                s="сводку";
                setSvodkaRate();
                recyclerView.setAdapter(svodkaAdapter);
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
