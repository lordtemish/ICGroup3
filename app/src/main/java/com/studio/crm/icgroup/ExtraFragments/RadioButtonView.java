package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.studio.crm.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonView extends FrameLayout {
    List<RadioButton> radioButtons;
    List<LinearLayout> radioLinears;
    List<CounterView> counterViews;
    int[] radios={R.id.button1, R.id.button2,R.id.button3,R.id.button4}, gals={R.id.firstGal,R.id.secondGal,R.id.thirdGal,R.id.fourthGal}
        , counters={R.id.firstCounter, R.id.secondCounter,  R.id.thirdCounter,R.id.fourthCounter};
    int page=0;
    public RadioButtonView(Context context, AttributeSet attrs,int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public RadioButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RadioButtonView(Context context) {
        super(context);
        initView();
    }
    private void initView() {
        View view = inflate(getContext(), R.layout.radiobutton_view, null);
        addView(view);
        createViews(view);
    }
    private void createViews(View view){
        radioButtons=new ArrayList<>();radioLinears=new ArrayList<>();
        counterViews=new ArrayList<>();
        for(int i=0;i<4;i++){
            radioButtons.add((RadioButton) view.findViewById(radios[i]));
            radioLinears.add((LinearLayout) view.findViewById(gals[i]));
            counterViews.add((CounterView)view.findViewById(counters[i]));
            final int j=i;
            radioLinears.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setChecked(j);
                }
            });
        }
    }
    private void setChecked(int i){

        boolean a=radioButtons.get(i).isChecked();
        Log.d("index it", i+" "+a);
        if(a){
            counterViews.get(i).setVisibility(View.GONE);
        }
        else{
            counterViews.get(i).setVisibility(View.VISIBLE);
        }
        radioButtons.get(i).setChecked(!a);
        Log.d("index it", ""+(radioLinears.get(i).getVisibility()==View.VISIBLE));
    }

    public int[] getResults(){
        int[] a={-1,-1,-1,-1};
        for(int i=0;i<4;i++){
            if(radioButtons.get(i).isChecked()){
                a[i]=counterViews.get(i).getPage();
            }
        }
        return a;
    }
    public void setResult(int index, int result){
        radioButtons.get(index).setChecked(false);
        setChecked(index);
        counterViews.get(index).setPage(result);
    }
}
