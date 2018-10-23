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
import com.studio.dynamica.icgroup.Adapters.ChooseAcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.RadioUserAdapter;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.Forms.RadioUserForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewOlkFragment extends Fragment {
    RecyclerView setAcceptRecyclerView,radioUserRecyclerView;
    List<Spinner> spinners;
    List<TextView> rateTexts;
    TextView acceptLabelTextView, createNewTextView, commentsLabelTextView, mainObjectTitle;
    public AddNewOlkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_new_olk, container, false);
        createViews(view);
        spinnerSet();
        setFonttype();
        ((MainActivity) getActivity()).setRecyclerViewOrientation(setAcceptRecyclerView, LinearLayoutManager.VERTICAL);
        List<ChooseAcceptForm> acceptForms=new ArrayList<>();acceptForms.add(new ChooseAcceptForm("","",""));acceptForms.add(new ChooseAcceptForm("","",""));
        acceptForms.add(new ChooseAcceptForm("","",""));
        acceptForms.add(new ChooseAcceptForm("","",""));
        ChooseAcceptAdapter adapter=new ChooseAcceptAdapter(acceptForms);
        setAcceptRecyclerView.setAdapter(adapter);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(radioUserRecyclerView,LinearLayoutManager.VERTICAL);
        List<RadioUserForm> radioUserForms=new ArrayList<>();
        radioUserForms.add(new RadioUserForm("Теимрлан Алмасов","ОПУ",true));
        radioUserForms.add(new RadioUserForm("Теимрлан Алмасов","ОПУ",false));
        RadioUserAdapter userAdapter=new RadioUserAdapter(radioUserForms);
        radioUserRecyclerView.setAdapter(userAdapter);
        return view;
    }
    private void spinnerSet(){
        for(int i=0;i<3;i++){

            String[] numbers={"1","2","3","4","5"};
            ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getActivity(),R.layout.rate_spinner_item,numbers){
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view=null;
                    view=super.getDropDownView(position, convertView, parent);
                    try {

                        setTypeface("demibold", ((TextView) view));
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    return view;
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view= super.getView(position, convertView, parent);
                    view.setBackgroundResource(R.drawable.green_circle_line);
                    setTypeface("demibold",((TextView)view));
                    return view;
                }
            };
            spinners.get(i).setAdapter(spinnerAdapter);
        }
    }
    private void setFonttype(){
        setTypeface("demibold", createNewTextView);
        setTypeface("it", mainObjectTitle);
        setTypeface("light",rateTexts.get(0), rateTexts.get(1) ,rateTexts.get(2), acceptLabelTextView, commentsLabelTextView);
    }
    private void setTypeface(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity)getActivity()).getTypeFace(s));
        }
    }
    private void createViews(View view){
        setAcceptRecyclerView=(RecyclerView) view.findViewById(R.id.setAcceptRecyclerView);
        radioUserRecyclerView=(RecyclerView) view.findViewById(R.id.radioUserRecyclerView);
        acceptLabelTextView=(TextView) view.findViewById(R.id.acceptLabelTextView);
        createNewTextView=(TextView) view.findViewById(R.id.createNewTextView);
        commentsLabelTextView=(TextView) view.findViewById(R.id.commentsLabelTextView);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);

        spinners=new ArrayList<>();
        spinners.add((Spinner) view.findViewById(R.id.rateSpinner0));
        spinners.add((Spinner) view.findViewById(R.id.rateSpinner1));
        spinners.add((Spinner) view.findViewById(R.id.rateSpinner2));

        rateTexts=new ArrayList<>();
        rateTexts.add((TextView) view.findViewById(R.id.rateTextView0));
        rateTexts.add((TextView) view.findViewById(R.id.rateTextView1));
        rateTexts.add((TextView) view.findViewById(R.id.rateTextView2));
    }

}
