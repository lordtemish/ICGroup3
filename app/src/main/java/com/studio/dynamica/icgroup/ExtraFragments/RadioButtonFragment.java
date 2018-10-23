package com.studio.dynamica.icgroup.ExtraFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioButtonFragment extends Fragment {
    List<RadioButton> radioButtons;
    List<LinearLayout> radioLinears;
    int[] radios={R.id.button1, R.id.button2,R.id.button3,R.id.button4}, gals={R.id.firstGal,R.id.secondGal,R.id.thirdGal,R.id.fourthGal};
    int page=0;
    public RadioButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_radio_button, container, false);
        createViews(view);

        setChecked(page);
        return view;
    }
    private void createViews(View view){
        radioButtons=new ArrayList<>();radioLinears=new ArrayList<>();
        for(int i=0;i<4;i++){
            radioButtons.add((RadioButton) view.findViewById(radios[i]));
            radioLinears.add((LinearLayout) view.findViewById(gals[i]));
            radioLinears.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setClear();
                    setChecked(radioLinears.indexOf(view));
                }
            });
        }
    }

    private void setClear(){
        for(int i=0;i<4;i++){
            radioButtons.get(i).setChecked(false);

        }
    }
    private void setChecked(int i){
        radioButtons.get(i).setChecked(true);
        page=i;
        getArguments().putInt("checked",page);
    }

    public int getPage(){
        return page;
    }
}
