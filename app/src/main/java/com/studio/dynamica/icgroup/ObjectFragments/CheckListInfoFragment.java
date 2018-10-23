package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.CheckListBoxAdapter;
import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListInfoFragment extends Fragment {
    RecyclerView recyclerView, messagesInfoRecyclerView, infoAcceptRecyclerView;
    Spinner spinner;
    TextView mainObjectTitle,infoDateTextView, totalMarkLabelTextView, rateTextView, revisorLabelTextView, nameInfoLayout,tookLabel;
    public CheckListInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_list_info, container, false);
        createViews(view);
        setFonttype();
        List<CheckListBoxForm> boxForms=new ArrayList<>();
        List<CheckListBoxRowForm> boxRowForms=new ArrayList<>();

        boxRowForms.add(new CheckListBoxRowForm("Тима Машуров","",4));
        boxRowForms.add(new CheckListBoxRowForm("","",3));
        boxRowForms.add(new CheckListBoxRowForm("","",2));

        boxForms.add(new CheckListBoxForm("",true, boxRowForms));
        boxForms.add(new CheckListBoxForm("",false, boxRowForms));


        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(messagesInfoRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(infoAcceptRecyclerView, LinearLayoutManager.HORIZONTAL);

        CheckListBoxAdapter adapter=new CheckListBoxAdapter(boxForms, true);
        recyclerView.setAdapter(adapter);

        MessageAdapter messageAdapter=new MessageAdapter(new MessageForm(getActivity().getResources().getString(R.string.bigtext)));
        messagesInfoRecyclerView.setAdapter(messageAdapter);

        List<AcceptForm> acceptForms=new ArrayList<>();
        acceptForms.add(new AcceptForm("Темирлан Алмасов","Отдел уборки","ОПУ","Провалено",false));acceptForms.add(new AcceptForm("","","","",false));acceptForms.add(new AcceptForm("","","","",false));acceptForms.add(new AcceptForm("","","","",true));
        AcceptAdapter acceptAdapter=new AcceptAdapter(acceptForms);
        infoAcceptRecyclerView.setAdapter(acceptAdapter);

        return view;
    }

    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold", infoDateTextView, rateTextView, nameInfoLayout,tookLabel);
        setTypeFace("light", totalMarkLabelTextView, revisorLabelTextView);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.checkListRecyclerView);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        infoDateTextView=(TextView) view.findViewById(R.id.infoDateTextView);
        totalMarkLabelTextView=(TextView) view.findViewById(R.id.totalMarkLabelTextView);
        rateTextView=(TextView) view.findViewById(R.id.rateTextView);
        revisorLabelTextView=(TextView) view.findViewById(R.id.revisorLabelTextView);
        tookLabel=(TextView) view.findViewById(R.id.tookLabel);
        nameInfoLayout=(TextView) view.findViewById(R.id.nameInfoLayout);

        messagesInfoRecyclerView=(RecyclerView) view.findViewById(R.id.messagesInfoRecyclerView);
        infoAcceptRecyclerView=(RecyclerView) view.findViewById(R.id.infoAcceptRecyclerView);
    }
}
