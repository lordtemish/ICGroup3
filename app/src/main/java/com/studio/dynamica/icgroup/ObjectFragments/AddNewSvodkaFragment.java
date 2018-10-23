package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.ChooseAcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.RateStarsAdapter;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewSvodkaFragment extends Fragment {

    RecyclerView rateRecyclerView, acceptRecyclerView;
    TextView mainObjectTitle,acceptLabelTextView,commentsLabelTextView, addNewTextView;
    List<EditText> editTexts;
    List<TextView> textViews;
    public AddNewSvodkaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_new_svodka, container, false);
        createViews(view);
        setFonttype();
        ((MainActivity)getActivity()).setRecyclerViewOrientation(rateRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(acceptRecyclerView,LinearLayoutManager.VERTICAL);

        List<ChooseAcceptForm> acceptForms=new ArrayList<>();
        acceptForms.add(new ChooseAcceptForm("","",""));acceptForms.add(new ChooseAcceptForm("","",""));acceptForms.add(new ChooseAcceptForm("","",""));

        RateStarsAdapter rateStarsAdapter=new RateStarsAdapter(true);
        ChooseAcceptAdapter acceptAdapter=new ChooseAcceptAdapter(acceptForms);

        rateRecyclerView.setAdapter(rateStarsAdapter);
        acceptRecyclerView.setAdapter(acceptAdapter);
        return view;
    }
    private void createViews(View view){
            rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
            acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
            mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        acceptLabelTextView=(TextView) view.findViewById(R.id.acceptLabelTextView);
        commentsLabelTextView=(TextView) view.findViewById(R.id.commentsLabelTextView);
        addNewTextView=(TextView) view.findViewById(R.id.addNewTextView);

            editTexts=new ArrayList<>();
            textViews=new ArrayList<>();
            editTexts.add((EditText)view.findViewById(R.id.cityEditText));
            textViews.add((TextView) view.findViewById(R.id.cityTextView));
              editTexts.add((EditText)view.findViewById(R.id.addressEditText));
              textViews.add((TextView) view.findViewById(R.id.addressTextView));
            editTexts.add((EditText)view.findViewById(R.id.clientEditText));
            textViews.add((TextView) view.findViewById(R.id.clientTextView));
            editTexts.add((EditText)view.findViewById(R.id.positionEditText));
            textViews.add((TextView) view.findViewById(R.id.positionTextView));

    }
    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold", addNewTextView);
        setTypeFace("light",acceptLabelTextView,commentsLabelTextView);
        for(TextView i :textViews) {
            setTypeFace("light",i);
        }
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }
}
